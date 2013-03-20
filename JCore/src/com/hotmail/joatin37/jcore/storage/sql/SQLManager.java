/*
 * Copyright 2013 Joatin Granlund. All rights reserved.
 *
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 *    conditions and the following disclaimer.
 *
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 *    of conditions and the following disclaimer in the documentation and/or other materials
 *    provided with the distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package com.hotmail.joatin37.jcore.storage.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.core.Core;

public class SQLManager {

	private final Core core;
	private static Connection conn = null;
	private final String baseURL;
	private static HashMap<String, SQL> connections = new HashMap<String, SQL>(
			5, 1);
	private static SQLManager manager;
	public final static String QUICKSTORAGE = "quickstorage";
	public final static String GLOBALQUICKSTORAGE = "globalquickstorage";
	public final static String GLOBALSCHEMA = "globalschema";

	public SQLManager(Core core) throws SQLException {
		this.core = core;
		manager = this;
		this.baseURL = this.core.getConfig().getString("sql.url",
				"mysql://localhost:3306/");
		if (SQLManager.conn == null) {
			Properties connectionProps = new Properties();
			connectionProps.put("user",
					this.core.getConfig().getString("sql.username", "root"));
			connectionProps.put("password",
					this.core.getConfig().getString("sql.password", ""));

			SQLManager.conn = DriverManager.getConnection("jdbc:"
					+ this.baseURL, connectionProps);
			this.core.getLogger().info("Connected to database");
		}
	}

	private void reconnect() {
		try {
			Properties connectionProps = new Properties();
			connectionProps.put("user",
					this.core.getConfig().getString("sql.username", "root"));
			connectionProps.put("password",
					this.core.getConfig().getString("sql.password", ""));
			SQLManager.conn = DriverManager.getConnection("jdbc:"
					+ this.baseURL, connectionProps);
			this.core.getLogger().info("Connected to database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized SQL getSQL(JavaPlugin plugin) {
		SQL sql;
		synchronized (connections) {
			if ((sql = connections.get(plugin.getName())) != null) {
				return sql;
			} else {
				try {
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS "
							+ plugin.getName());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sql = new SQLBase(plugin.getName(), manager);
				connections.put(plugin.getName(), sql);
				return sql;
			}
		}

	}

	public synchronized String getString(String schema, String key) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM `" + schema + "`.`"
							+ SQLManager.QUICKSTORAGE + "` WHERE `key`=\'"
							+ key + "\'");
			rs.next();
			return rs.getString("value");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public synchronized Number getNumber(String schema, String key) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt
					.executeQuery("SELECT * FROM `" + schema + "`.`"
							+ SQLManager.QUICKSTORAGE + "` WHERE `key`=\'"
							+ key + "\'");
			rs.next();
			Core.sendDebug(rs.getString("type"));
			return this.reconstructNumberFromString(rs.getString("value"),
					rs.getString("type"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {

			}
		}
	}

	public void remove(String schema, String key) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM `" + schema + "`.`"
					+ SQLManager.QUICKSTORAGE + "` WHERE `key`=\'" + key + "\'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param schema
	 * @param key
	 * @param datatype
	 * @param value
	 * @since JCore 1.0
	 */
	public synchronized void doPut(String schema, String key, String datatype,
			String value) {
		try {
			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate("CREATE TABLE IF NOT EXISTS `"
					+ schema
					+ "`.`"
					+ SQLBase.QUICKSTORAGE
					+ "` (`key` VARCHAR(255) NOT NULL , `type` VARCHAR(32) NULL DEFAULT 'String' , `value` TEXT NULL , PRIMARY KEY (`key`) , UNIQUE INDEX `key_UNIQUE` (`key` ASC));");
		} catch (SQLException e1) {
			this.reconnect();
		}

		try {
			Statement stmt3 = conn.createStatement();
			stmt3.executeUpdate("INSERT INTO `" + schema + "`.`"
					+ SQLBase.QUICKSTORAGE + "` VALUES ( \'" + key + "\', \'"
					+ datatype + "\', \'" + value + "\')");
		} catch (SQLException e1) {
			try {

				Statement stmt2 = conn.createStatement();
				stmt2.executeUpdate("UPDATE `" + schema + "`.`"
						+ SQLBase.QUICKSTORAGE + "` SET `type`=\'" + datatype
						+ "\', `value`=\'" + value + "\' WHERE `key`=\'" + key
						+ "\'");
			} catch (SQLException e) {
				Core.sendDebug("Severe sql error");
			}
		}

	}

	public enum DataType {
		INTEGER, FLOAT, DOUBLE, SHORT, LONG, BYTE, STRING
	}

	/**
	 * Translates the number passed into a DataType enum.
	 * 
	 * @param number
	 *            The number to turn into a enum
	 * @return The enum that matches the Number
	 * @since JCore 1.0
	 * @see Number
	 */
	public DataType translateNumberToType(Number number) {
		if (number instanceof Integer) {
			return DataType.INTEGER;
		} else {
			if (number instanceof Float) {
				return DataType.FLOAT;
			} else {
				if (number instanceof Double) {
					return DataType.DOUBLE;
				} else {
					if (number instanceof Short) {
						return DataType.SHORT;
					} else {
						if (number instanceof Long) {
							return DataType.LONG;
						} else {
							if (number instanceof Byte) {
								return DataType.BYTE;
							} else {
								return DataType.STRING;
							}
						}
					}
				}
			}
		}
	}

	public Number reconstructNumberFromString(String string, String type) {
		if (type.equals(DataType.BYTE.name())) {
			return Byte.parseByte(string);
		} else {
			if (type.equals(DataType.DOUBLE.name())) {
				return Double.parseDouble(string);
			} else {
				if (type.equals(DataType.FLOAT.name())) {
					return Float.parseFloat(string);
				} else {
					if (type.equals(DataType.INTEGER.name())) {
						return Integer.parseInt(string);
					} else {
						if (type.equals(DataType.LONG.name())) {
							return Long.parseLong(string);
						} else {
							if (type.equals(DataType.SHORT.name())) {
								return Short.parseShort(string);
							} else {
								return null;
							}
						}
					}
				}
			}
		}
	}
}
