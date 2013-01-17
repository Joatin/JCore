package com.hotmail.joatin37.jcore.threadsafeserver;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;

import com.avaje.ebean.config.ServerConfig;
import com.hotmail.joatin37.jcore.core.Core;

public class ThreadSafeServer {

	private static Server server;

	public ThreadSafeServer(Core core) {
		if (server == null) {
			server = core.getServer();
		}
	}

	/**
	 * Gets the name of this server implementation
	 * 
	 * @return name of this server implementation
	 */
	public static String getName() {
		Core.lock.lock();
		try {
			return server.getName();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the version string of this server implementation.
	 * 
	 * @return version of this server implementation
	 */
	public static String getVersion() {
		Core.lock.lock();
		try {
			return server.getVersion();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the Bukkit version that this server is running.
	 * 
	 * @return Version of Bukkit
	 */
	public static String getBukkitVersion() {
		Core.lock.lock();
		try {
			return server.getBukkitVersion();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a list of all currently logged in players
	 * 
	 * @return An array of Players that are currently online
	 * 
	 * @deprecated
	 */
	@Deprecated
	public static Player[] getOnlinePlayers() {
		Core.lock.lock();
		try {
			return server.getOnlinePlayers();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get the maximum amount of players which can login to this server
	 * 
	 * @return The amount of players this server allows
	 */
	public static int getMaxPlayers() {
		Core.lock.lock();
		try {
			return server.getMaxPlayers();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get the game port that the server runs on
	 * 
	 * @return The port number of this server
	 */
	public static int getPort() {
		Core.lock.lock();
		try {
			return server.getPort();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get the view distance from this server.
	 * 
	 * @return The view distance from this server.
	 */
	public static int getViewDistance() {
		Core.lock.lock();
		try {
			return server.getViewDistance();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get the IP that this server is bound to or empty string if not specified
	 * 
	 * @return The IP string that this server is bound to, otherwise empty
	 *         string
	 */
	public static String getIp() {
		Core.lock.lock();
		try {
			return server.getIp();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get the name of this server
	 * 
	 * @return The name of this server
	 */
	public static String getServerName() {
		Core.lock.lock();
		try {
			return server.getServerName();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get an ID of this server. The ID is a simple generally alphanumeric ID
	 * that can be used for uniquely identifying this server.
	 * 
	 * @return The ID of this server
	 */
	public static String getServerId() {
		Core.lock.lock();
		try {
			return server.getServerId();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get world type (level-type setting) for default world
	 * 
	 * @return The value of level-type (e.g. DEFAULT, FLAT, DEFAULT_1_1)
	 */
	public static String getWorldType() {
		Core.lock.lock();
		try {
			return server.getWorldType();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get generate-structures setting
	 * 
	 * @return true if structure generation is enabled, false if not
	 */
	public static boolean getGenerateStructures() {
		Core.lock.lock();
		try {
			return server.getGenerateStructures();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets whether this server allows the End or not.
	 * 
	 * @return Whether this server allows the End or not.
	 */
	public static boolean getAllowEnd() {
		Core.lock.lock();
		try {
			return server.getAllowEnd();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets whether this server allows the Nether or not.
	 * 
	 * @return Whether this server allows the Nether or not.
	 */
	public static boolean getAllowNether() {
		Core.lock.lock();
		try {
			return server.getAllowNether();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets whether this server has a whitelist or not.
	 * 
	 * @return Whether this server has a whitelist or not.
	 */
	public static boolean hasWhitelist() {
		Core.lock.lock();
		try {
			return server.hasWhitelist();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Sets the whitelist on or off
	 * 
	 * @param value
	 *            true if whitelist is on, otherwise false
	 */
	public static void setWhitelist(boolean value) {
		Core.lock.lock();
		try {
			server.setWhitelist(value);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a list of whitelisted players
	 * 
	 * @return Set containing all whitelisted players
	 * @deprecated
	 */
	@Deprecated
	public static Set<OfflinePlayer> getWhitelistedPlayers() {
		Core.lock.lock();
		try {
			return server.getWhitelistedPlayers();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Reloads the whitelist from disk
	 */
	public static void reloadWhitelist() {
		Core.lock.lock();
		try {
			server.reloadWhitelist();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Broadcast a message to all players.
	 * <p />
	 * This is the same as calling
	 * {@link #broadcast(java.lang.String, java.lang.String)} to
	 * {@link #BROADCAST_CHANNEL_USERS}
	 * 
	 * @param message
	 *            the message
	 * @return the number of players
	 */
	public static int broadcastMessage(String message) {
		Core.lock.lock();
		try {
			return server.broadcastMessage(message);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the name of the update folder. The update folder is used to safely
	 * update plugins at the right moment on a plugin load.
	 * <p />
	 * The update folder name is relative to the plugins folder.
	 * 
	 * @return The name of the update folder
	 */
	public static String getUpdateFolder() {
		Core.lock.lock();
		try {
			return server.getUpdateFolder();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the update folder. The update folder is used to safely update
	 * plugins at the right moment on a plugin load.
	 * 
	 * @return The name of the update folder
	 * @deprecated
	 */
	@Deprecated
	public static File getUpdateFolderFile() {
		Core.lock.lock();
		try {
			return server.getUpdateFolderFile();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the value of the connection throttle setting
	 * 
	 * @return the value of the connection throttle setting
	 */
	public static long getConnectionThrottle() {
		Core.lock.lock();
		try {
			return server.getConnectionThrottle();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets default ticks per animal spawns value
	 * <p />
	 * <b>Example Usage:</b>
	 * <ul>
	 * <li>A value of 1 will mean the server will attempt to spawn monsters
	 * every tick.
	 * <li>A value of 400 will mean the server will attempt to spawn monsters
	 * every 400th tick.
	 * <li>A value below 0 will be reset back to Minecraft's default.
	 * </ul>
	 * <p />
	 * <b>Note:</b> If set to 0, animal spawning will be disabled. We recommend
	 * using spawn-animals to control this instead.
	 * <p />
	 * Minecraft default: 400.
	 * 
	 * @return The default ticks per animal spawns value
	 */
	public static int getTicksPerAnimalSpawns() {
		Core.lock.lock();
		try {
			return server.getTicksPerAnimalSpawns();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the default ticks per monster spawns value
	 * <p />
	 * <b>Example Usage:</b>
	 * <ul>
	 * <li>A value of 1 will mean the server will attempt to spawn monsters
	 * every tick.
	 * <li>A value of 400 will mean the server will attempt to spawn monsters
	 * every 400th tick.
	 * <li>A value below 0 will be reset back to Minecraft's default.
	 * </ul>
	 * <p />
	 * <b>Note:</b> If set to 0, monsters spawning will be disabled. We
	 * recommend using spawn-monsters to control this instead.
	 * <p />
	 * Minecraft default: 1.
	 * 
	 * @return The default ticks per monsters spawn value
	 */
	public static int getTicksPerMonsterSpawns() {
		Core.lock.lock();
		try {
			return server.getTicksPerMonsterSpawns();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a player object by the given username
	 * <p />
	 * This method may not return objects for offline players
	 * 
	 * @param name
	 *            Name to look up
	 * @return Player if it was found, otherwise null
	 * @deprecated
	 */
	@Deprecated
	public static Player getPlayer(String name) {
		Core.lock.lock();
		try {
			return server.getPlayer(name);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the player with the exact given name, case insensitive
	 * 
	 * @param name
	 *            Exact name of the player to retrieve
	 * @return Player object or null if not found
	 * @deprecated
	 */
	@Deprecated
	public static Player getPlayerExact(String name) {
		Core.lock.lock();
		try {
			return server.getPlayerExact(name);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Attempts to match any players with the given name, and returns a list of
	 * all possibly matches
	 * <p />
	 * This list is not sorted in any particular order. If an exact match is
	 * found, the returned list will only contain a single result.
	 * 
	 * @param name
	 *            Name to match
	 * @return List of all possible players
	 * @deprecated
	 */
	@Deprecated
	public static List<Player> matchPlayer(String name) {
		Core.lock.lock();
		try {
			return server.matchPlayer(name);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the PluginManager for interfacing with plugins
	 * 
	 * @return PluginManager for this Server instance
	 * @deprecated
	 */
	@Deprecated
	public static PluginManager getPluginManager() {
		Core.lock.lock();
		try {
			return server.getPluginManager();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the Scheduler for managing scheduled events
	 * 
	 * @return Scheduler for this Server instance
	 * @deprecated
	 */
	@Deprecated
	public static BukkitScheduler getScheduler() {
		Core.lock.lock();
		try {
			return server.getScheduler();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a services manager
	 * 
	 * @return Services manager
	 * @deprecated
	 */
	@Deprecated
	public static ServicesManager getServicesManager() {
		Core.lock.lock();
		try {
			return server.getServicesManager();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a list of all worlds on this server
	 * 
	 * @return A list of worlds
	 * @deprecated
	 */
	@Deprecated
	public static List<World> getWorlds() {
		Core.lock.lock();
		try {
			return server.getWorlds();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Creates or loads a world with the given name using the specified options.
	 * <p />
	 * If the world is already loaded, it will just return the equivalent of
	 * getWorld(creator.name()).
	 * 
	 * @param creator
	 *            The options to use when creating the world.
	 * @return Newly created or loaded world
	 * @deprecated
	 */
	@Deprecated
	public static World createWorld(WorldCreator creator) {
		Core.lock.lock();
		try {
			return server.createWorld(creator);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Unloads a world with the given name.
	 * 
	 * @param name
	 *            Name of the world to unload
	 * @param save
	 *            Whether to save the chunks before unloading.
	 * @return Whether the action was Successful
	 */
	public static boolean unloadWorld(String name, boolean save) {
		Core.lock.lock();
		try {
			return server.unloadWorld(name, save);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Unloads the given world.
	 * 
	 * @param world
	 *            The world to unload
	 * @param save
	 *            Whether to save the chunks before unloading.
	 * @return Whether the action was Successful
	 */
	public static boolean unloadWorld(World world, boolean save) {
		Core.lock.lock();
		try {
			return server.unloadWorld(world, save);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the world with the given name
	 * 
	 * @param name
	 *            Name of the world to retrieve
	 * @return World with the given name, or null if none exists
	 * @deprecated
	 */
	@Deprecated
	public static World getWorld(String name) {
		Core.lock.lock();
		try {
			return server.getWorld(name);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the world from the given Unique ID
	 * 
	 * @param uid
	 *            Unique ID of the world to retrieve.
	 * @return World with the given Unique ID, or null if none exists.
	 * @deprecated
	 */
	@Deprecated
	public static World getWorld(UUID uid) {
		Core.lock.lock();
		try {
			return server.getWorld(uid);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the map from the given item ID.
	 * 
	 * @param id
	 *            ID of the map to get.
	 * @return The MapView if it exists, or null otherwise.
	 * @deprecated
	 */
	@Deprecated
	public static MapView getMap(short id) {
		Core.lock.lock();
		try {
			return server.getMap(id);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Create a new map with an automatically assigned ID.
	 * 
	 * @param world
	 *            The world the map will belong to.
	 * @return The MapView just created.
	 * @deprecated
	 */
	@Deprecated
	public static MapView createMap(World world) {
		Core.lock.lock();
		try {
			return server.createMap(world);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Reloads the server, refreshing settings and plugin information
	 */
	public static void reload() {
		Core.lock.lock();
		try {
			server.reload();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Returns the primary logger associated with this server instance
	 * 
	 * @return Logger associated with this server
	 * @deprecated
	 */
	@Deprecated
	public static Logger getLogger() {
		Core.lock.lock();
		try {
			return server.getLogger();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a {@link PluginCommand} with the given name or alias
	 * 
	 * @param name
	 *            Name of the command to retrieve
	 * @return PluginCommand if found, otherwise null
	 * @deprecated
	 */
	@Deprecated
	public static PluginCommand getPluginCommand(String name) {
		Core.lock.lock();
		try {
			return server.getPluginCommand(name);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Writes loaded players to disk
	 */
	public static void savePlayers() {
		Core.lock.lock();
		try {
			server.savePlayers();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Dispatches a command on the server, and executes it if found.
	 * 
	 * @param sender
	 *            The apparent sender of the command
	 * @param commandLine
	 *            command + arguments. Example: "test abc 123"
	 * @return returns false if no target is found.
	 * @throws CommandException
	 *             Thrown when the executor for the given command fails with an
	 *             unhandled exception
	 */
	public static boolean dispatchCommand(CommandSender sender,
			String commandLine) throws CommandException {
		Core.lock.lock();
		try {
			return server.dispatchCommand(sender, commandLine);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Populates a given {@link ServerConfig} with values attributes to this
	 * server
	 * 
	 * @param config
	 *            ServerConfig to populate
	 */
	public static void configureDbConfig(ServerConfig config) {
		Core.lock.lock();
		try {
			server.configureDbConfig(config);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Adds a recipe to the crafting manager.
	 * 
	 * @param recipe
	 *            The recipe to add.
	 * @return True if the recipe was added, false if it wasn't for some reason.
	 */
	public static boolean addRecipe(Recipe recipe) {
		Core.lock.lock();
		try {
			return server.addRecipe(recipe);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get a list of all recipes for a given item. The stack size is ignored in
	 * comparisons. If the durability is -1, it will match any data value.
	 * 
	 * @param result
	 *            The item whose recipes you want
	 * @return The list of recipes
	 */
	public static List<Recipe> getRecipesFor(ItemStack result) {
		Core.lock.lock();
		try {
			return server.getRecipesFor(result);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Get an iterator through the list of crafting recipes.
	 * 
	 * @return The iterator.
	 */
	public static Iterator<Recipe> recipeIterator() {
		Core.lock.lock();
		try {
			return server.recipeIterator();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Clears the list of crafting recipes.
	 */
	public static void clearRecipes() {
		Core.lock.lock();
		try {
			server.clearRecipes();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Resets the list of crafting recipes to the default.
	 */
	public static void resetRecipes() {
		Core.lock.lock();
		try {
			server.resetRecipes();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a list of command aliases defined in the server properties.
	 * 
	 * @return Map of aliases to command names
	 */
	public static Map<String, String[]> getCommandAliases() {
		Core.lock.lock();
		try {
			return server.getCommandAliases();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the radius, in blocks, around each worlds spawn point to protect
	 * 
	 * @return Spawn radius, or 0 if none
	 */
	public static int getSpawnRadius() {
		Core.lock.lock();
		try {
			return server.getSpawnRadius();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Sets the radius, in blocks, around each worlds spawn point to protect
	 * 
	 * @param value
	 *            New spawn radius, or 0 if none
	 */
	public static void setSpawnRadius(int value) {
		Core.lock.lock();
		try {
			server.setSpawnRadius(value);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets whether the Server is in online mode or not.
	 * 
	 * @return Whether the server is in online mode.
	 */
	public static boolean getOnlineMode() {
		Core.lock.lock();
		try {
			return server.getOnlineMode();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets whether this server allows flying or not.
	 * 
	 * @return Whether this server allows flying or not.
	 */
	public static boolean getAllowFlight() {
		Core.lock.lock();
		try {
			return server.getAllowFlight();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets whether the server is in hardcore mode or not.
	 * 
	 * @return Whether this server is in hardcore mode or not.
	 */
	public static boolean isHardcore() {
		Core.lock.lock();
		try {
			return server.isHardcore();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets whether to use vanilla (false) or exact behaviour (true).
	 * 
	 * Vanilla behaviour: check for collisions and move the player if needed.
	 * Exact behaviour: spawn players exactly where they should be.
	 * 
	 * @return Whether to use vanilla (false) or exact behaviour (true).
	 */
	public static boolean useExactLoginLocation() {
		Core.lock.lock();
		try {
			return server.useExactLoginLocation();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Shutdowns the server, stopping everything.
	 */
	public static void shutdown() {
		Core.lock.lock();
		try {
			server.shutdown();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Broadcasts the specified message to every user with the given permission
	 * 
	 * @param message
	 *            Message to broadcast
	 * @param permission
	 *            Permission the users must have to receive the broadcast
	 * @return Amount of users who received the message
	 */
	public static int broadcast(String message, String permission) {
		Core.lock.lock();
		try {
			return server.broadcast(message, permission);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the player by the given name, regardless if they are offline or
	 * online.
	 * <p />
	 * This will return an object even if the player does not exist. To this
	 * method, all players will exist.
	 * 
	 * @param name
	 *            Name of the player to retrieve
	 * @return OfflinePlayer object
	 */
	public static OfflinePlayer getOfflinePlayer(String name) {
		Core.lock.lock();
		try {
			return server.getOfflinePlayer(name);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a set containing all current IPs that are banned
	 * 
	 * @return Set containing banned IP addresses
	 */
	public static Set<String> getIPBans() {
		Core.lock.lock();
		try {
			return server.getIPBans();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Bans the specified address from the server
	 * 
	 * @param address
	 *            IP address to ban
	 */
	public static void banIP(String address) {
		Core.lock.lock();
		try {
			server.banIP(address);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Unbans the specified address from the server
	 * 
	 * @param address
	 *            IP address to unban
	 */
	public static void unbanIP(String address) {
		Core.lock.lock();
		try {
			server.unbanIP(address);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a set containing all banned players
	 * 
	 * @return Set containing banned players
	 */
	public static Set<OfflinePlayer> getBannedPlayers() {
		Core.lock.lock();
		try {
			return server.getBannedPlayers();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets a set containing all player operators
	 * 
	 * @return Set containing player operators
	 */
	public static Set<OfflinePlayer> getOperators() {
		Core.lock.lock();
		try {
			return server.getOperators();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the default {@link GameMode} for new players
	 * 
	 * @return Default game mode
	 */
	public static GameMode getDefaultGameMode() {
		Core.lock.lock();
		try {
			return server.getDefaultGameMode();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Sets the default {@link GameMode} for new players
	 * 
	 * @param mode
	 *            New game mode
	 */
	public static void setDefaultGameMode(GameMode mode) {
		Core.lock.lock();
		try {
			server.setDefaultGameMode(mode);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the {@link ConsoleCommandSender} that may be used as an input source
	 * for this server.
	 * 
	 * @return The Console CommandSender
	 */
	public static ConsoleCommandSender getConsoleSender() {
		Core.lock.lock();
		try {
			return server.getConsoleSender();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the folder that contains all of the various {@link World}s.
	 * 
	 * @return World container folder
	 */
	public static File getWorldContainer() {
		Core.lock.lock();
		try {
			return server.getWorldContainer();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets every player that has ever played on this server.
	 * 
	 * @return Array containing all players
	 */
	public static OfflinePlayer[] getOfflinePlayers() {
		Core.lock.lock();
		try {
			return server.getOfflinePlayers();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the {@link Messenger} responsible for this server.
	 * 
	 * @return Messenger responsible for this server.
	 */
	public static Messenger getMessenger() {
		Core.lock.lock();
		try {
			return server.getMessenger();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the {@link HelpMap} providing help topics for this server.
	 * 
	 * @return The server's HelpMap.
	 */
	public static HelpMap getHelpMap() {
		Core.lock.lock();
		try {
			return server.getHelpMap();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Creates an empty inventory of the specified type. If the type is
	 * {@link InventoryType#CHEST}, the new inventory has a size of 27;
	 * otherwise the new inventory has the normal size for its type.
	 * 
	 * @param owner
	 *            The holder of the inventory; can be null if there's no holder.
	 * @param type
	 *            The type of inventory to create.
	 * @return The new inventory.
	 */
	static Inventory createInventory(InventoryHolder owner, InventoryType type) {
		Core.lock.lock();
		try {
			return server.createInventory(owner, type);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Creates an empty inventory of type {@link InventoryType#CHEST} with the
	 * specified size.
	 * 
	 * @param owner
	 *            The holder of the inventory; can be null if there's no holder.
	 * @param size
	 *            The size of inventory to create; must be a multiple of 9.
	 * @return The new inventory.
	 * @throws IllegalArgumentException
	 *             If the size is not a multiple of 9.
	 */
	static Inventory createInventory(InventoryHolder owner, int size) {
		Core.lock.lock();
		try {
			return server.createInventory(owner, size);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Creates an empty inventory of type {@link InventoryType#CHEST} with the
	 * specified size and title.
	 * 
	 * @param owner
	 *            The holder of the inventory; can be null if there's no holder.
	 * @param size
	 *            The size of inventory to create; must be a multiple of 9.
	 * @param title
	 *            The title of the inventory, to be displayed when it is viewed.
	 * @return The new inventory.
	 * @throws IllegalArgumentException
	 *             If the size is not a multiple of 9.
	 */
	static Inventory createInventory(InventoryHolder owner, int size,
			String title) {
		Core.lock.lock();
		try {
			return server.createInventory(owner, size, title);
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets user-specified limit for number of monsters that can spawn in a
	 * chunk
	 * 
	 * @returns The monster spawn limit
	 */
	static int getMonsterSpawnLimit() {
		Core.lock.lock();
		try {
			return server.getMonsterSpawnLimit();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets user-specified limit for number of animals that can spawn in a chunk
	 * 
	 * @returns The animal spawn limit
	 */
	static int getAnimalSpawnLimit() {
		Core.lock.lock();
		try {
			return server.getAnimalSpawnLimit();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets user-specified limit for number of water animals that can spawn in a
	 * chunk
	 * 
	 * @returns The water animal spawn limit
	 */
	static int getWaterAnimalSpawnLimit() {
		Core.lock.lock();
		try {
			return server.getWaterAnimalSpawnLimit();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets user-specified limit for number of ambient mobs that can spawn in a
	 * chunk
	 * 
	 * @returns The ambient spawn limit
	 */
	static int getAmbientSpawnLimit() {
		Core.lock.lock();
		try {
			return server.getAmbientSpawnLimit();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Returns true if the current {@link Thread} is the server's primary thread
	 */
	static boolean isPrimaryThread() {
		Core.lock.lock();
		try {
			return server.isPrimaryThread();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the message that is displayed on the server list
	 * 
	 * @returns the servers MOTD
	 */
	static String getMotd() {
		Core.lock.lock();
		try {
			return server.getMotd();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the default message that is displayed when the server is stopped
	 * 
	 * @return the shutdown message
	 */
	static String getShutdownMessage() {
		Core.lock.lock();
		try {
			return server.getShutdownMessage();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the current warning state for the server
	 * 
	 * @return The configured WarningState
	 */
	public static WarningState getWarningState() {
		Core.lock.lock();
		try {
			return server.getWarningState();
		} finally {
			Core.lock.unlock();
		}
	}

	/**
	 * Gets the instance of the item factory (for {@link ItemMeta}).
	 * 
	 * @return the item factory
	 * @see ItemFactory
	 */
	static ItemFactory getItemFactory() {
		Core.lock.lock();
		try {
			return server.getItemFactory();
		} finally {
			Core.lock.unlock();
		}
	}

}
