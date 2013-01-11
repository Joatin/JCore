package com.hotmail.joatin37.jcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import net.minecraft.server.v1_4_6.Block;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.ChatPaginator;

public final class Lang {

	private static HashMap<String, HashMap<String, HashMap<String, String>>> langfiles = new HashMap<String, HashMap<String, HashMap<String, String>>>();
	private static HashMap<String, JavaPlugin> plugins = new HashMap<String, JavaPlugin>();
	private static String deflang = "en-US";
	private static boolean isCustom = false;
	private static Core core;
	public static final String DEFAULTLANGAUGE = "en-US";
	public static final String FILEENDING = ".lang";
	public static final String[] suportedlangs = new String[] { "en-US",
			"sv-SE" };

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
		Core.sendDebug("Started loading: " + plugin.getName());
		plugins.put(plugin.getName(), plugin);
		langfiles.put(plugin.getName(),
				new HashMap<String, HashMap<String, String>>());
		for (int i = 0; i < suportedlangs.length; i++) {
			InputStream input = plugin.getResource("lang/" + suportedlangs[i]
					+ FILEENDING);
			if (input != null) {
				Core.sendDebug("Found the " + suportedlangs[i] + ".lang file");
			} else {
				if (suportedlangs[i].equals(DEFAULTLANGAUGE)) {
					Core.sendDebug("Couldn't find the en-US file! You must make one else you plugin wont work!");
				}
				continue;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			langfiles.get(plugin.getName()).put(suportedlangs[i],
					new HashMap<String, String>());
			String s;
			try {
				while ((s = reader.readLine()) != null) {
					if (!s.startsWith("#")) {
						String[] splits = s.split(":", 2);
						if (splits.length == 2) {
							langfiles
									.get(plugin.getName())
									.get(suportedlangs[i])
									.put(splits[0].toUpperCase().trim(),
											splits[1]);
						}

					}
				}
			} catch (IOException e) {
				core.getLogger().severe(
						"ERROR: an error occured while reading the "
								+ suportedlangs[i]
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
			core.getLogger().info("error");
		}

		return ChatPaginator.wordWrap(tag1, 100);

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

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Player player, Block block) {
		String[] array = MessageSentence(plugin, tag, entity, player, block);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Block block) {
		String[] array = MessageSentence(plugin, tag, entity, null, block);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Player player, Block block) {
		String[] array = MessageSentence(plugin, tag, null, player, block);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Block block) {
		String[] array = MessageSentence(plugin, tag, null, null, block);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity, Player player) {
		String[] array = MessageSentence(plugin, tag, entity, player, null);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Entity entity) {
		String[] array = MessageSentence(plugin, tag, entity, null, null);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static String getConsoleMessageSentence(JavaPlugin plugin,
			String tag, Player player) {
		String[] array = MessageSentence(plugin, tag, null, player, null);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static String getConsoleMessageSentence(JavaPlugin plugin, String tag) {
		String[] array = MessageSentence(plugin, tag, null, null, null);
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
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
