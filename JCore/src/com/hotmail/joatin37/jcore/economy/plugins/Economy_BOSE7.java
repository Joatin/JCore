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

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.hotmail.joatin37.jcore.InsufficientPrivilegeException;
import com.hotmail.joatin37.jcore.NotSuportedException;
import com.hotmail.joatin37.jcore.core.Core;
import com.hotmail.joatin37.jcore.economy.EconomyDescription;
import com.hotmail.joatin37.jcore.economy.IEconomy;
import com.hotmail.joatin37.jcore.economy.IllegalBankNameException;
import com.hotmail.joatin37.jcore.economy.InsufficientMoneyException;
import com.hotmail.joatin37.jcore.language.Lang;

import cosine.boseconomy.BOSEconomy;

public class Economy_BOSE7 implements IEconomy {

	private final String NAME = "BOSEconomy";
	private final String VERSION = "0.7";
	private Core core;
	private BOSEconomy economy = null;

	public Economy_BOSE7(Core core) {
		this.core = core;
		Bukkit.getServer().getPluginManager()
				.registerEvents(new PluginLoadListener(this), core);
		if (this.economy == null) {
			Plugin bose = core.getServer().getPluginManager()
					.getPlugin(this.NAME);
			if (bose != null
					&& bose.isEnabled()
					&& bose.getDescription().getVersion()
							.startsWith(this.VERSION)) {
				this.economy = (BOSEconomy) bose;
				Lang.sendConsoleInfoMessage(core, "AF", bose);
			}
		}
	}

	public class PluginLoadListener implements Listener {
		Economy_BOSE7 economy = null;

