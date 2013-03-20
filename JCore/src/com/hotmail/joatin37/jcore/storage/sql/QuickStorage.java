package com.hotmail.joatin37.jcore.storage.sql;

/**
 * A class made for quickly adding and accessing data. All the data put in here
 * are visible for other users (Like websites) in the database but not visible
 * for other plugins.
 * 
 * @author Joatin
 * 
 * @since JCore 1.0.0
 */
public interface QuickStorage {

	/**
	 * Removes a key with data from the QuickStorage
	 * 
	 * @param key
	 */
	public void remove(String key);

	/**
	 * Puts a number into the QuickStorage. The number may later be retrieved as
	 * a number or as a String.
	 * 
	 * @param key
	 *            The key for the value to put. May not be longer than 255. If
	 *            an identical key already exists it will be overridden.
	 * @param value
	 *            The number to put in the QuickStorage
	 * @throws NullPointerException
	 *             If the value or key is null
	 * @since JCore 1.0
	 * @see Number
	 */
	public void put(String key, Number value);

	/**
	 * Puts a String into the QuickStorage. The String must later be retrieved
	 * as a String. The string may not be longer than 65,535 characters.
	 * 
	 * @param key
	 *            The key for the value to put. May not be longer than 255. If
	 *            an identical key already exists it will be overridden.
	 * @param value
	 *            The String to put in the QuickStorage. May not be longer than
	 *            65,535 characters.
	 * @since JCore 1.0
	 */
	public void put(String key, String value);

	/**
	 * Returns a Number from the QuickStorage. The primitive number can later be
	 * retrieved from the number returned.
	 * 
	 * @param key
	 *            The key for the Number to be returned
	 * @return The Number stored
	 * @since JCore 1.0
	 * @see Number
	 */
	public Number getNumber(String key);

	/**
	 * Returns a String from the QuickStorage.
	 * 
	 * @param key
	 *            The key to get the String from
	 * @return The String stored
	 * @since JCore 1.0
	 */
	public String getString(String key);
}
