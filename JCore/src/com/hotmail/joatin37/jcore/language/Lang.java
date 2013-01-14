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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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

	private static HashMap<String, HashMap<String, HashMap<String, String>>> langfiles = new HashMap<String, HashMap<String, HashMap<String, String>>>();
	private static HashMap<String, JavaPlugin> plugins = new HashMap<String, JavaPlugin>();
	private static String deflang = "en-US";
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

	/**
	 * This is a list of all language tags, following the IETF standard with one
	 * region tag, that this class will try to load from each plugin. If you
	 * wan't to add a language that is missing here please contact the developer
	 * and he will add it as soon as possible.
	 * <p>
	 * The current tags in the list are:
	 * <p>
	 * en-US, se-SV
	 */
	public static final String[] SUPORTEDLANGS = new String[] { "en-US",
			"sv-SE", "zh-TW" };

	/**
	 * Used to set JCore reference inside your class. If you use this it wont
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
		langfiles.put(plugin.getName(),
				new HashMap<String, HashMap<String, String>>());
		for (int i = 0; i < SUPORTEDLANGS.length; i++) {
			InputStream input = plugin.getResource("lang/" + SUPORTEDLANGS[i]
					+ FILEENDING);
			if (input != null) {
				Core.sendDebug("Found the " + SUPORTEDLANGS[i] + ".lang file");
			} else {
				if (SUPORTEDLANGS[i].equals(DEFAULTLANGAUGE)) {
					Core.sendDebug("Couldn't find the en-US file! You must make one else you plugin wont work!");
				}
				continue;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			langfiles.get(plugin.getName()).put(SUPORTEDLANGS[i],
					new HashMap<String, String>());
			String s;
			try {
				while ((s = reader.readLine()) != null) {
					if (!s.startsWith("#")) {
						String[] splits = s.split(":", 2);
						if (splits.length == 2) {
							langfiles
									.get(plugin.getName())
									.get(SUPORTEDLANGS[i])
									.put(splits[0].toUpperCase().trim(),
											splits[1]);
						}

					}
				}
			} catch (IOException e) {
				core.getLogger().severe(
						"ERROR: an error occured while reading the "
								+ SUPORTEDLANGS[i]
								+ ".lang file, belonging to "
								+ plugin.getName());
			}
			try {
				reader.close();
			} catch (IOException e) {
			}
		}

		// TODO
	}

	/**
	 * Returns a sentence from a language file.
	 * 
	 * @param plugin
	 *            The plugin to get the sentence from
	 * @param tag
	 *            A tag specifying which sentence to retrieve.
	 * @param entity
	 *            If the the sentence contains a entity tag then it will be
	 *            replaced with this entity.
	 * @param player
	 *            If the the sentence contains a player tag then it will be
	 *            replaced with this player.
	 * @param block
	 *            If the the sentence contains a block tag then it will be
	 *            replaced with this block.
	 * @return A String with the sentence.
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see Player
	 * @see Block
	 */
	private final static String MessageSentence(JavaPlugin plugin, String tag,
			Entity entity, OfflinePlayer player, Block block) {
		return MessageSentence(plugin, tag, deflang, entity, player, block);

	}

	/**
	 * Parses the Block passed to it. It replaces wrong names with more user
	 * friendly ones. For example "BURNING_FURNACE" with just "furnace". Returns
	 * just "block" if the Block passed was null.
	 * 
	 * @param block
	 *            The block to parse.
	 * @return A String with a user friendly name of the block
	 * @since 1.0.0
	 * @see Block
	 */
	private static String parseBlock(Block block) {
		if (block == null) {
			return "block";
		}
		return block.getType().toString();
	}

	/**
	 * Returns a more user friandly name of this entity. For example it replaces
	 * "SNOWMAN" with "snow golem". It returns just "entity" if the Entity
	 * passed was null;
	 * 
	 * @param entity
	 *            The entity to parse.
	 * @return A more user friendly name of this entity
	 * @since 1.0.0
	 * @see Entity
	 */
	private static String parseEntity(Entity entity) {
		if (entity == null) {
			return "entity";
		}
		return entity.getType().toString();
	}

	/**
	 * Returns the name of the plugin, or just "plugin" if the JavaPlugin passed
	 * was null.
	 * 
	 * @param plugin
	 *            the plugin to get the name from
	 * @return The name of the plugin, or "plugin" if the plugin passed was null
	 * @since 1.0.0
	 * @See JavaPlugin
	 */
	private static String parsePlugin(JavaPlugin plugin) {
		if (plugin == null) {
			return "plugin";
		} else {
			return plugin.getName();
		}
	}

	/**
	 * Returns the name of the player, or "player" if the OfflinePlayer passed
	 * was null.
	 * 
	 * @param player
	 *            The player to get the name from
	 * @return The players name, or "player" if the player passed was null.
	 * @since 1.0.0
	 * @see OfflinePlayer
	 */
	private static String parsePlayer(OfflinePlayer player) {
		if (player == null) {
			return "player";
		} else {
			return player.getName();
		}
	}

	/**
	 * Returns a sentence from a language file.
	 * 
	 * @param plugin
	 *            The plugin to get the sentence from
	 * @param tag
	 *            A tag specifying which sentence to retrieve.
	 * @param langtag
	 *            The language to use, the langtag must be of the IETF standard
	 *            with only one subtag containing the region
	 * @param entity
	 *            If the the sentence contains a entity tag then it will be
	 *            replaced with this entity.
	 * @param player
	 *            If the the sentence contains a player tag then it will be
	 *            replaced with this player.
	 * @param block
	 *            If the the sentence contains a block tag then it will be
	 *            replaced with this block.
	 * @return A String with the sentence.
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see Player
	 * @see Block
	 */
	private final static String MessageSentence(JavaPlugin plugin, String tag,
			String langtag, Entity entity, OfflinePlayer player, Block block) {
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

		if (!langfiles.containsKey(plugin.getName())) {
			loadPluginLanguages(plugin);
		}
		HashMap<String, String> lang = langfiles.get(plugin.getName()).get(
				deflang);
		if (lang == null) {
			lang = langfiles.get(plugin.getName()).get(DEFAULTLANGAUGE);
			if (lang == null) {
				throw new RuntimeException(
						"The plugin "
								+ plugin.getName()
								+ " doesn't have the default en-US.lang file. You have to remove this plugin from your server since it's making it malfunction.");
			}
		}
		String tag1 = lang.get(tag);
		if (tag1 == null) {
			if (plugin.getName().equals("JCore")) {
				core.getLogger().info("Language error");
			} else {
				core.getLogger().warning(
						Lang.getConsoleMessageSentence(core, "AB")
								.replace("[plugin]", plugin.getName())
								.replace("[tag]", tag));
			}

		}
		tag1 = tag1.replace("[tag]", tag)
				.replace("[plugin]", Lang.parsePlugin(plugin))
				.replace("[entity]", Lang.parseEntity(entity))
				.replace("[player]", Lang.parsePlayer(player))
				.replace("[block]", Lang.parseBlock(block));
		return tag1;

	}

	private static String[] PlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, OfflinePlayer player, Block block) {
		return ChatPaginator.wordWrap(
				MessageSentence(plugin, tag, entity, player, block), 100);
	}

	private static String ConsoleMessageSenetence(JavaPlugin plugin,
			String tag, Entity entity, OfflinePlayer player, Block block) {
		return MessageSentence(plugin, tag, entity, player, block)
				.replaceAll("§0", "").replaceAll("§1", "").replaceAll("§2", "")
				.replaceAll("§3", "").replaceAll("§4", "").replaceAll("§5", "")
				.replaceAll("§6", "").replaceAll("§7", "").replaceAll("§8", "")
				.replaceAll("§9", "").replaceAll("§a", "").replaceAll("§b", "")
				.replaceAll("§c", "").replaceAll("§d", "").replaceAll("§e", "")
				.replaceAll("§f", "").replaceAll("§k", "").replaceAll("§l", "")
				.replaceAll("§m", "").replaceAll("§n", "").replaceAll("§o", "")
				.replaceAll("§r", "");
	}

	/**
	 * Sends a message to the player.
	 * 
	 * @param plugin
	 *            The plugin requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentnce to send
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param repplayer
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 * @see OfflinePlayer
	 * @see Entity
	 * @see Block
	 */
	private static void doSendMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity, OfflinePlayer repplayer, Block block) {
		player.sendMessage(ChatPaginator.wordWrap(
				MessageSentence(plugin, tag, entity, player, block), 100));
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Object... replacements) {
		return null;

	}

	public static void sendPlayerMessage(JavaPlugin plugin, String tag,
			Player player, Object... replacements) {

	}

	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			Object... replacements) {

	}

	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			Object... replacements) {

	}

	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			Object... replacements) {

	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, OfflinePlayer player, Block block) {
		return PlayerMessageSentence(plugin, tag, entity, player, block);
	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see Block
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		return PlayerMessageSentence(plugin, tag, entity, null, block);
	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, OfflinePlayer player, Block block) {
		return PlayerMessageSentence(plugin, tag, null, player, block);
	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @param block
	 *            The block to replace the block tag with
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Block
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Block block) {
		return PlayerMessageSentence(plugin, tag, null, null, block);
	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, OfflinePlayer player) {
		return PlayerMessageSentence(plugin, tag, entity, player, null);
	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity) {
		return PlayerMessageSentence(plugin, tag, entity, null, null);
	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @param player
	 *            The player to replace the player tag with
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, OfflinePlayer player) {
		return PlayerMessageSentence(plugin, tag, null, player, null);
	}

	/**
	 * Returns a String array with lines that is equal to one full line in the
	 * chat. This might be usefull if you want to do something more with the
	 * message before sending it.
	 * 
	 * @param plugin
	 *            The plugin requesting the sentence
	 * @param tag
	 *            The tag of the sentence requested.
	 * @return A String array with lines that are equal to one full line in the
	 *         chat
	 * @since 1.0.0
	 * @see JavaPlugin
	 */
	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag) {
		return PlayerMessageSentence(plugin, tag, null, null, null);
	}

	/**
	 * Sends a message to the player
	 * 
	 * @param plugin
	 *            The plugin with requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentence to use
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param repplayer
	 *            The player to replace the player tag with
	 * @param block
	 *            The bloc to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 * @see OfflinePlayer
	 * @see Entity
	 * @see Block
	 */
	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity, OfflinePlayer repplayer, Block block) {
		player.sendMessage(MessageSentence(plugin, tag, entity, repplayer,
				block));
	}

	/**
	 * Sends a message to the player
	 * 
	 * @param plugin
	 *            The plugin with requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentence to use
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 */
	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag) {
		doSendMessage(plugin, player, tag, null, null, null);
	}

	/**
	 * Sends a message to the player
	 * 
	 * @param plugin
	 *            The plugin with requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentence to use
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 * @see Entity
	 */
	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity) {
		doSendMessage(plugin, player, tag, entity, null, null);
	}

	/**
	 * Sends a message to the player
	 * 
	 * @param plugin
	 *            The plugin with requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentence to use
	 * @param repplayer
	 *            The player to replace the player tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 * @see OfflinePlayer
	 */
	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, OfflinePlayer repplayer) {
		doSendMessage(plugin, player, tag, null, repplayer, null);
	}

	/**
	 * Sends a message to the player
	 * 
	 * @param plugin
	 *            The plugin with requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentence to use
	 * @param block
	 *            The bloc to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 * @see Block
	 */
	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Block block) {
		doSendMessage(plugin, player, tag, null, null, block);
	}

	/**
	 * Sends a message to the player
	 * 
	 * @param plugin
	 *            The plugin with requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentence to use
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param block
	 *            The bloc to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 * @see Entity
	 * @see Block
	 */
	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity, Block block) {
		doSendMessage(plugin, player, tag, entity, null, block);
	}

	/**
	 * Sends a message to the player
	 * 
	 * @param plugin
	 *            The plugin with requesting this
	 * @param player
	 *            The player to send to
	 * @param tag
	 *            The tag of the sentence to use
	 * @param repplayer
	 *            The player to replace the player tag with
	 * @param block
	 *            The bloc to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Player
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, OfflinePlayer repplayer, Block block) {
		doSendMessage(plugin, player, tag, null, repplayer, block);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, OfflinePlayer player, Block block) {
		return ConsoleMessageSenetence(plugin, tag, entity, player, block);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see Block
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		return ConsoleMessageSenetence(plugin, tag, entity, null, block);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, OfflinePlayer player, Block block) {
		return ConsoleMessageSenetence(plugin, tag, null, player, block);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @param block
	 *            The block to replace the block tag with
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Block
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Block block) {
		return ConsoleMessageSenetence(plugin, tag, null, null, block);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, OfflinePlayer player) {
		return ConsoleMessageSenetence(plugin, tag, entity, player, null);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity) {
		return ConsoleMessageSenetence(plugin, tag, entity, null, null);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @param player
	 *            The player to replace the player tag with
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, OfflinePlayer player) {
		return ConsoleMessageSenetence(plugin, tag, null, player, null);
	}

	/**
	 * Returns the sentence requested without colors and bold, italic etc. This
	 * is more suitable for the console. This method is usefull if you want to
	 * edit the message more. Otherwise its suggest that you use the methods for
	 * sending the message directly.
	 * 
	 * @param plugin
	 *            The plugin requesting the message
	 * @param tag
	 *            The tag of the sentence to get
	 * @return Returns a message suitable for the console
	 * @since 1.0.0
	 * @see JavaPlugin
	 */
	public static String getConsoleMessageSentence(JavaPlugin plugin, String tag) {
		return ConsoleMessageSenetence(plugin, tag, null, null, null);
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			Entity entity, OfflinePlayer player, Block block) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, entity, player, block));
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see Block
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			Entity entity, Block block) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, entity, null, block));
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			OfflinePlayer player, Block block) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, null, player, block));
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Block
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			Block block) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, null, null, block));
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			Entity entity, OfflinePlayer player) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, entity, player, null));
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			Entity entity) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, entity, null, null));
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param player
	 *            The player to replace the player tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag,
			OfflinePlayer player) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, null, player, null));
	}

	/**
	 * Sends a message to the console of the level info.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @since 1.0.0
	 * @see JavaPlugin
	 */
	public static void sendConsoleInfoMessage(JavaPlugin plugin, String tag) {
		plugin.getLogger().info(
				ConsoleMessageSenetence(plugin, tag, null, null, null));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			Entity entity, OfflinePlayer player, Block block) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, entity, player, block));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see Block
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			Entity entity, Block block) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, entity, null, block));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			OfflinePlayer player, Block block) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, null, player, block));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Block
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			Block block) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, null, null, block));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			Entity entity, OfflinePlayer player) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, entity, player, null));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			Entity entity) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, entity, null, null));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param player
	 *            The player to replace the player tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag,
			OfflinePlayer player) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, null, player, null));
	}

	/**
	 * Sends a message to the console of the level Warning.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @since 1.0.0
	 * @see JavaPlugin
	 */
	public static void sendConsoleWarningMessage(JavaPlugin plugin, String tag) {
		plugin.getLogger().warning(
				ConsoleMessageSenetence(plugin, tag, null, null, null));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			Entity entity, OfflinePlayer player, Block block) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, entity, player, block));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see Block
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			Entity entity, Block block) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, entity, null, block));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param player
	 *            The player to replace the player tag with
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 * @see Block
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			OfflinePlayer player, Block block) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, null, player, block));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param block
	 *            The block to replace the block tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Block
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			Block block) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, null, null, block));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @param player
	 *            The player to replace the player tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 * @see OfflinePlayer
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			Entity entity, OfflinePlayer player) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, entity, player, null));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param entity
	 *            The entity to replace the entity tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see Entity
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			Entity entity) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, entity, null, null));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @param player
	 *            The player to replace the player tag with
	 * @since 1.0.0
	 * @see JavaPlugin
	 * @see OfflinePlayer
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag,
			OfflinePlayer player) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, null, player, null));
	}

	/**
	 * Sends a message to the console of the level Severe.
	 * 
	 * @param plugin
	 *            The plugin requesting the message and the plugin to send the
	 *            message to
	 * @param tag
	 *            The tag of the sentence requested
	 * @since 1.0.0
	 * @see JavaPlugin
	 */
	public static void sendConsoleSevereMessage(JavaPlugin plugin, String tag) {
		plugin.getLogger().severe(
				ConsoleMessageSenetence(plugin, tag, null, null, null));
	}
}
