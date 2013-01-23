package com.hotmail.joatin37.jcore.sql;

public class SQLBase implements SQL {

	private final String schema;
	private final SQLManager manager;

	public SQLBase(String schema, SQLManager manager) {
		this.schema = schema;
		this.manager = manager;
	}
}
