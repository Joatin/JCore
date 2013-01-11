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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.hotmail.joatin37.jcore.api.Collection;
import com.hotmail.joatin37.jcore.api.IWorldMap;
import com.hotmail.joatin37.jcore.api.Plot;


class EditHolder implements Runnable {
	private boolean pinswitch = true;
	private boolean drawpin = true;
	private boolean draw = true;
	private Location pinpointa;
	private Location pinpointb;
	private List<simpleblockrow> markedarea;
	private double cost;
	private IWorldMap map;
	private Collection coll;
	private Player player;
	private Plot plot;
	private JavaPlugin plugin;
	private BukkitTask task;

	protected EditHolder(JavaPlugin plugin, Player player, Collection coll,
			IWorldMap map) {
		this.coll = coll;
		this.map = map;
		this.plugin = plugin;
		this.player = player;
		this.markedarea = new Vector<simpleblockrow>();
		this.task = plugin.getServer().getScheduler()
				.runTaskTimer(plugin, this, 0, 20);
	}

	public void Stop() {
		this.plugin.getServer().getScheduler()
				.cancelTask(this.task.getTaskId());
	}

	protected EditHolder(JavaPlugin plugin, Player player, Plot plot,
			IWorldMap map) {
		this.coll = plot.getParent();
		this.plot = plot;
		this.map = map;
		this.plugin = plugin;
		this.player = player;
	}

	public void createSquare(Player player) {
		if (this.pinpointa != null && this.pinpointb != null) {

		} else {
			// TODO tell the player that both points must be set;
			player.sendMessage("§4 can not create square here");
		}
	}

	public boolean addPinPoint(Location loc) {
		if (this.coll != null && this.plot == null) {
			if (this.map.canSet(loc)) {
				if (this.pinswitch) {
					this.pinpointa = loc;
					this.pinswitch = false;
					return true;
				} else {
					this.pinpointb = loc;
					this.pinswitch = true;
					return true;
				}
			} else {
				return false;
			}
		} else {
			if (this.coll != null && this.plot != null) {
				if (this.map.canSet(loc, this.plot.getParent())) {
					if (this.pinswitch) {
						this.pinpointa = loc;
						this.pinswitch = false;
						return true;
					} else {
						this.pinpointb = loc;
						this.pinswitch = true;
						return true;
					}
				} else {
					return false;
				}
			}
			return false;
		}
	}

	@Override
	public void run() {
		if (this.draw) {
			Iterator<simpleblockrow> it = this.markedarea.iterator();
			while (it.hasNext()) {
				simpleblockrow row = it.next();
				for (int i = row.getMinheight(); i <= row.getMaxheight(); i++) {
					Location loc = new Location(this.plugin.getServer()
							.getWorld(row.getWorld()), row.getX(), 0,
							row.getZ());
					if (loc.getBlock().getType() != Material.AIR) {
						this.player.sendBlockChange(
								new Location(this.plugin.getServer().getWorld(
										row.getWorld()), row.getX(), 0, row
										.getZ()), Material.WOOL, (byte) 4);
					}
				}
			}
			if (this.pinpointa != null) {
				this.player.sendBlockChange(this.pinpointa, Material.WOOL,
						(byte) 1);
			}
			this.draw = false;
		} else {
			Iterator<simpleblockrow> it = this.markedarea.iterator();
			while (it.hasNext()) {
				simpleblockrow row = it.next();
				for (int i = row.getMinheight(); i <= row.getMaxheight(); i++) {
					Location loc = new Location(this.plugin.getServer()
							.getWorld(row.getWorld()), row.getX(), 0,
							row.getZ());
					if (loc.getBlock().getType() != Material.AIR) {
						this.player.sendBlockChange(
								new Location(this.plugin.getServer().getWorld(
										row.getWorld()), row.getX(), 0, row
										.getZ()), Material.WOOL, (byte) 6);
					}
				}
			}
			this.draw = true;
		}

	}

	protected class simpleblockrow {

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.getOuterType().hashCode();
			result = prime * result + this.maxheight;
			result = prime * result + this.minheight;
			result = prime * result
					+ ((this.world == null) ? 0 : this.world.hashCode());
			result = prime * result + this.x;
			result = prime * result + this.z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof simpleblockrow)) {
				return false;
			}
			simpleblockrow other = (simpleblockrow) obj;
			if (!this.getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (this.maxheight != other.maxheight) {
				return false;
			}
			if (this.minheight != other.minheight) {
				return false;
			}
			if (this.world == null) {
				if (other.world != null) {
					return false;
				}
			} else if (!this.world.equals(other.world)) {
				return false;
			}
			if (this.x != other.x) {
				return false;
			}
			if (this.z != other.z) {
				return false;
			}
			return true;
		}

		private final String world;
		private final int x;
		private final int z;
		private final int maxheight;
		private final int minheight;

		protected simpleblockrow(String world, int x, int z, int maxheight,
				int minheight) {
			this.world = world;
			this.x = x;
			this.z = z;
			this.maxheight = maxheight;
			this.minheight = minheight;
		}

		public int getX() {
			return this.x;
		}

		public int getZ() {
			return this.z;
		}

		public int getMaxheight() {
			return this.maxheight;
		}

		public int getMinheight() {
			return this.minheight;
		}

		public String getWorld() {
			return this.world;
		}

		private EditHolder getOuterType() {
			return EditHolder.this;
		}

	}
}
