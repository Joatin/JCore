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

package com.hotmail.joatin37.jcore.core;

import java.io.File;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.economy.Economy;
import com.hotmail.joatin37.jcore.economy.IEconomy;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_3co;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_AEco;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_BOSE6;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_BOSE7;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_CommandsEX;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_Craftconomy;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_Craftconomy3;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_CurrencyCore;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_Dosh;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_EconXP;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_Essentials;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_GoldIsMoney;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_GoldIsMoney2;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_Gringotts;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_McMoney;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_MineConomy;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_MultiCurrency;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_SDFEconomy;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_XPBank;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_eWallet;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_iConomy4;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_iConomy5;
import com.hotmail.joatin37.jcore.economy.plugins.Economy_iConomy6;
import com.hotmail.joatin37.jcore.landmanagement.CollectionManager;
import com.hotmail.joatin37.jcore.landmanagement.ICollectionManager;
import com.hotmail.joatin37.jcore.landmanagement.LandHandler;
import com.hotmail.joatin37.jcore.language.Lang;
import com.hotmail.joatin37.jcore.language.Lang.LangTag;
import com.hotmail.joatin37.jcore.metrics.GraphCollector;
import com.hotmail.joatin37.jcore.storage.sql.SQL;
import com.hotmail.joatin37.jcore.storage.sql.SQLInstaller;
import com.hotmail.joatin37.jcore.storage.sql.SQLManager;
import com.hotmail.joatin37.jcore.util.myLock;
import com.hotmail.joatin37.jcore.website.ConnectionHandler;
import com.hotmail.joatin37.jcore.website.WebPage;

public final class Core extends JavaPlugin implements ICore, Listener, Runnable {

	private final HashMap<String, LandHandler> landHandlers;
	private final HashMap<String, WebPage> webpages;
	private final CollectionManager manager;
	private FileConfiguration config = null;
	private File configfile = null;
	private boolean skipsave = false;
	private ConnectionHandler webSite;
	private static boolean DEBUGG = false;
	private GraphCollector metrics;
	private SQLManager sql;
	private static Core core;
	private Lang lang;
	private boolean usingCustomLang;
	private LangTag defaultLanguage = LangTag.enUS;
	private IEconomy econ;
	public static final myLock lock = new myLock(true);
	public static String MASTERTHREAD;
	private Economy eco;
	private PlayerCommandHandler playercommand;
	private ServerCommandHandler servercommand;
	private SQLInstaller installer;

	public Core() {
		DEBUGG = true;
		this.webpages = new HashMap<String, WebPage>();
		this.landHandlers = new HashMap<String, LandHandler>();
		this.manager = new CollectionManager(this, this);
	}

	@Override
	public SQL getSQL(JavaPlugin plugin) {
		return this.sql.getSQL(plugin);
	}

	public static ICore hook(JCoreExtension extension) {
		return core;
	}

	public boolean isCustomLang() {
		return this.usingCustomLang;
	}

	public IEconomy getIEconomy() {
		return this.econ;
	}

	public LangTag defLanguage() {
		return this.defaultLanguage;
	}

	public LandHandler getExtension(String plugin) {
		LandHandler ex = this.landHandlers.get(plugin);
		if (ex == null && this.getConfig().getBoolean("safemode", true)) {
			this.getLogger()
					.severe(plugin
							+ " was missing, its collections and/or plots couldn't be created, shuting down. If you want to continue anyway, disable \"safemode\" in the coreconfig.yml");
			this.skipsave = true;
			this.getServer().shutdown();
			return null;
		} else {
			return ex;
		}
	}

	public static boolean isDebugg() {
		return DEBUGG;
	}

	public Set<String> getExtensionNames() {
		return Collections.unmodifiableSet(this.landHandlers.keySet());
	}

