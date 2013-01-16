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

package com.hotmail.joatin37.jcore.economy;

import java.util.List;

import com.hotmail.joatin37.jcore.InsufficientPrivilegeException;
import com.hotmail.joatin37.jcore.NotSuportedException;

/**
 * A interface used for handling money.
 * 
 * @author Joatin
 * 
 * @since 1.0.0
 * 
 */
public interface IEconomy {

	/**
	 * Returns false if the underlying plugin doesn't suport banks
	 * 
	 * @return true if the underlying plugin supports banks
	 * @since 1.0.0
	 */
	public boolean hasBankSupport();

	/**
	 * Some economy plugins round off after a certain number of digits. This
	 * function returns the number of digits the plugin keeps or -1 if no
	 * rounding occurs.
	 * 
	 * @return number of digits after the decimal point kept
	 * @since 1.0.0
	 */
	public int fractionalDigits();

	/**
	 * Format amount into a human readable String This provides translation into
	 * economy specific formatting to improve consistency between plugins.
	 * 
	 * @param amount
	 * @return Human readable string describing amount
	 * @since 1.0.0
	 */
	public String format(double amount);

	/**
	 * Returns the name of the currency in plural form. If the economy being
	 * used does not support currency names then an empty string will be
	 * returned.
	 * 
	 * @return name of the currency (plural)
	 * @since 1.0.0
	 */
	public String currencyNamePlural();

	/**
	 * Returns the name of the currency in singular form. If the economy being
	 * used does not support currency names then an empty string will be
	 * returned.
	 * 
	 * @return name of the currency (singular)
	 * @since 1.0.0
	 */
	public String currencyNameSingular();

	/**
	 * Checks if this player has an account on the server yet This will always
	 * return true if the player has joined the server at least once as all
	 * major economy plugins auto-generate a player account when the player
	 * joins the server
	 * 
	 * @param playerName
	 * @return if the player has an account
	 * @throws NullPointerException
	 *             if the playerName was null
	 * @since 1.0.0
	 */
	public boolean hasAccount(String playerName);

	/**
	 * Gets balance of a player
	 * 
	 * @param playerName
	 * @return Amount currently held in players account
	 * @throws NullPointerException
	 *             if the playerName was null
	 * @since 1.0.0
	 */
	public double getBalance(String playerName);

	/**
	 * Checks if the player account has the amount, a negative amount will
	 * always return false.
	 * 
	 * @param playerName
	 *            The name of the player to check
	 * @param amount
	 *            The amount of money to check if the player has. This should be
	 *            a positive number, you can't obviously check if he has a
	 *            negative.
	 * @return True if the player has the amount specified
	 * 
	 * @throws IllegalArgumentException
	 *             if the amount was negative
	 * @throws NullPointerException
	 *             if the playerName was null
	 * @since 1.0.0
	 */
	public boolean has(String playerName, double amount);

	/**
	 * Withdraw an amount from a player. Can not be negative.
	 * 
	 * @param playerName
	 *            Name of player
	 * @param amount
	 *            Amount to withdraw
	 * @return The amount the player has left after the transaction.
	 * @throws InsufficientMoneyException
	 *             if the player has not enough money
	 * @throws IllegalArgumentException
	 *             if the amount was negative
	 * @throws NullPointerException
	 *             if the playerName was null
	 * @since 1.0.0
	 * @see InsufficientMoneyException
	 */
	public double withdrawPlayer(String playerName, double amount)
			throws InsufficientMoneyException;

	/**
	 * Deposit an amount to a player, throws a exception if the number is
	 * negative.
	 * 
	 * @param playerName
	 *            Name of player
	 * @param amount
	 *            Amount to deposit
	 * @return The amount the player has left after the transaction.
	 * @throws IllegalArgumentException
	 *             if the amount was negative
	 * @throws NullPointerException
	 *             if the playerName was null
	 * @since 1.0.0
	 */
	public double depositPlayer(String playerName, double amount);

