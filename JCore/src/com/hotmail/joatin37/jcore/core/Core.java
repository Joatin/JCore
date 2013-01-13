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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.language.Lang;
import com.hotmail.joatin37.jcore.sql.SQLManager;
import com.hotmail.joatin37.jcore.util.GraphCollector;
import com.hotmail.joatin37.jcore.website.WebPage;
import com.hotmail.joatin37.jcore.website.WebSite;
import com.hotmail.joatin37.jcore.worldmap.CollectionManager;
import com.hotmail.joatin37.jcore.worldmap.ICollectionManager;
import com.hotmail.joatin37.jcore.worldmap.LandHandler;

public final class Core extends JavaPlugin implements ICore, Listener {

	private final HashMap<String, LandHandler> landHandlers;
	private final HashMap<String, WebPage> webpages;
	private final CollectionManager manager;
	private FileConfiguration config = null;
	private File configfile = null;
	private boolean skipsave = false;
	private WebSite webSite;
	private static boolean DEBUGG = false;
	private GraphCollector metrics;
	private SQLManager sql;
	private static Core core;
	private Lang lang;
	private boolean usingCustomLang;
	private String defaultLanguage;

	public Core() {
		DEBUGG = true;
		this.webpages = new HashMap<String, WebPage>();
		this.landHandlers = new HashMap<String, LandHandler>();
		this.manager = new CollectionManager(this, this);
	}

	public boolean isCustomLang() {
		return this.usingCustomLang;
	}

	public String defLanguage() {
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
	}

	public static void sendDebug(String message) {
		if (Core.core != null && Core.isDebugg()) {
			core.getLogger().info("[DEBUG] " + message);
		}
	}

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.manager.onInit();
		if (this.getConfig().getBoolean("website.enabled", false)) {
			this.webSite = new WebSite(this);
		}
		this.metrics = new GraphCollector(this);
		Core.sendDebug(Lang.getConsoleMessageSentence(this, "AA"));
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
}