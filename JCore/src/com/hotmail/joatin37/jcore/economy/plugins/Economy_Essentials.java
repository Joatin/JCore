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

package com.hotmail.joatin37.jcore.economy.plugins;

import java.util.List;

import com.hotmail.joatin37.jcore.InsufficientPrivilegeException;
import com.hotmail.joatin37.jcore.NotSuportedException;
import com.hotmail.joatin37.jcore.economy.EconomyDescription;
import com.hotmail.joatin37.jcore.economy.IEconomy;
import com.hotmail.joatin37.jcore.economy.IllegalBankNameException;
import com.hotmail.joatin37.jcore.economy.InsufficientMoneyException;

public class Economy_Essentials implements IEconomy {

	@Override
	public boolean hasBankSupport() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int fractionalDigits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String format(double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String currencyNamePlural() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String currencyNameSingular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAccount(String playerName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBalance(String playerName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean has(String playerName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double withdrawPlayer(String playerName, double amount)
			throws InsufficientMoneyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double depositPlayer(String playerName, double amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double setPlayerAmount(String playerName, double amount)
			throws InsufficientMoneyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double createBank(String bank, String player)
			throws InsufficientPrivilegeException, NotSuportedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteBank(String bank) throws NotSuportedException {
		// TODO Auto-generated method stub

	}

	@Override
	public double bankBalance(String bank) throws NotSuportedException,
			IllegalBankNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean bankHas(String bank, double amount)
			throws NotSuportedException, IllegalBankNameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double bankWithdraw(String bank, double amount)
			throws InsufficientMoneyException, NotSuportedException,
			IllegalBankNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double bankDeposit(String bank, double amount)
			throws NotSuportedException, IllegalBankNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double setBankAmount(String bank, double amount)
			throws InsufficientMoneyException, NotSuportedException,
			IllegalBankNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBankOwner(String bank, String playerName)
			throws NotSuportedException, IllegalBankNameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBankMember(String name, String playerName)
			throws NotSuportedException, IllegalBankNameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBankMember(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBankMember(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addBankOwner(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBankOwner(String bank, String playerName)
			throws NotSuportedException, IllegalBankNameException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getBankMembers(String bank)
			throws NotSuportedException, IllegalBankNameException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getBankOwners() throws NotSuportedException,
			IllegalBankNameException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getBanks() throws NotSuportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPlayerAccount(String playerName)
			throws InsufficientPrivilegeException, NotSuportedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyDescription getEconomyDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