	/**
	 * Numbers in this method can be both positive and negative. Positive
	 * numbers adds more money to the players account while a negative removes
	 * money.
	 * 
	 * @param playerName
	 *            The name of the player to add/remove money from
	 * @param amount
	 *            amount of money to withdraw/deposit
	 * @return The amount the player has left after the transaction.
	 * @throws InsufficientMoneyException
	 *             if the player doesn't have enough money
	 * @since 1.0.0
	 * @see InsufficientMoneyException
	 */
	public double setPlayerAmount(String playerName, double amount)
			throws InsufficientMoneyException;

	/**
	 * Creates a bank account with the specified name and the player as the
	 * owner
	 * 
	 * @param bank
	 * @param player
	 * @return The amount the bank has after it's construction.
	 * 
	 * @throws InsufficientPrivilegeException
	 *             if the player doesn't have the privilege to have a bank. This
	 *             is only thrown on by certain plugins.
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws NullPointerException
	 *             if either of the parameters was null
	 * @since 1.0.0
	 */
	public double createBank(String bank, String player)
			throws InsufficientPrivilegeException, NotSuportedException;

	/**
	 * Deletes a bank account with the specified name. The bank doesn't have to
	 * exist.
	 * 
	 * @param bank
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws NullPointerException
	 *             if the parameter bank was null
	 * @since 1.0.0
	 */
	public void deleteBank(String bank) throws NotSuportedException;

	/**
	 * Returns the amount the bank has
	 * 
	 * @param bank
	 * @return The banks current amount
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NullPointerException
	 *             if the parameter bank was null
	 * @since 1.0.0
	 */
	public double bankBalance(String bank) throws NotSuportedException,
			IllegalBankNameException;

	/**
	 * Returns true or false whether the bank has the amount specified, will
	 * throw an error if negative.
	 * 
	 * @param bank
	 * @param amount
	 * @return True if the bank has the specified amount
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NullPointerException
	 *             if the parameter bank was null
	 * @since 1.0.0
	 */
	public boolean bankHas(String bank, double amount)
			throws NotSuportedException, IllegalBankNameException;

	/**
	 * Withdraw an amount from a bank account, can not negative.
	 * 
	 * @param bank
	 * @param amount
	 * @return The amount the bank has left after the transaction.
	 * @throws InsufficientMoneyException
	 *             if the player doesn't have enough money
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws IllegalArgumentException
	 *             if the amount is less than 0
	 * @throws NullPointerException
	 *             if the parameter bank was null
	 * @since 1.0.0
	 * @see InsufficientMoneyException
	 */
	public double bankWithdraw(String bank, double amount)
			throws InsufficientMoneyException, NotSuportedException,
			IllegalBankNameException;

	/**
	 * Deposit an amount into a bank account. Can not be negative.
	 * 
	 * @param bank
	 * @param amount
	 * @return The amount the bank has left after the transaction.
	 * 
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws IllegalArgumentException
	 *             if the amount is less than 0
	 * @throws NullPointerException
	 *             if the parameter bank was null
	 * @since 1.0.0
	 */
	public double bankDeposit(String bank, double amount)
			throws NotSuportedException, IllegalBankNameException;

	/**
	 * Adds or removes a the amount specified. The amount can be either positive
	 * or negative. A negative amount will remove money while a positiv will
	 * add.
	 * 
	 * @param bank
	 *            The bank to add/remove money from
	 * @param amount
	 *            The amont to remove/add
	 * @return The amount the bank has left after the transaction.
	 * @throws InsufficientMoneyException
	 *             if the player doesn't have enough money
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws IllegalArgumentException
	 *             if the amount is less than 0
	 * @throws NullPointerException
	 *             if the parameter bank was null
	 * @since 1.0.0
	 * @see InsufficientMoneyException
	 */
	public double setBankAmount(String bank, double amount)
			throws InsufficientMoneyException, NotSuportedException,
			IllegalBankNameException;

