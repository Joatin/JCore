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

package com.hotmail.joatin37.jcore.api;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonEvent;
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
import org.bukkit.event.entity.EntityEvent;
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
import org.bukkit.event.hanging.HangingEvent;
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
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerEvent;
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
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
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
import org.bukkit.event.vehicle.VehicleCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

import com.hotmail.joatin37.jcore.worldmap.BlockRow1;

public abstract class Plot {

	private final Collection parent;
	private final UUID uuid;
	private int landmass = 0;

	public void addLandMass() {
		this.landmass++;
	}

	public void removeLandMass() {
		this.landmass--;
	}

	public int getLandMass() {
		return this.landmass;
	}

	protected Plot(Collection parent, UUID uuid) {
		this.parent = parent;
		this.uuid = uuid;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public Collection getParent() {
		return this.parent;
	}

	public abstract String getName();

	/*------EventListeners------*/

	// All block listeners

	public final void BlockBreakEvent(BlockBreakEvent event, BlockRow1 row) {

	}

	public final void BlockBurnEvent(BlockBurnEvent event, BlockRow1 row) {

	}

	public final void BlockCanBuildEvent(BlockCanBuildEvent event, BlockRow1 row) {

	}

	public final void BlockDamageEvent(BlockDamageEvent event, BlockRow1 row) {

	}

	public final void BlockDispenseEvent(BlockDispenseEvent event, BlockRow1 row) {

	}

	public final void BlockEvent(BlockEvent event, BlockRow1 row) {

	}

	public final void BlockExpEvent(BlockExpEvent event, BlockRow1 row) {

	}

	public final void BlockFadeEvent(BlockFadeEvent event, BlockRow1 row) {

	}

	public final void BlockFormEvent(BlockFormEvent event, BlockRow1 row) {

	}

	public final void BlockFromToEvent(BlockFromToEvent event, BlockRow1 row) {

	}

	public final void BlockGrowEvent(BlockGrowEvent event, BlockRow1 row) {

	}

	public final void BlockIgniteEvent(BlockIgniteEvent event, BlockRow1 row) {

	}

	public final void BlockPhysicsEvent(BlockPhysicsEvent event, BlockRow1 row) {

	}

	public final void BlockPistonEvent(BlockPistonEvent event, BlockRow1 row) {

	}

	public final void BlockPistonExtendEvent(BlockPistonExtendEvent event,
			BlockRow1 row) {

	}

	public final void BlockPistonRetractEvent(BlockPistonRetractEvent event,
			BlockRow1 row) {

	}

	public final void BlockPlaceEvent(BlockPlaceEvent event, BlockRow1 row) {

	}

	public final void BlockRedstoneEvent(BlockRedstoneEvent event, BlockRow1 row) {

	}

	public final void BlockSpreadEvent(BlockSpreadEvent event, BlockRow1 row) {

	}

	public final void EntityBlockFormEvent(EntityBlockFormEvent event,
			BlockRow1 row) {

	}

	public final void LeavesDecayEvent(LeavesDecayEvent event, BlockRow1 row) {

	}

	public final void NotePlayEvent(NotePlayEvent event, BlockRow1 row) {

	}

	public final void SignChangeEvent(SignChangeEvent event, BlockRow1 row) {

	}

	// Enchantment Listeners

	public final void EnchantItemEvent(EnchantItemEvent event, BlockRow1 row) {

	}

	public final void PrepareItemEnchantEvent(PrepareItemEnchantEvent event,
			BlockRow1 row) {

	}

	// Entity listeners

	public final void CreatureSpawnEvent(CreatureSpawnEvent event, BlockRow1 row) {

	}

	public final void CreeperPowerEvent(CreeperPowerEvent event, BlockRow1 row) {

	}

	public final void EntityBreakDoorEvent(EntityBreakDoorEvent event,
			BlockRow1 row) {

	}

	public final void EntityChangeBlockEvent(EntityChangeBlockEvent event,
			BlockRow1 row) {

	}

	public final void EntityCombustByBlockEvent(
			EntityCombustByBlockEvent event, BlockRow1 row) {

	}

	public final void EntityCombustByEntityEvent(
			EntityCombustByEntityEvent event, BlockRow1 row) {

	}

	public final void EntityCombustEvent(EntityCombustEvent event, BlockRow1 row) {

	}

	public final void EntityCreatePortalEvent(EntityCreatePortalEvent event,
			BlockRow1 row) {

	}

	public final void EntityDamageByBlockEvent(EntityDamageByBlockEvent event,
			BlockRow1 row) {

	}

	public final void EntityDamageByEntityEvent(
			EntityDamageByEntityEvent event, BlockRow1 row) {

	}

	public final void EntityDamageEvent(EntityDamageEvent event, BlockRow1 row) {

	}

	public final void EntityEvent(EntityEvent event, BlockRow1 row) {

	}

	public final void EntityExplodeEvent(EntityExplodeEvent event, BlockRow1 row) {

	}

	public final void EntityInteractEvent(EntityInteractEvent event,
			BlockRow1 row) {

	}

	public final void EntityPortalEnterEvent(EntityPortalEnterEvent event,
			BlockRow1 row) {

	}

	public final void EntityRegainHealthEvent(EntityRegainHealthEvent event,
			BlockRow1 row) {

	}

	public final void EntityShootBowEvent(EntityShootBowEvent event,
			BlockRow1 row) {

	}

	public final void EntityTameEvent(EntityTameEvent event, BlockRow1 row) {

	}

	public final void EntityTargetEvent(EntityTargetEvent event, BlockRow1 row) {

	}

	public final void EntityTargetLivingEntityEvent(
			EntityTargetLivingEntityEvent event, BlockRow1 row) {

	}

	public final void EntityTeleportEvent(EntityTeleportEvent event,
			BlockRow1 row) {

	}

	public final void ExpBottleEvent(ExpBottleEvent event, BlockRow1 row) {

	}

	public final void ExplosionPrimeEvent(ExplosionPrimeEvent event,
			BlockRow1 row) {

	}

	public final void FoodLevelChangeEvent(FoodLevelChangeEvent event,
			BlockRow1 row) {

	}

	public final void ItemDespawnEvent(ItemDespawnEvent event, BlockRow1 row) {

	}

	public final void ItemSpawnEvent(ItemSpawnEvent event, BlockRow1 row) {

	}

	public final void PigZapEvent(PigZapEvent event, BlockRow1 row) {

	}

	public final void PlayerDeathEvent(PlayerDeathEvent event, BlockRow1 row) {

	}

	public final void PotionSplashEvent(PotionSplashEvent event, BlockRow1 row) {

	}

	public final void ProjectileHitEvent(ProjectileHitEvent event, BlockRow1 row) {

	}

	public final void ProjectileLaunchEvent(ProjectileLaunchEvent event,
			BlockRow1 row) {

	}

	public final void SheepDyeWoolEvent(SheepDyeWoolEvent event, BlockRow1 row) {

	}

	public final void SheepRegrowWoolEvent(SheepRegrowWoolEvent event,
			BlockRow1 row) {

	}

	public final void SlimeSplitEvent(SlimeSplitEvent event, BlockRow1 row) {

	}

	// Hanging listeners

	public final void HangingBreakByEntityEvent(
			HangingBreakByEntityEvent event, BlockRow1 row) {

	}

	public final void HangingBreakEvent(HangingBreakEvent event, BlockRow1 row) {

	}

	public final void HangingEvent(HangingEvent event, BlockRow1 row) {

	}

	public final void HangingPlaceEvent(HangingPlaceEvent event, BlockRow1 row) {

	}

	// Inventory listeners

	public final void BrewEvent(BrewEvent event, BlockRow1 row) {

	}

	public final void CraftItemEvent(CraftItemEvent event, BlockRow1 row) {

	}

	public final void FurnaceBurnEvent(FurnaceBurnEvent event, BlockRow1 row) {

	}

	public final void FurnaceExtractEvent(FurnaceExtractEvent event,
			BlockRow1 row) {

	}

	public final void FurnaceSmeltEvent(FurnaceSmeltEvent event, BlockRow1 row) {

	}

	public final void InventoryClickEvent(InventoryClickEvent event,
			BlockRow1 row) {

	}

	public final void InventoryCloseEvent(InventoryCloseEvent event,
			BlockRow1 row) {

	}

	public final void InventoryEvent(InventoryEvent event, BlockRow1 row) {

	}

	public final void InventoryOpenEvent(InventoryOpenEvent event, BlockRow1 row) {

	}

	public final void PrepareItemCraftEvent(PrepareItemCraftEvent event,
			BlockRow1 row) {

	}

	// Player listeners

	@EventHandler
	public final void AsyncPlayerChatEvent(AsyncPlayerChatEvent event,
			BlockRow1 row) {

	}

	public final void AsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event,
			BlockRow1 row) {

	}

