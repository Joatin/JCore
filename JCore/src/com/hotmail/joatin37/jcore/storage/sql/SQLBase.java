package com.hotmail.joatin37.jcore.sql;

import com.hotmail.joatin37.jcore.sql.SQLManager.DataType;

public class SQLBase implements SQL {

	private final String schema;
	private final SQLManager manager;
	public final static String QUICKSTORAGE = "quickstorage";
	private final pQuickStorage storage;
	private final gQuickStorage gstorage;

	public SQLBase(String schema, SQLManager manager) {
		this.schema = schema;
		this.manager = manager;
		this.storage = new pQuickStorage();
		this.gstorage = new gQuickStorage();
	}

	@Override
	public QuickStorage QuickStoragePrivate() {
		return this.storage;
	}

	public GlobalQuickStorage QuickStorageGlobal() {
		return this.gstorage;
	}

	public final class pQuickStorage implements QuickStorage {

		private pQuickStorage() {

		}

		@Override
		public void remove(String key) {
			SQLBase.this.manager.remove(SQLBase.this.schema, key);
		}

		@Override
		public void put(String key, Number value) {
			SQLBase.this.manager.doPut(SQLBase.this.schema, key,
					SQLBase.this.manager.translateNumberToType(value).name(),
					value.toString());
		}

		@Override
		public void put(String key, String value) {
			SQLBase.this.manager.doPut(SQLBase.this.schema, key,
					DataType.STRING.name(), value);
		}

		@Override
		public Number getNumber(String key) {
			return SQLBase.this.manager.getNumber(SQLBase.this.schema, key);
		}

		@Override
		public String getString(String key) {
			return SQLBase.this.manager.getString(SQLBase.this.schema, key);
		}
	}

	public final class gQuickStorage implements GlobalQuickStorage {

		@Override
		public void remove(String key) {
			// TODO Auto-generated method stub

		}

		@Override
		public void put(String key, Number value) {
			// TODO Auto-generated method stub

		}

		@Override
		public void put(String key, String value) {
			// TODO Auto-generated method stub

		}

		@Override
		public Number getNumber(String key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getString(String key) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
