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

package com.hotmail.joatin37.jcore.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.core.Core;

public class SQLManager {

	private final Core core;
	private Connection conn = null;

	public SQLManager(Core core) throws SQLException {
		this.core = core;
		;
		if (this.conn == null) {

			String url = this.core.getConfig().getString("sql.url",
					"mysql://localhost:3306/");

			Properties connectionProps = new Properties();
			connectionProps.put("user",
					this.core.getConfig().getString("sql.username", "root"));
			connectionProps.put("password",
					this.core.getConfig().getString("sql.password", ""));

			this.conn = DriverManager.getConnection("jdbc:" + url,
					connectionProps);
			this.core.getLogger().info("Connected to database");
		}
	}

	public void getSQL(JavaPlugin plugin) {
		Statement stmt;
		try {
			stmt = this.conn.createStatement();
			String sql = "IF EXISTS (SELECT NAME FROM sysdatabases WHERE NAME = 'database_to_check_for') PRINT 'exists' ELSE PRINT 'does not exist'";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