		public PluginLoadListener(Economy_BOSE7 economy) {
			this.economy = economy;
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void onPluginEnable(PluginEnableEvent event) {
			if (this.economy.economy == null) {
				Plugin bose = Economy_BOSE7.this.core.getServer()
						.getPluginManager().getPlugin(Economy_BOSE7.this.NAME);
				if (bose != null
						&& bose.isEnabled()
						&& bose.getDescription().getVersion()
								.startsWith(Economy_BOSE7.this.VERSION)) {
					this.economy.economy = (BOSEconomy) bose;
					Lang.sendConsoleInfoMessage(Economy_BOSE7.this.core, "AF",
							bose);
				}
			}
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void onPluginDisable(PluginDisableEvent event) {
			if (this.economy.economy != null) {
				if (event.getPlugin().getDescription().getName()
						.equals(Economy_BOSE7.this.NAME)
						&& event.getPlugin().getDescription().getVersion()
								.startsWith(Economy_BOSE7.this.VERSION)) {
					this.economy.economy = null;
				}
			}
		}
	}

	@Override
	public String getName() {
		return this.NAME;
	}

	@Override
	public boolean hasBankSupport() {
		// This plugin does suport banks
		return true;
	}

	@Override
	public int fractionalDigits() {
		return this.economy.getFractionalDigits();
	}

	@Override
	public String format(double amount) {
		return this.economy.getMoneyFormatted(amount);
	}

	@Override
	public String currencyNamePlural() {
		return this.economy.getMoneyNamePlural();
	}

	@Override
	public String currencyNameSingular() {
		return this.economy.getMoneyName();
	}

	@Override
	public boolean hasAccount(String playerName) {
		if (playerName == null) {
			throw new NullPointerException();
		}
		return this.economy.playerRegistered(playerName, false);
	}

	@Override
	public double getBalance(String playerName) {
		if (playerName == null) {
			throw new NullPointerException();
		}
		final double balance;
		balance = this.economy.getPlayerMoneyDouble(playerName);
		return balance;
	}

	@Override
	public boolean has(String playerName, double amount) {
		if (playerName == null) {
			throw new NullPointerException();
		}
		return this.getBalance(playerName) >= amount;
	}

	@Override
	public double withdrawPlayer(String playerName, double amount)
			throws InsufficientMoneyException {
		if (playerName == null) {
			throw new NullPointerException();
		}
		if (amount < 0) {
			throw new IllegalArgumentException();
		}
		if (this.getBalance(playerName) < amount) {
			throw new InsufficientMoneyException(amount
					- this.getBalance(playerName), "Player " + playerName, this);
		}
		this.economy.setPlayerMoney(playerName, 0d - amount, false);
		return this.getBalance(playerName);
	}

	@Override
	public double depositPlayer(String playerName, double amount) {
		if (playerName == null) {
			throw new NullPointerException();
		}
		if (amount < 0) {
			throw new IllegalArgumentException();
		}
		this.economy.addPlayerMoney(playerName, amount, false);
		return this.getBalance(playerName);
	}

	@Override
	public double setPlayerAmount(String playerName, double amount)
			throws InsufficientMoneyException {
		if (playerName == null) {
			throw new NullPointerException();
		}
		this.economy.setPlayerMoney(playerName, amount, false);
		return this.getBalance(playerName);
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
	public double bankBalance(String bank) throws NotSuportedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean bankHas(String bank, double amount)
			throws NotSuportedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double bankWithdraw(String bank, double amount)
			throws InsufficientMoneyException, NotSuportedException {
		if (bank == null) {
			throw new NullPointerException();
		}
		if (amount < 0) {
			throw new IllegalArgumentException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		this.economy.setBankMoney(bank, 0d - amount, true);
		return this.economy.getBankMoneyDouble(bank);
	}

	@Override
	public double bankDeposit(String bank, double amount)
			throws NotSuportedException {
		if (bank == null) {
			throw new NullPointerException();
		}
		if (amount < 0) {
			throw new IllegalArgumentException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		this.economy.addBankMoney(bank, amount, true);
		return this.economy.getBankMoneyDouble(bank);
	}

	@Override
	public double setBankAmount(String bank, double amount)
			throws InsufficientMoneyException, NotSuportedException {
		if (bank == null) {
			throw new NullPointerException();
		}
		if (amount < 0) {
			throw new IllegalArgumentException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		this.economy.setBankMoney(bank, amount, true);
		return this.economy.getBankMoneyDouble(bank);
	}

	@Override
	public boolean isBankOwner(String bank, String playerName)
			throws NotSuportedException, IllegalBankNameException {
		if (bank == null || playerName == null) {
			throw new NullPointerException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}

		return this.economy.isBankOwner(bank, playerName);
	}

	@Override
	public boolean isBankMember(String bank, String playerName)
			throws NotSuportedException {
		if (bank == null || playerName == null) {
			throw new NullPointerException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		return this.economy.isBankMember(bank, playerName);
	}

	@Override
	public void addBankMember(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException {
		if (bank == null || playerName == null) {
			throw new NullPointerException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		this.economy.addBankMember(bank, playerName, true);
	}

	@Override
	public void removeBankMember(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException {
		if (bank == null || playerName == null) {
			throw new NullPointerException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		this.economy.removeBankPlayer(bank, playerName);
	}

	@Override
	public List<String> getBanks() throws NotSuportedException {
		return this.economy.getBankList();
	}

	@Override
	public void createPlayerAccount(String playerName)
			throws InsufficientPrivilegeException, NotSuportedException {
		this.economy.registerPlayer(playerName);

	}

	@Override
	public void addBankOwner(String bank, String playerName)
			throws InsufficientPrivilegeException, NotSuportedException,
			IllegalBankNameException {
		if (bank == null || playerName == null) {
			throw new NullPointerException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		this.economy.addBankOwner(bank, playerName, true);

	}

	@Override
	public void removeBankOwner(String bank, String playerName)
			throws NotSuportedException, IllegalBankNameException {
		if (bank == null || playerName == null) {
			throw new NullPointerException();
		}
		if (!this.economy.bankExists(bank)) {
			throw new IllegalBankNameException();
		}
		this.economy.removeBankPlayer(bank, playerName);

	}

	@Override
	public List<String> getBankMembers(String bank)
			throws NotSuportedException, IllegalBankNameException {
		throw new NotSuportedException();
	}

	@Override
	public List<String> getBankOwners() throws NotSuportedException,
			IllegalBankNameException {
		throw new NotSuportedException();
	}

	@Override
	public EconomyDescription getEconomyDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}