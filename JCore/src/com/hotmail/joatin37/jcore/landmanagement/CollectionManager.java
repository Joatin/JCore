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

package com.hotmail.joatin37.jcore.landmanagement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.core.Core;
import com.hotmail.joatin37.jcore.util.JUtil;

public final class CollectionManager implements Listener, ICollectionManager {

	private HashMap<UUID, Collection> collections;
	private final JavaPlugin jtown;
	private File configfile;
	private FileConfiguration saveconfig;
	private WorldMap worldmap;
	private HashMap<String, Collection> players;
	private Core core;

	public CollectionManager(JavaPlugin jtown, Core core) {
		this.core = core;
		this.jtown = jtown;
		this.players = new HashMap<String, Collection>();
		this.collections = new HashMap<UUID, Collection>();

	}

	@Override
	public Collection getCollection(UUID uuid) {
		return this.collections.get(uuid);
	}

	@Override
	public Collection getCurrentCollection(String player) {
		return this.players.get(player);
	}

	private Collection reconstructCollection(String plugin, String type,
			UUID uuid) {
		Collection coll = this.core.getExtension(plugin).constructCollection(
				type, this, uuid, this.saveconfig);
		if (coll == null) {
			throw new NullPointerException();
		}
		return coll;
	}

	@Override
	public Plot reconstructPlot(String plugin, String type, Collection parent,
			UUID uuid) {
		return null;
	}

	public void onInit() {
		this.load();
		this.worldmap = new WorldMap(this.jtown, this);
		this.jtown.getServer().getPluginManager()
				.registerEvents(this, this.jtown);
	}

	public FileConfiguration getConfig() {
		return this.saveconfig;
	}

	@Override
	public boolean putNewCollection(Collection coll, Location baseloc) {

		List<Location> list = new Vector<Location>();
		int x = baseloc.getBlockX();
		int z = baseloc.getBlockZ();
		list.add(new Location(baseloc.getWorld(), x - 1, 0, z - 1));
		list.add(new Location(baseloc.getWorld(), x - 1, 0, z));
		list.add(new Location(baseloc.getWorld(), x - 1, 0, z + 1));
		list.add(new Location(baseloc.getWorld(), x, 0, z - 1));
		list.add(new Location(baseloc.getWorld(), x, 0, z));
		list.add(new Location(baseloc.getWorld(), x, 0, z + 1));
		list.add(new Location(baseloc.getWorld(), x + 1, 0, z - 1));
		list.add(new Location(baseloc.getWorld(), x + 1, 0, z));
		list.add(new Location(baseloc.getWorld(), x + 1, 0, z + 1));
		Iterator<Location> it = list.iterator();
		while (it.hasNext()) {
			if (!this.worldmap.canSet(it.next())) {
				return false;
			}
		}
		Iterator<Location> it2 = list.iterator();
		this.collections.put(coll.getUUID(), coll);
		while (it2.hasNext()) {
			this.worldmap.set(it2.next(), coll, null);
		}
		return true;
	}

