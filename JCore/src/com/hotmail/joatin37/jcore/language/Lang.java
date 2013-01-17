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

package com.hotmail.joatin37.jcore.language;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.ChatPaginator;

import com.hotmail.joatin37.jcore.core.Core;

/**
 * This class is made to make localization easier for developers
 * <p>
 * When you request to get a sentence or to send one for the first time it will
 * automatically load the language files inside you plugin. It will search for
 * files inside you lang directory. Your lang files names must be a IETF
 * standard language tag with one region subtag. Examples are en-US, en-EN, or
 * sv-SE. However it will only search for tags that have been added to a list
 * inside this class. So if you wan't to add a language that isn't yet suport
 * please leave a note to the developer and he wil add it as soon as possible.
 * <p>
 * In the case that a language tag you plugin requests is missing in one of your
 * lnaguage files, JCore will send a massege to the console with the tag,
 * plugin, and the language used.
 * 
 * @author Joatin
 * 
 */
public final class Lang {

	private static HashMap<String, HashMap<Tag, Language>> langfiles = new HashMap<String, HashMap<Tag, Language>>();
	private static HashMap<String, JavaPlugin> plugins = new HashMap<String, JavaPlugin>();
	private static HashMap<String, TagReplacer> replacers = new HashMap<String, TagReplacer>();
	private static final TagReplacer DEFAULTREPLACER = new DefaultReplacer();
	private static HashMap<String, Tag> playerlangs = new HashMap<String, Tag>();
	private static Tag deflang = Tag.enUS;
	private static boolean isCustom = false;
	private static Core core;

	/**
	 * The default language for all plugins. This is currently "en-US". All
	 * plugins must have this language.
	 */
	public static final String DEFAULTLANGAUGE = "en-US";

	/**
	 * The file ending for language files. This is equal to ".lang".
	 */
	public static final String FILEENDING = ".lang";

	private static final int LINEWIDTH = 100;

	/**
	 * A enum conatianing the languages that can be used with this class. If you
	 * wan't additional languages in it, contact the developer and he will add
	 * it as soon as he can.
	 * 
	 * @author Joatin
	 * @since 1.0.0
	 */
	public enum Tag {
		svSE("sv-SE", "Svenska Sverige", "Swedish Sweden"), zhTW("zh-TW",
				"中國台灣省，中國", "Traditional-Chinese Taiwan Province of China"), enUK(
				"en-UK", "English United Kingdom", "English United Kingdom"), enUS(
				"en-US", "English United States of America",
				"English United States of America"), deDE("de-DE",
				"Deutsch Deutschland", "German Germany"), esES("es-ES",
				"Españoles Castellano España", "Spanish-Castilian Spain"), esMX(
				"es-MX", "Españoles México", "Spanish Mexico"), frFR("fr-FR",
				"Français France", "French France"), itIT("it-IT",
				"Italiano Italia", "Italian Italy"), jaJP("ja-JP", "日本日本",
				"Japanese Japan"), koKR("ko-KR", "한국 한국 대한민국",
				"Korean Republic of Korea"), plPL("pl-PL", "Polsko Polska",
				"Polish Poland"), ptBR("pt-BR", "Português Brasil",
				"Portuguese Brazil"), ruRU("ru-RU",
				"Русский Русский Федерации", "Russian Russian Federation"), zhCN(
				"zh-CN", "中国中国", "Chinese China");

		private final String tag;
		private final String langname;
		private final String langnameenglish;

		private Tag(String tag, String langname, String langnameenglish) {
			this.tag = tag;
			this.langname = langname;
			this.langnameenglish = langnameenglish;
		}

		@Override
		public String toString() {
			return this.tag;
		}

		/**
		 * Returns the tag as the full language name and region on the localized
		 * language.
		 * 
		 * @return A string with the language
		 * @since 1.0.0
		 */
		public String getLanguageString() {
			return this.langname;
		}

		/**
		 * Returns the tag as the full language name and region in english.
		 * 
		 * @return A string with the language
		 * @since 1.0.0
		 */
		public String GetLanguageStringEnglish() {
			return this.langnameenglish;
		}
	}

	/**
	 * Used to set JCore reference inside the class. If you use this it wont
	 * have any effect.
	 * 
	 * @throws NullPointerException
	 *             if the JCore passed was null
	 * @since 1.0.0
	 */
	public Lang(Core core) {
		if (core == null) {
			throw new NullPointerException();
		}
		if (Lang.core == null) {
			Lang.core = core;
			Lang.deflang = core.defLanguage();
			Lang.isCustom = core.isCustomLang();
		}
	}

	/**
	 * Loads all languages from a plugin.
	 * 
	 * @param plugin
	 *            The plugin to load classes from;
	 * @throws NullPointerException
	 *             If the plugin passed was null
	 * @since 1.0.0
	 * @see JavaPlugin
	 */
	private static void loadPluginLanguages(JavaPlugin plugin) {
		if (plugin == null) {
			throw new NullPointerException("No JavaPlugin");
		}
		Core.sendDebug("Started loading: " + plugin.getName());
		plugins.put(plugin.getName(), plugin);
		langfiles.put(plugin.getName(), new HashMap<Tag, Language>());
		Tag[] tags = Tag.values();
		for (int i = 0; i < tags.length; i++) {
			try {
				Language lang = new Language(Lang.core, plugin,
						tags[i].toString());
				langfiles.get(plugin.getName()).put(tags[i], lang);
				Core.sendDebug("Succesfully added " + tags[i].toString());
			} catch (Exception e) {
				Core.sendDebug("Didn't create " + tags[i].toString());
			}
		}
	}