	public final void PlayerAnimationEvent(PlayerAnimationEvent event,
			BlockRow1 row) {

	}

	public final void PlayerBedEnterEvent(PlayerBedEnterEvent event,
			BlockRow1 row) {

	}

	public final void PlayerBedLeaveEvent(PlayerBedLeaveEvent event,
			BlockRow1 row) {

	}

	public final void PlayerBucketEmptyEvent(PlayerBucketEmptyEvent event,
			BlockRow1 row) {

	}

	public final void PlayerBucketEvent(PlayerBucketEvent event, BlockRow1 row) {

	}

	public final void PlayerBucketFillEvent(PlayerBucketFillEvent event,
			BlockRow1 row) {

	}

	public final void PlayerChangedWorldEvent(PlayerChangedWorldEvent event,
			BlockRow1 row) {

	}

	public final void PlayerChannelEvent(PlayerChannelEvent event, BlockRow1 row) {

	}

	public final void PlayerChatTabCompleteEvent(
			PlayerChatTabCompleteEvent event, BlockRow1 row) {

	}

	public final void PlayerCommandPreprocessEvent(
			PlayerCommandPreprocessEvent event, BlockRow1 row) {

	}

	public final void PlayerDropItemEvent(PlayerDropItemEvent event,
			BlockRow1 row) {

	}