	/**
	 * Check if a player is the owner of a bank account
	 * 
	 * @param bank
	 * @param playerName
	 * @return True if he is
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NullPointerException
	 *             if any of the parameters is null
	 * @since 1.0.0
	 */
	public boolean isBankOwner(String bank, String playerName)
			throws NotSuportedException, IllegalBankNameException;

	/**
	 * Check if the player is a member of the bank account
	 * 
	 * @param name
	 * @param playerName
	 * @return True if he is the owner
	 * 
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NullPointerException
	 *             if any of the parameters is null
	 * @since 1.0.0
	 */
	public boolean isBankMember(String name, String playerName)
			throws NotSuportedException, IllegalBankNameException;

	/**
	 * Adds a new member to a bank. Does nothing if the player already is a
	 * member of the bank.
	 * 
	 * @param bank
	 * @param playerName
	 * @throws InsufficientPrivilegeException
	 *             If the player doesn't have enough privileges. Only thrown by
	 *             certain plugins.
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws NullPointerException
	 *             if any of the parameters was null
	 * @since 1.0.0
	 */
	public void addBankMember(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException;

	/**
	 * Removes a player as a member of a bank.
	 * 
	 * @param bank
	 * @param playerName
	 * 
	 * @throws InsufficientPrivilegeException
	 *             If the player doesn't have enough privileges. Only thrown by
	 *             certain plugins.
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws NullPointerException
	 *             if any of the parameters was null
	 * @since 1.0.0
	 */
	public void removeBankMember(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException;

	/**
	 * Adds a owner to a bank.
	 * 
	 * @param bank
	 * @param playerName
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws InsufficientPrivilegeException
	 *             If the new owner doesn't have enough privileges
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NullPointerException
	 *             if any of the parameters was null
	 * @since 1.0.0
	 */
	public void addBankOwner(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException;

	/**
	 * Removes a owner from a bank.
	 * 
	 * @param bank
	 * @param playerName
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 * @throws NullPointerException
	 *             if any of the parameters was null
	 * @since 1.0.0
	 */
	public void removeBankOwner(String bank, String playerName)
			throws NotSuportedException, IllegalBankNameException;

	/**
	 * Returns a list of a banks members, including owners.
	 * 
	 * @param bank
	 * @return A list with the banks members
	 * @throws NotSuportedException
	 *             If the method isn't suported by the underlying plugin.
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exist.
	 */
	public List<String> getBankMembers(String bank)
			throws NotSuportedException, IllegalBankNameException;

	/**
	 * Returns a list of the banks owners. Some plugins might only allow one
	 * owner at all times. In that case the list will only return one owner.
	 * 
	 * @return A list with the owners
	 * @throws NotSuportedException
	 *             If the underlying plugin doesn't suport this operation
	 * @throws IllegalBankNameException
	 *             If the bank doesn't exists.
	 */
	public List<String> getBankOwners() throws NotSuportedException,
			IllegalBankNameException;

	/**
	 * Gets the list of banks
	 * 
	 * @return the List of Banks
	 * 
	 * 
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @throws NullPointerException
	 *             if any of the parameters is null
	 * @since 1.0.0
	 */
	public List<String> getBanks() throws NotSuportedException;

	/**
	 * Attempts to create a player account for the given player
	 * 
	 * @throws InsufficientPrivilegeException
	 *             if the player didn't have enough privileges to have a
	 *             account. This is only thrown by certain plugins.
	 * 
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @since 1.0.0
	 */
	public void createPlayerAccount(String playerName)
			throws InsufficientPrivilegeException, NotSuportedException;

	/**
	 * Returns the name of the underlying plugn;
	 * 
	 * @return Returns the name of the underlying plugn
	 * @throws NotSuportedException
	 *             if the underlying plugin does not suport this operation
	 * @since 1.0.0
	 */
	public String getName();

	/**
	 * Returns a economydescription object containing the the underlying plugins
	 * capabilities.
	 * 
	 * @return A EconomyDescription object
	 * @since 1.0.0
	 * @see EconomyDescription
	 */
	public EconomyDescription getEconomyDescription();

}
