package com.hotmail.joatin37.jcore;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import net.minecraft.server.v1_4_6.Block;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lang {

	private static HashMap<String, HashMap<String, HashMap<String, String>>> langfiles = new HashMap<String, HashMap<String, HashMap<String, String>>>();
	private static HashMap<String, JavaPlugin> plugins;
	private static String deflang = "en-US";
	private static boolean isCustom = false;
	private static Core core;
	public static final String DEFAULTLANGAUGE = "en-US";

	protected void setCustom(boolean bool) {

	}

	protected void setup(Core core, String tag, boolean iscustom) {
		if (tag == null) {
			throw new NullPointerException(
					"The tag containing the language to use was null");
		}
		if (core == null) {
			throw new NullPointerException("Core was null");
		}
		Lang.core = core;
		Lang.isCustom = iscustom;
		Lang.deflang = tag;
	}

	protected void setDefLang(String tag) {
		if (tag == null) {
			throw new NullPointerException();
		}
		deflang = tag;
	}

	private static void loadPluginLanguages(JavaPlugin plugin) {
		if (plugin == null) {
			throw new NullPointerException("No JavaPlugin");
		}
		plugins.put(plugin.getName(), plugin);
		InputStream input = plugin
				.getResource("lang" + File.separator + "enUS");
		if (input != null) {
			Core.sendDebug("Found the en-US file");
		} else {
			Core.sendDebug("Couldnt create the en-US file");
		}

		// TODO
	}

	private final static String[] MessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Player player, Block block) {
		return MessageSentence(plugin, tag, deflang, entity, player, block);

	}

	private String parseBlock(Block block) {
		if (block == null) {
			return "block";
		}
		return null;
	}

	private String parseBlock(Entity entity) {
		if (entity == null) {
			return "entity";
		}
		return null;
	}

	private final static String[] MessageSentence(JavaPlugin plugin,
			String tag, String langtag, Entity entity, Player player,
			Block block) {
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

		}

		return null;

	}

	private static void doSendMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity, Player repplayer) {

	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Player player, Block block) {
		return MessageSentence(plugin, tag, entity, player, block);
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		return MessageSentence(plugin, tag, entity, null, block);
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Player player, Block block) {
		return MessageSentence(plugin, tag, null, player, block);
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Block block) {
		return MessageSentence(plugin, tag, null, null, block);
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Player player) {
		return MessageSentence(plugin, tag, entity, player, null);
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Entity entity) {
		return MessageSentence(plugin, tag, entity, null, null);
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag, Player player) {
		return MessageSentence(plugin, tag, null, player, null);
	}

	public static String[] getPlayerMessageSentence(JavaPlugin plugin,
			String tag) {
		return MessageSentence(plugin, tag, null, null, null);
	}

	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity, Player repplayer, Block block) {
		// TODO
	}

	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag) {
		// TODO
	}

	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity) {
		// TODO
	}

	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Player repplayer) {
		// TODO
	}

	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Block block) {
		// TODO
	}

	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Entity entity, Block block) {
		// TODO
	}

	public static void sendPlayerMessage(JavaPlugin plugin, Player player,
			String tag, Player repplayer, Block block) {
		// TODO
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Player player, Block block) {
		return MessageSentence(plugin, tag, entity, player, block);
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		return MessageSentence(plugin, tag, entity, null, block);
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Player player, Block block) {
		return MessageSentence(plugin, tag, null, player, block);
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Block block) {
		return MessageSentence(plugin, tag, null, null, block);
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Player player) {
		return MessageSentence(plugin, tag, entity, player, null);
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity) {
		return MessageSentence(plugin, tag, entity, null, null);
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Player player) {
		return MessageSentence(plugin, tag, null, player, null);
	}

	public static String[] getConsoleMessageSentence(JavaPlugin plugin,
			String tag) {
		return MessageSentence(plugin, tag, null, null, null);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin,
			String tag, Entity entity, Player player, Block block) {
		return MessageSentence(plugin, tag, entity, player, block);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		return MessageSentence(plugin, tag, entity, null, block);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin,
			String tag, Player player, Block block) {
		return MessageSentence(plugin, tag, null, player, block);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin,
			String tag, Block block) {
		return MessageSentence(plugin, tag, null, null, block);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin,
			String tag, Entity entity, Player player) {
		return MessageSentence(plugin, tag, entity, player, null);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin,
			String tag, Entity entity) {
		return MessageSentence(plugin, tag, entity, null, null);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin,
			String tag, Player player) {
		return MessageSentence(plugin, tag, null, player, null);
	}

	public static String[] sendConsoleInfoMessage(JavaPlugin plugin, String tag) {
		return MessageSentence(plugin, tag, null, null, null);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag, Entity entity, Player player, Block block) {
		return MessageSentence(plugin, tag, entity, player, block);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		return MessageSentence(plugin, tag, entity, null, block);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag, Player player, Block block) {
		return MessageSentence(plugin, tag, null, player, block);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag, Block block) {
		return MessageSentence(plugin, tag, null, null, block);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag, Entity entity, Player player) {
		return MessageSentence(plugin, tag, entity, player, null);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag, Entity entity) {
		return MessageSentence(plugin, tag, entity, null, null);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag, Player player) {
		return MessageSentence(plugin, tag, null, player, null);
	}

	public static String[] sendConsoleWarningMessage(JavaPlugin plugin,
			String tag) {
		return MessageSentence(plugin, tag, null, null, null);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag, Entity entity, Player player, Block block) {
		return MessageSentence(plugin, tag, entity, player, block);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		return MessageSentence(plugin, tag, entity, null, block);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag, Player player, Block block) {
		return MessageSentence(plugin, tag, null, player, block);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag, Block block) {
		return MessageSentence(plugin, tag, null, null, block);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag, Entity entity, Player player) {
		return MessageSentence(plugin, tag, entity, player, null);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag, Entity entity) {
		return MessageSentence(plugin, tag, entity, null, null);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag, Player player) {
		return MessageSentence(plugin, tag, null, player, null);
	}

	public static String[] sendConsoleSevereMessage(JavaPlugin plugin,
			String tag) {
		return MessageSentence(plugin, tag, null, null, null);
	}
}