	private static String getMessage(JavaPlugin plugin, String tag,
			Tag langtag, boolean forceLatin, Object[] replacements) {
		if (plugin == null) {
			throw new NullPointerException(
					"JavaPlugin was null! This is caused by a mallfunctioning plugin");
		}
		if (tag == null) {
			throw new NullPointerException(
					"String tag, was null! This was caused by "
							+ plugin.getName());
		}
		if (tag.length() != 2) {
			throw new RuntimeException(
					"The String tag must be excactly two uppercase letters ranging from A-Z. This was caused by"
							+ plugin.getName());
		}
		if (langtag == null) {
			throw new RuntimeException(
					"Language tag, was null! Please contact JCore's developer about this");
		}
		// If the plugin doesn't exists in the map it will be loaded
		if (!langfiles.containsKey(plugin.getName())) {
			loadPluginLanguages(plugin);
		}
		Language lang = langfiles.get(plugin.getName()).get(langtag);
		if (lang == null) {
			lang = langfiles.get(plugin.getName()).get(DEFAULTLANGAUGE);
			if (lang == null) {
				throw new RuntimeException(
						"The plugin "
								+ plugin.getName()
								+ " doesn't have the default en-US.lang file. You have to remove this plugin from your server since it's making it malfunction.");
			}
		}
		if (!lang.isLatin() && forceLatin) {
			lang = langfiles.get(plugin.getName()).get(DEFAULTLANGAUGE);
			if (lang == null) {
				throw new RuntimeException(
						"The plugin "
								+ plugin.getName()
								+ " doesn't have the default en-US.lang file. You have to remove this plugin from your server since it's making it malfunction.");
			}
		}
		String message = lang.get(tag);
		if (message == null) {
			if (plugin.getName().equals("JCore")) {
				core.getLogger().info("Language error");
			} else {
				core.getLogger().warning(
						Lang.getConsoleMessageSentence(core, "AB")
								.replace("[plugin]", plugin.getName())
								.replace("[tag]", tag));
			}

		}
		message = message.trim();
		TagReplacer rep = replacers.get(plugin.getName());
		if (rep == null) {
			return DEFAULTREPLACER.doReplace(message, tag, replacements);
		} else {
			return rep.doReplace(message, tag, replacements);
		}

	}

	/**
	 * Returns a message that has been broken up to better fit into the chat.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the message
	 * @param player
	 *            The player to send to, used for choosing the language
	 * @param replacements
	 *            A list of objects that are used for replacing tags in the
	 *            message
	 * @return A broken up string
	 * @throws NullPointerException
	 *             If plugin, tag or player was null
	 * @since 1.0.0
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, String player, Object... replacements) {
		if (plugin == null || tag == null || player == null) {
			throw new NullPointerException();
		}
		Core.lock.lock();
		try {
			Tag lang = playerlangs.get(player);
			if (lang == null) {
				lang = deflang;
			}
			return ChatPaginator.wordWrap(
					Lang.getMessage(plugin, tag, lang, false, replacements),
					LINEWIDTH);
		} finally {
			Core.lock.unlock();
		}

	}

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Object... replacements) {
		Core.lock.lock();
		try {
			return Lang.getMessage(plugin, tag, deflang, true, replacements)
					.replaceAll("§0", "").replaceAll("§1", "")
					.replaceAll("§2", "").replaceAll("§3", "")
					.replaceAll("§4", "").replaceAll("§5", "")
					.replaceAll("§6", "").replaceAll("§7", "")
					.replaceAll("§8", "").replaceAll("§9", "")
					.replaceAll("§a", "").replaceAll("§b", "")
					.replaceAll("§c", "").replaceAll("§d", "")
					.replaceAll("§e", "").replaceAll("§f", "")
					.replaceAll("§k", "").replaceAll("§l", "")
					.replaceAll("§m", "").replaceAll("§n", "")
					.replaceAll("§o", "").replaceAll("§r", "");
		} finally {
			Core.lock.unlock();
		}

	}

	public static void sendPlayerMessage(JavaPlugin plugin, String tag,
			Player player, Object... replacements) {

		player.sendMessage(Lang.getPlayerMessageSentence(plugin, tag,
				player.getName(), replacements));

	}

	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			Object... replacements) {

		plugin.getLogger().info(
				Lang.getConsoleMessageSentence(plugin, tag, replacements));

	}

	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			Object... replacements) {

		plugin.getLogger().warning(
				Lang.getConsoleMessageSentence(plugin, tag, replacements));

	}

	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			Object... replacements) {

		plugin.getLogger().severe(
				Lang.getConsoleMessageSentence(plugin, tag, replacements));

	}

	public static List<String> getPluginsUsing() {
		Core.lock.lock();
		try {
			return new Vector<String>(langfiles.keySet());
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Returns the language tag used for that player. If the player doesnt have
	 * specified its own it will return the default tag.
	 * 
	 * @param player
	 *            The player to request the tag for.
	 * @return The tag used for that player
	 * @throws NullPointerException
	 *             If player was null
	 * @since 1.0.0
	 */
	public static Tag getPlayerLanguage(String player) {
		if (player == null) {
			throw new NullPointerException();
		}
		Core.lock.lock();
		try {
			if (playerlangs.get(player) != null) {
				return playerlangs.get(player);
			} else {
				return deflang;
			}
		} finally {
			Core.lock.unlock();
		}
	}

}
