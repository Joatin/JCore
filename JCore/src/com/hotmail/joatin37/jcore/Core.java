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

package com.hotmail.joatin37.jcore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.api.Extension;
import com.hotmail.joatin37.jcore.api.ICollectionManager;
import com.hotmail.joatin37.jcore.api.ICore;
import com.hotmail.joatin37.jcore.api.WebPage;
import com.hotmail.joatin37.jcore.website.WebSite;

public final class Core extends JavaPlugin implements ICore, Listener {

	private final HashMap<String, Extension> extensions;
	private final HashMap<String, WebPage> webpages;
	private final CollectionManager manager;
	private FileConfiguration config = null;
	private File configfile = null;
	private final JavaPlugin plugin;
	private boolean skipsave = false;
	private BlockEditMode editmode;
	private WebSite webSite;

	public Core(JavaPlugin plugin) {
		this.plugin = plugin;
		this.webpages = new HashMap<String, WebPage>();
		this.extensions = new HashMap<String, Extension>();
		this.manager = new CollectionManager(this.plugin, this);
	}

	public void sendCoreInfoMessage(String message) {
		this.plugin.getLogger().info("[Core] " + message);
	}

	public void sendCoreWarningMessage(String message) {
		this.plugin.getLogger().warning("[Core] " + message);
	}

	public void sendCoreSevereMessage(String message) {
		this.plugin.getLogger().severe("[Core] " + message);
	}

	public Extension getExtension(String plugin) {
		Extension ex = this.extensions.get(plugin);
		if (ex == null && this.getConfig().getBoolean("safemode", true)) {
			this.sendCoreSevereMessage(plugin
					+ " was missing, its collections and/or plots couldn't be created, shuting down. If you want to continue anyway, disable \"safemode\" in the coreconfig.yml");
			this.skipsave = true;
			this.plugin.getServer().shutdown();
			return null;
		} else {
			return ex;
		}
	}

	public JavaPlugin getPlugin() {
		return this.plugin;
	}

	@Override
	public void addExtension(Extension extension, JavaPlugin plugin) {
		this.extensions.put(plugin.getName(), extension);
	}

	public void addWebPage(WebPage page) {
		this.webpages.put(page.getName(), page);
	}

	public void init() {
		this.manager.onInit();
		this.editmode = new BlockEditMode(this);
		this.reloadConfig();
		if (this.getConfig().getBoolean("website", false)) {
			this.webSite = new WebSite(this);
		}

	}

	public void reloadConfig() {
		if (this.configfile == null) {
			this.configfile = new File(this.plugin.getDataFolder(),
					"coreconfig.yml");
		}
		this.config = YamlConfiguration.loadConfiguration(this.configfile);

		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("coreconfig.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			this.config.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (this.config == null) {
			this.reloadConfig();
		}
		return this.config;
	}

	public void saveConfig() {
		if (this.config == null || this.configfile == null) {
			return;
		}
		try {
			this.getConfig().save(this.configfile);
		} catch (IOException ex) {
			this.plugin.getLogger().log(Level.SEVERE,
					"Could not save config to " + this.configfile, ex);
		}
	}

	@Override
	public ICollectionManager getManager() {
		return this.manager;
	}

	public void save() {
		this.saveConfig();
		this.manager.save();
		if (this.webSite != null) {
			this.webSite.save();
		}
	}
}