	private void load() {
		if (this.configfile == null) {
			this.configfile = new File(this.jtown.getDataFolder(),
					"collections.sav");
		}
		this.saveconfig = YamlConfiguration.loadConfiguration(this.configfile);
		List<String> list = this.saveconfig.getStringList("collections");
		if (list == null) {
			return;
		}
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String s = it.next();
			Collection coll = this.reconstructCollection(
					JUtil.getPluginFromUuidString(s),
					JUtil.getTypeFromUuidString(s), JUtil.stringToUUID(s));
			this.collections.put(JUtil.stringToUUID(s), coll);
			if (Core.isDebugg()) {
				this.core.getLogger().info(
						coll.getName() + " was constructed, with the uuid: "
								+ JUtil.uuidToString(coll.getUUID()));
				this.collections.get(JUtil.stringToUUID(s)).addLandMass();
			}
		}

	}

	@Override
	public IWorldMap getWorldMap() {
		return this.worldmap;
	}

	public void save() {
		if (this.saveconfig == null || this.configfile == null) {
			return;
		}
		List<String> list = new Vector<String>();
		Iterator<Collection> it = this.collections.values().iterator();
		while (it.hasNext()) {
			Collection coll = it.next();
			list.add(JUtil.uuidToString(coll.save(this.saveconfig)) + ";"
					+ coll.getPluginName() + ";" + coll.getKind());
		}
		this.saveconfig.set("collections", list);
		try {
			this.saveconfig.save(this.configfile);
			this.jtown.getLogger().info("Succesfully saved collections.sav");
		} catch (IOException ex) {
			this.jtown.getLogger().log(Level.SEVERE,
					"Could not save config to " + this.configfile, ex);
		}
		this.worldmap.save();
	}

	public void createNewCollection(Player player, String name) {

	}

	/*------EventListeners------*/

	// block listeners

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockBreakEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockBurnEvent(BlockBurnEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockBurnEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockCanBuildEvent(BlockCanBuildEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockCanBuildEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockDamageEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockDispenseEvent(BlockDispenseEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockDispenseEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockExpEvent(BlockExpEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockExpEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockFadeEvent(BlockFadeEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockFadeEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockFormEvent(BlockFormEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockFormEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockFromToEvent(BlockFromToEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockFromToEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockGrowEvent(BlockGrowEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockGrowEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockIgniteEvent(BlockIgniteEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockIgniteEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockPhysicsEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockPistonExtendEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockPistonRetractEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockPlaceEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockRedstoneEvent(BlockRedstoneEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockRedstoneEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onBlockSpreadEvent(BlockSpreadEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.BlockSpreadEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onEntityBlockFormEvent(EntityBlockFormEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.EntityBlockFormEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onLeavesDecayEvent(LeavesDecayEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.LeavesDecayEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onNotePlayEvent(NotePlayEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.NotePlayEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent event) {

		BlockRow1 row = this.worldmap.get(event.getBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.SignChangeEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	// enchantment listeners

	@EventHandler
	public void onEnchantItemEvent(EnchantItemEvent event) {
		BlockRow1 row = this.worldmap
				.get(event.getEnchantBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.EnchantItemEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	@EventHandler
	public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
		BlockRow1 row = this.worldmap
				.get(event.getEnchantBlock().getLocation());
		if (row == null) {
			return;
		}
		Collection coll = this.collections.get(row.getCollectionId());
		if (coll == null) {
			this.worldmap.set(null, null, null);
		} else {
			try {
				coll.PrepareItemEnchantEvent(event, row);
			} catch (Exception e) {
				this.core.getLogger().severe(
						"Couldn't pass " + event.getEventName() + " to "
								+ coll.getName());
			}
		}
	}

	// enitity listeners

	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) {

	}

	@EventHandler
	public void onCreeperPowerEvent(CreeperPowerEvent event) {

	}

	@EventHandler
	public void onEntityBreakDoorEvent(EntityBreakDoorEvent event) {

	}

	@EventHandler
	public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {

	}

	@EventHandler
	public void onEntityCombustByBlockEvent(EntityCombustByBlockEvent event) {

	}

	@EventHandler
	public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event) {

	}

	@EventHandler
	public void onEntityCombustEvent(EntityCombustEvent event) {

	}

	@EventHandler
	public void onEntityCreatePortalEvent(EntityCreatePortalEvent event) {

	}

	@EventHandler
	public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event) {

	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

	}

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {

	}

	@EventHandler
	public void onEntityExplodeEvent(EntityExplodeEvent event) {

	}

	@EventHandler
	public void onEntityInteractEvent(EntityInteractEvent event) {

	}

	@EventHandler
	public void onEntityPortalEnterEvent(EntityPortalEnterEvent event) {

	}

	@EventHandler
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {

	}

	@EventHandler
	public void onEntityShootBowEvent(EntityShootBowEvent event) {

	}

	@EventHandler
	public void onEntityTameEvent(EntityTameEvent event) {

	}

	@EventHandler
	public void onEntityTargetEvent(EntityTargetEvent event) {

	}

	@EventHandler
	public void onEntityTargetLivingEntityEvent(
			EntityTargetLivingEntityEvent event) {

	}

	@EventHandler
	public void onEntityTeleportEvent(EntityTeleportEvent event) {

	}

	@EventHandler
	public void onExpBottleEvent(ExpBottleEvent event) {

	}

	@EventHandler
	public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {

	}

	@EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {

	}

	@EventHandler
	public void onItemDespawnEvent(ItemDespawnEvent event) {

	}

	@EventHandler
	public void onItemSpawnEvent(ItemSpawnEvent event) {

	}

	@EventHandler
	public void onPigZapEvent(PigZapEvent event) {

	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {

	}

	@EventHandler
	public void onPotionSplashEvent(PotionSplashEvent event) {

	}

	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent event) {

	}

	@EventHandler
	public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {

	}

	@EventHandler
	public void onSheepDyeWoolEvent(SheepDyeWoolEvent event) {

	}

	@EventHandler
	public void onSheepRegrowWoolEvent(SheepRegrowWoolEvent event) {

	}

	@EventHandler
	public void onSlimeSplitEvent(SlimeSplitEvent event) {

	}

	// hanging listeners

	@EventHandler
	public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event) {

	}

	@EventHandler
	public void onHangingBreakEvent(HangingBreakEvent event) {

	}

	@EventHandler
	public void onHangingPlaceEvent(HangingPlaceEvent event) {

	}

	// inventory listeners

	@EventHandler
	public void onBrewEvent(BrewEvent event) {

	}

	@EventHandler
	public void onCraftItemEvent(CraftItemEvent event) {

	}

	@EventHandler
	public void onFurnaceBurnEvent(FurnaceBurnEvent event) {

	}

	@EventHandler
	public void onFurnaceExtractEvent(FurnaceExtractEvent event) {

	}

	@EventHandler
	public void onFurnaceSmeltEvent(FurnaceSmeltEvent event) {

	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {

	}

	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) {

	}

	@EventHandler
	public void onInventoryEvent(InventoryEvent event) {

	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event) {

	}

	@EventHandler
	public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {

	}

	// player listeners

	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

	}

	@EventHandler
	public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {

	}

	@EventHandler
	public void onPlayerAnimationEvent(PlayerAnimationEvent event) {

	}

	@EventHandler
	public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {

	}

	@EventHandler
	public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {

	}

	@EventHandler
	public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {

	}

	@EventHandler
	public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) {

	}

	@EventHandler
	public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {

	}

	@EventHandler
	public void onPlayerChannelEvent(PlayerChannelEvent event) {

	}

	@EventHandler
	public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent event) {

	}

	@EventHandler
	public void onPlayerCommandPreprocessEvent(
			PlayerCommandPreprocessEvent event) {

	}

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event) {

	}

	@EventHandler
	public void onPlayerEggThrowEvent(PlayerEggThrowEvent event) {

	}

	@EventHandler
	public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {

	}

	@EventHandler
	public void onPlayerFishEvent(PlayerFishEvent event) {

	}

	@EventHandler
	public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {

	}

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {

	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {

	}

	@EventHandler
	public void onPlayerItemBreakEvent(PlayerItemBreakEvent event) {

	}

	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {

	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Collection coll;
		BlockRow1 row = this.worldmap.get(event.getPlayer().getLocation());
		if (row == null) {
			coll = null;
		} else {
			coll = this.collections.get(row.getCollectionId());
			coll.PlayerEntered(event.getPlayer(), row, null);
		}
		this.players.put(event.getPlayer().getName(), coll);
	}

	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent event) {

	}

	@EventHandler
	public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent event) {

	}

	@EventHandler
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {

	}

	@EventHandler
	public void onPlayerPortalEvent(PlayerPortalEvent event) {

	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		BlockRow1 row1 = this.worldmap.get(event.getFrom());
		BlockRow1 row2 = this.worldmap.get(event.getTo());
		if (row1 != null) {
			this.players.put(event.getPlayer().getName(),
					this.collections.get(row1.getCollectionId()));
		}
		if (row1 == null && row2 == null) {
			return;
		}
		if (row1.getCollectionId() != null && row2.getCollectionId() != null) {
			if (row1.getCollectionId().equals(row2.getCollectionId())) {
				Collection coll = this.collections.get(row1.getCollectionId());
				try {
					coll.PlayerMoveEvent(event, row1);
				} catch (Exception e) {
					this.core.getLogger().severe(
							"Couldn't pass " + event.getEventName() + " to "
									+ coll.getName());
				}
			} else {
				Collection coll = this.collections.get(row1.getCollectionId());
				try {
					coll.PlayerMoveEvent(event, row1);
				} catch (Exception e) {
					this.core.getLogger().severe(
							"Couldn't pass " + event.getEventName() + " to "
									+ coll.getName());
				}

				Collection coll2 = this.collections.get(row2.getCollectionId());
				try {
					coll2.PlayerMoveEvent(event, row2);
				} catch (Exception e) {
					this.core.getLogger().severe(
							"Couldn't pass " + event.getEventName() + " to "
									+ coll2.getName());
				}
			}
		} else {
			if (row1.getCollectionId() != null) {
				Collection coll = this.collections.get(row1.getCollectionId());
				try {
					coll.PlayerMoveEvent(event, row1);
				} catch (Exception e) {
					this.core.getLogger().severe(
							"Couldn't pass " + event.getEventName() + " to "
									+ coll.getName());
				}
			} else {
				if (row2.getCollectionId() != null) {
					Collection coll2 = this.collections.get(row2
							.getCollectionId());
					try {
						coll2.PlayerMoveEvent(event, row2);
					} catch (Exception e) {
						this.core.getLogger().severe(
								"Couldn't pass " + event.getEventName()
										+ " to " + coll2.getName());
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {

	}

	@EventHandler
	public void onPlayerRegisterChannelEvent(PlayerRegisterChannelEvent event) {

	}

	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {

	}

	@EventHandler
	public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {

	}

	@EventHandler
	public void onPlayerTeleportEvent(PlayerTeleportEvent event) {

	}

	@EventHandler
	public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) {

	}

	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {

	}

	@EventHandler
	public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent event) {

	}

	@EventHandler
	public void onPlayerUnregisterChannelEvent(
			PlayerUnregisterChannelEvent event) {

	}

	@EventHandler
	public void onPlayerVelocityEvent(PlayerVelocityEvent event) {

	}

	// vehicle listeners

	@EventHandler
	public void onVehicleBlockCollisionEvent(VehicleBlockCollisionEvent event) {

	}

	@EventHandler
	public void onVehicleCreateEvent(VehicleCreateEvent event) {

	}

	@EventHandler
	public void onVehicleDamageEvent(VehicleDamageEvent event) {

	}

	@EventHandler
	public void onVehicleDestroyEvent(VehicleDestroyEvent event) {

	}

	@EventHandler
	public void onVehicleEnterEvent(VehicleEnterEvent event) {

	}

	@EventHandler
	public void onVehicleEntityCollisionEvent(VehicleEntityCollisionEvent event) {

	}

	@EventHandler
	public void onVehicleExitEvent(VehicleExitEvent event) {

	}

	@EventHandler
	public void onVehicleMoveEvent(VehicleMoveEvent event) {

	}

	@EventHandler
	public void onVehicleUpdateEvent(VehicleUpdateEvent event) {

	}

	// weather listeners

	@EventHandler
	public void onLightningStrikeEvent(LightningStrikeEvent event) {

	}

	// world listeners

	@EventHandler
	public void onPortalCreateEvent(PortalCreateEvent event) {

	}

	@EventHandler
	public void onStructureGrowEvent(StructureGrowEvent event) {

	}

}