	@Override
	public void addExtension(LandHandler landHandler, JavaPlugin plugin) {
		this.landHandlers.put(plugin.getName(), landHandler);
		this.getLogger().info(
				plugin.getDescription().getName() + " "
						+ plugin.getDescription().getVersion()
						+ " was succesfully hooked!");
	}

	public void addWebPage(WebPage page) {
		this.webpages.put(page.getName(), page);
	}

	@Override
	public void onLoad() {
		this.playercommand = new PlayerCommandHandler(this);
		this.servercommand = new ServerCommandHandler(this);
		MASTERTHREAD = Thread.currentThread().getName();
		Core.core = this;
		this.lang = new Lang(this);
		if (this.getConfig().getBoolean("sql.enable", false)) {
			try {
				this.sql = new SQLManager(this);
			} catch (SQLException e) {
				Lang.sendConsoleWarningMessage(this, "AC");
			}
			Core.sendDebug("Created the sql instance");
		} else {
			Core.sendDebug("Didn't create the sql instance");
		}
		this.sql.getSQL(this).QuickStoragePrivate().put("string", "hello");
		this.sql.getSQL(this).QuickStoragePrivate().put("number", 32.001d);
		Core.sendDebug(this.sql.getSQL(this).QuickStoragePrivate()
				.getString("string"));
		try {
			Core.sendDebug(""
					+ this.sql.getSQL(this).QuickStoragePrivate()
							.getNumber("number").doubleValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.sql.getSQL(this).QuickStoragePrivate().remove("number");
		this.sql.getSQL(this).QuickStoragePrivate().remove("string");

	}

	public static void sendDebug(String message) {
		Core.lock.lock();
		try {
			if (Core.core != null && Core.isDebugg()) {
				core.getLogger().info("[DEBUG] " + message);
			}
		} finally {
			Core.lock.unlock();
		}
	}

	@Override
	public void onEnable() {
		// Making a COOOL intro

		if (Core.isDebugg()) {
			this.getServer()
					.getConsoleSender()
					.sendMessage(
							ChatColor.GREEN
									+ "\n\n"
									+ ""
									+ " ============================================================================== \n"
									+ "\n"
									+ "         #  #####                 #\n"
									+ "         #  #                     #                       #\n"
									+ "         #  #                     #                                     #\n"
									+ "         #  #     ##### # # ##    #      #####   #   ###  # #   #  #### #\n"
									+ "         #  #     #   # ## #  #   #      #   #  # #  #  # # ##  # #     #\n"
									+ "     #   #  #     #   # #  ####   #      #   # ##### #  # # # # # #  ## #\n"
									+ "     #   #  #     #   # #  #      #      #   # #   # #  # # #  ## #   #\n"
									+ "      ###   ##### ##### #   ##    ###### ##### #   # ###  # #   #  ###  #\n"
									+ "\n"
									+ " ============================================================================== ");
		}
		// End of intro
		this.getServer().getScheduler().runTaskTimer(this, this, 1, 1);
		this.saveDefaultConfig();
		this.manager.onInit();
		this.webSite = new ConnectionHandler(this);
		this.metrics = new GraphCollector(this);
		Core.sendDebug(Lang.getConsoleMessageSentence(this, "AA"));
		this.loadEconomy();
		this.eco = new Economy(this);
	}

	@Override
	public void onDisable() {
		this.save();
	}

	@Override
	public ICollectionManager getManager() {
		return this.manager;
	}

	public void save() {
		this.saveConfig();
		this.manager.save();
		if (this.webSite != null) {
		}
	}

	private static boolean existsPlugin(String... packages) {
		try {
			for (String pkg : packages) {
				Class.forName(pkg);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void hookEconomy(String name, Class<? extends IEconomy> hookClass,
			String... packages) {
		try {
			if (existsPlugin(packages)) {
				if (this.econ == null) {
					Lang.sendConsoleInfoMessage(this, "AD", name);
					this.econ = hookClass.getConstructor(Core.class)
							.newInstance(this);
				} else {
					Lang.sendConsoleInfoMessage(this, "AE", name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadEconomy() {
		this.hookEconomy("MultiCurrency", Economy_MultiCurrency.class,
				"me.ashtheking.currency.Currency",
				"me.ashtheking.currency.CurrencyList");

		this.hookEconomy("MineConomy", Economy_MineConomy.class,
				"me.mjolnir.mineconomy.MineConomy");

		this.hookEconomy("AEco", Economy_AEco.class, "org.neocraft.AEco.AEco");

		this.hookEconomy("McMoney", Economy_McMoney.class,
				"boardinggamer.mcmoney.McMoneyAPI");

		this.hookEconomy("CraftConomy", Economy_Craftconomy.class,
				"me.greatman.Craftconomy.Craftconomy");

		this.hookEconomy("CraftConomy3", Economy_Craftconomy3.class,
				"com.greatmancode.craftconomy3.BukkitLoader");

		this.hookEconomy("eWallet", Economy_eWallet.class,
				"me.ethan.eWallet.ECO");

		this.hookEconomy("3co", Economy_3co.class, "me.ic3d.eco.ECO");

		this.hookEconomy("BOSEconomy6", Economy_BOSE6.class,
				"cosine.boseconomy.BOSEconomy",
				"cosine.boseconomy.CommandManager");

		this.hookEconomy("BOSEconomy7", Economy_BOSE7.class,
				"cosine.boseconomy.BOSEconomy",
				"cosine.boseconomy.CommandHandler");

		this.hookEconomy("CurrencyCore", Economy_CurrencyCore.class,
				"is.currency.Currency");

		this.hookEconomy("Gringotts", Economy_Gringotts.class,
				"org.gestern.gringotts.Gringotts");

		this.hookEconomy("Essentials Economy", Economy_Essentials.class,
				"com.earth2me.essentials.api.Economy",
				"com.earth2me.essentials.api.NoLoanPermittedException",
				"com.earth2me.essentials.api.UserDoesNotExistException");

		this.hookEconomy("iConomy 4", Economy_iConomy4.class,
				"com.nijiko.coelho.iConomy.iConomy",
				"com.nijiko.coelho.iConomy.system.Account");

		this.hookEconomy("iConomy 5", Economy_iConomy5.class,
				"com.iConomy.iConomy", "com.iConomy.system.Account",
				"com.iConomy.system.Holdings");

		this.hookEconomy("iConomy 6", Economy_iConomy6.class,
				"com.iCo6.iConomy");

		this.hookEconomy("EconXP", Economy_EconXP.class,
				"ca.agnate.EconXP.EconXP");

		this.hookEconomy("GoldIsMoney", Economy_GoldIsMoney.class,
				"com.flobi.GoldIsMoney.GoldIsMoney");

		this.hookEconomy("GoldIsMoney2", Economy_GoldIsMoney2.class,
				"com.flobi.GoldIsMoney2.GoldIsMoney");

		this.hookEconomy("Dosh", Economy_Dosh.class, "com.gravypod.Dosh.Dosh");

		this.hookEconomy("CommandsEX", Economy_CommandsEX.class,
				"com.github.zathrus_writer.commandsex.api.EconomyAPI");

		this.hookEconomy("SDFEconomy", Economy_SDFEconomy.class,
				"com.github.omwah.SDFEconomy.SDFEconomy");

		this.hookEconomy("XPBank", Economy_XPBank.class,
				"com.gmail.mirelatrue.xpbank.XPBank");
	}

	@Override
	public void run() {
		try {
			Core.lock.masterUnlock();
		} catch (Exception e) {

		} finally {
			Core.lock.masterLock();
		}

	}

	public IEconomy getEconomy() {
		return this.econ;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender instanceof Player) {
			this.playercommand.onCommand((Player) sender, cmd, label, args);
		} else {
			this.servercommand.onCommand(sender, cmd, label, args);
		}
		return true;

	}

}
