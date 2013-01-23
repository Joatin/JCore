package com.hotmail.joatin37.jcore.sql;

public class SQLBase implements SQL {

	private final String schema;
	private final SQLManager manager;
	public final static String QUICKSTORAGE = "quickstorage";

	public SQLBase(String schema, SQLManager manager) {
		this.schema = schema;
		this.manager = manager;
	}

	private enum DataType {
		INTEGER
	}

	public <V extends Number> void put(String key, V value) {

	}

	@Override
	public void put(String key, String value) {
		this.manager.doPut(this.schema, key, "string", value);
	}

	public void get(String key) {

	}

}
