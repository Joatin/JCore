package com.hotmail.joatin37.jcore.sql;

/**
 * A interface that represents a SQL connection. You can use this to manage all
 * data operations.
 * 
 * @author Joatin
 * 
 * @since JCore 1.0
 */
public interface SQL {

	/**
	 * Returns the private quick storage. Everything put there can't be accessed
	 * from another plugin. The functions inside the QuickStorage is similar to
	 * the functions in HashMap. Even though other plugins can't acces the data,
	 * it can be viewed by external users like websites.
	 * 
	 * @return The private QuickStorage
	 * @since JCore 1.0
	 * @see QuickStorage
	 */
	public QuickStorage QuickStoragePrivate();

	public GlobalQuickStorage QuickStorageGlobal();

}