	public final void PlayerEggThrowEvent(PlayerEggThrowEvent event,
			BlockRow1 row) {

	}

	public final void PlayerEvent(PlayerEvent event, BlockRow1 row) {

	}

	public final void PlayerExpChangeEvent(PlayerExpChangeEvent event,
			BlockRow1 row) {

	}

	public final void PlayerFishEvent(PlayerFishEvent event, BlockRow1 row) {

	}

	public final void PlayerGameModeChangeEvent(
			PlayerGameModeChangeEvent event, BlockRow1 row) {

	}

	public final void PlayerInteractEntityEvent(
			PlayerInteractEntityEvent event, BlockRow1 row) {

	}

	public final void PlayerInteractEvent(PlayerInteractEvent event,
			BlockRow1 row) {

	}

	public final void PlayerItemBreakEvent(PlayerItemBreakEvent event,
			BlockRow1 row) {

	}

	public final void PlayerItemHeldEvent(PlayerItemHeldEvent event,
			BlockRow1 row) {

	}

	public final void PlayerJoinEvent(PlayerJoinEvent event, BlockRow1 row) {

	}

	public final void PlayerKickEvent(PlayerKickEvent event, BlockRow1 row) {

	}

	public final void PlayerLevelChangeEvent(PlayerLevelChangeEvent event,
			BlockRow1 row) {

	}

	public final void PlayerLoginEvent(PlayerLoginEvent event, BlockRow1 row) {

	}

	public final void PlayerPickupItemEvent(PlayerPickupItemEvent event,
			BlockRow1 row) {

	}

	public final void PlayerPortalEvent(PlayerMoveEvent event, BlockRow1 row) {

	}

	public final void PlayerQuitEvent(PlayerQuitEvent event, BlockRow1 row) {

	}

	public final void PlayerRegisterChannelEvent(
			PlayerRegisterChannelEvent event, BlockRow1 row) {

	}

	public final void PlayerRespawnEvent(PlayerRespawnEvent event, BlockRow1 row) {

	}

	public final void PlayerShearEntityEvent(PlayerShearEntityEvent event,
			BlockRow1 row) {

	}

	public final void PlayerTeleportEvent(PlayerTeleportEvent event,
			BlockRow1 row) {

	}

	public final void PlayerToggleFlightEvent(PlayerToggleFlightEvent event,
			BlockRow1 row) {

	}

	public final void PlayerToggleSneakEvent(PlayerToggleSneakEvent event,
			BlockRow1 row) {

	}

	public final void PlayerToggleSprintEvent(PlayerToggleSprintEvent event,
			BlockRow1 row) {

	}

	public final void PlayerUnregisterChannelEvent(
			PlayerUnregisterChannelEvent event, BlockRow1 row) {

	}

	public final void PlayerVelocityEvent(PlayerVelocityEvent event,
			BlockRow1 row) {

	}

	// Vehicle listeners

	public final void VehicleBlockCollisionEvent(
			VehicleBlockCollisionEvent event, BlockRow1 row) {

	}

	public final void VehicleCollisionEvent(VehicleCollisionEvent event,
			BlockRow1 row) {

	}

	public final void VehicleCreateEvent(VehicleCreateEvent event, BlockRow1 row) {

	}

	public final void VehicleDamageEvent(VehicleDamageEvent event, BlockRow1 row) {

	}

	public final void VehicleDestroyEvent(VehicleDestroyEvent event,
			BlockRow1 row) {

	}

	public final void VehicleEnterEvent(VehicleEnterEvent event, BlockRow1 row) {

	}

	public final void VehicleEntityCollisionEvent(
			VehicleEntityCollisionEvent event, BlockRow1 row) {

	}

	public final void VehicleEvent(VehicleEvent event, BlockRow1 row) {

	}

	public final void VehicleExitEvent(VehicleExitEvent event, BlockRow1 row) {

	}

	public final void VehicleMoveEvent(VehicleMoveEvent event, BlockRow1 row) {

	}

	public final void VehicleUpdateEvent(VehicleUpdateEvent event, BlockRow1 row) {

	}

	// Weather listeners

	public final void LightningStrikeEvent(LightningStrikeEvent event,
			BlockRow1 row) {

	}

	// World listeners

	public final void PortalCreateEvent(PortalCreateEvent event, BlockRow1 row) {

	}

	public final void StructureGrowEvent(StructureGrowEvent event, BlockRow1 row) {

	}

	/*------EventListeners------*/

	// All block listeners

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {

	}

	@EventHandler
	public void onBlockBurnEvent(BlockBurnEvent event) {

	}

	@EventHandler
	public void onBlockCanBuildEvent(BlockCanBuildEvent event) {

	}

	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {

	}

	@EventHandler
	public void onBlockDispenseEvent(BlockDispenseEvent event) {

	}

	@EventHandler
	public void onBlockEvent(BlockEvent event) {

	}

	@EventHandler
	public void onBlockExpEvent(BlockExpEvent event) {

	}

	@EventHandler
	public void onBlockFadeEvent(BlockFadeEvent event) {

	}

	@EventHandler
	public void onBlockFormEvent(BlockFormEvent event) {

	}

	@EventHandler
	public void onBlockFromToEvent(BlockFromToEvent event) {

	}

	@EventHandler
	public void onBlockGrowEvent(BlockGrowEvent event) {

	}

	@EventHandler
	public void onBlockIgniteEvent(BlockIgniteEvent event) {

	}

	@EventHandler
	public void onBlockPhysicsEvent(BlockPhysicsEvent event) {

	}

	@EventHandler
	public void onBlockPistonEvent(BlockPistonEvent event) {

	}

	@EventHandler
	public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {

	}

	@EventHandler
	public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {

	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {

	}

	@EventHandler
	public void onBlockRedstoneEvent(BlockRedstoneEvent event) {

	}

	@EventHandler
	public void onBlockSpreadEvent(BlockSpreadEvent event) {

	}

	@EventHandler
	public void onEntityBlockFormEvent(EntityBlockFormEvent event) {

	}

	@EventHandler
	public void onLeavesDecayEvent(LeavesDecayEvent event) {

	}

	@EventHandler
	public void onNotePlayEvent(NotePlayEvent event) {

	}

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent event) {

	}

	// Enchantment Listeners

	@EventHandler
	public void onEnchantItemEvent(EnchantItemEvent event) {

	}

	@EventHandler
	public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {

	}

	// Entity listeners

	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) {

	}

	public void onCreeperPowerEvent(CreeperPowerEvent event) {

	}

	public void onEntityBreakDoorEvent(EntityBreakDoorEvent event) {

	}

	public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {

	}

	public void onEntityCombustByBlockEvent(EntityCombustByBlockEvent event) {

	}

	public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event) {

	}

	public void onEntityCombustEvent(EntityCombustEvent event) {

	}

	public void onEntityCreatePortalEvent(EntityCreatePortalEvent event) {

	}

	public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event) {

	}

	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

	}

	public void onEntityDamageEvent(EntityDamageEvent event) {

	}

	public void onEntityEvent(EntityEvent event) {

	}

	public void onEntityExplodeEvent(EntityExplodeEvent event) {

	}

	public void onEntityInteractEvent(EntityInteractEvent event) {

	}

	public void onEntityPortalEnterEvent(EntityPortalEnterEvent event) {

	}

	public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {

	}

	public void onEntityShootBowEvent(EntityShootBowEvent event) {

	}

	public void onEntityTameEvent(EntityTameEvent event) {

	}

	public void onEntityTargetEvent(EntityTargetEvent event) {

	}

	public void onEntityTargetLivingEntityEvent(
			EntityTargetLivingEntityEvent event) {

	}

	public void onEntityTeleportEvent(EntityTeleportEvent event) {

	}

	public void onExpBottleEvent(ExpBottleEvent event) {

	}

	public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {

	}

	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {

	}

	public void onItemDespawnEvent(ItemDespawnEvent event) {

	}

	public void onItemSpawnEvent(ItemSpawnEvent event) {

	}

	public void onPigZapEvent(PigZapEvent event) {

	}

	public void onPlayerDeathEvent(PlayerDeathEvent event) {

	}

	public void onPotionSplashEvent(PotionSplashEvent event) {

	}

	public void onProjectileHitEvent(ProjectileHitEvent event) {

	}

	public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {

	}

	public void onSheepDyeWoolEvent(SheepDyeWoolEvent event) {

	}

	public void onSheepRegrowWoolEvent(SheepRegrowWoolEvent event) {

	}

	public void onSlimeSplitEvent(SlimeSplitEvent event) {

	}

	// Hanging listeners

	public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event) {

	}

	public void onHangingBreakEvent(HangingBreakEvent event) {

	}

	public void onHangingEvent(HangingEvent event) {

	}

	public void onHangingPlaceEvent(HangingPlaceEvent event) {

	}

	// Inventory listeners

	public void onBrewEvent(BrewEvent event) {

	}

	public void onCraftItemEvent(CraftItemEvent event) {

	}

	public void onFurnaceBurnEvent(FurnaceBurnEvent event) {

	}

	public void onFurnaceExtractEvent(FurnaceExtractEvent event) {

	}

	public void onFurnaceSmeltEvent(FurnaceSmeltEvent event) {

	}

	public void onInventoryClickEvent(InventoryClickEvent event) {

	}

	public void onInventoryCloseEvent(InventoryCloseEvent event) {

	}

	public void onInventoryEvent(InventoryEvent event) {

	}

	public void onInventoryOpenEvent(InventoryOpenEvent event) {

	}

	public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {

	}

	// Player listeners

	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

	}

	public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {

	}

	public void onPlayerAnimationEvent(PlayerAnimationEvent event) {

	}

	public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {

	}

	public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {

	}

	public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {

	}

	public void onPlayerBucketEvent(PlayerBucketEvent event) {

	}

	public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) {

	}

	public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {

	}

	public void onPlayerChannelEvent(PlayerChannelEvent event) {

	}

	public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent event) {

	}

	public void onPlayerCommandPreprocessEvent(
			PlayerCommandPreprocessEvent event) {

	}

	public void onPlayerDropItemEvent(PlayerDropItemEvent event) {

	}

	public void onPlayerEggThrowEvent(PlayerEggThrowEvent event) {

	}

	public void onPlayerEvent(PlayerEvent event) {

	}

	public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {

	}

	public void onPlayerFishEvent(PlayerFishEvent event) {

	}

	public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {

	}

	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {

	}

	public void onPlayerInteractEvent(PlayerInteractEvent event) {

	}

	public void onPlayerItemBreakEvent(PlayerItemBreakEvent event) {

	}

	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {

	}

	public void onPlayerJoinEvent(PlayerJoinEvent event) {

	}

	public void onPlayerKickEvent(PlayerKickEvent event) {

	}

	public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent event) {

	}

	public void onPlayerLoginEvent(PlayerLoginEvent event) {

	}

	public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {

	}

	public void onPlayerPortalEvent(PlayerMoveEvent event) {

	}

	public void onPlayerQuitEvent(PlayerQuitEvent event) {

	}

	public void onPlayerRegisterChannelEvent(PlayerRegisterChannelEvent event) {

	}

	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {

	}

	public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {

	}

	public void onPlayerTeleportEvent(PlayerTeleportEvent event) {

	}

	public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) {

	}

	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {

	}

	public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent event) {

	}

	public void onPlayerUnregisterChannelEvent(
			PlayerUnregisterChannelEvent event) {

	}

	public void onPlayerVelocityEvent(PlayerVelocityEvent event) {

	}

	// Vehicle listeners

	public void onVehicleBlockCollisionEvent(VehicleBlockCollisionEvent event) {

	}

	public void onVehicleCollisionEvent(VehicleCollisionEvent event) {

	}

	public void onVehicleCreateEvent(VehicleCreateEvent event) {

	}

	public void onVehicleDamageEvent(VehicleDamageEvent event) {

	}

	public void onVehicleDestroyEvent(VehicleDestroyEvent event) {

	}

	public void onVehicleEnterEvent(VehicleEnterEvent event) {

	}

	public void onVehicleEntityCollisionEvent(VehicleEntityCollisionEvent event) {

	}

	public void onVehicleEvent(VehicleEvent event) {

	}

	public void onVehicleExitEvent(VehicleExitEvent event) {

	}

	public void onVehicleMoveEvent(VehicleMoveEvent event) {

	}

	public void onVehicleUpdateEvent(VehicleUpdateEvent event) {

	}

	// Weather listeners

	@EventHandler
	public void onLightningStrikeEvent(LightningStrikeEvent event) {

	}

	// World listeners

	@EventHandler
	public void onPortalCreateEvent(PortalCreateEvent event) {

	}

	@EventHandler
	public void onStructureGrowEvent(StructureGrowEvent event) {

	}

}
