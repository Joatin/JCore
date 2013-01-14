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
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.core.Core;

public class WorldMap implements IWorldMap {

	private HashMap<String, WorldMapCache> caches;
	private final JavaPlugin plugin;
	private final ICollectionManager manager;

	public WorldMap(JavaPlugin plugin, ICollectionManager manager) {
		this.manager = manager;
		this.plugin = plugin;
		this.caches = new HashMap<String, WorldMapCache>(3, (float) 0.75);
		Iterator<World> iterator = plugin.getServer().getWorlds().iterator();
		plugin.getLogger().info("Starting to load worlds");
		while (iterator.hasNext()) {
			String w = iterator.next().getName();
			Core.sendDebug("Loading world: " + w);
			this.caches.put(
					w,
					new WorldMapCache(this.plugin.getConfig().getInt(
							"chachesize", 1000), new File(this.plugin
							.getDataFolder(), w + ".sav"), this.plugin, w,
							manager));
		}
		plugin.getLogger().info("Done loading worlds");
	}

	public void save() {
		Iterator<WorldMapCache> iterator = this.caches.values().iterator();
		while (iterator.hasNext()) {
			iterator.next().save();
		}
	}

	public BlockRow1 get(Location loc) {
		WorldMapCache cache = this.caches.get(loc.getWorld().getName());
		if (cache == null) {
			return null;
		} else {
			return cache.get(loc);
		}
	}

	/**
	 * Used to see if a collection can be set at that loaction.
	 * 
	 * @param loc
	 * @return if the location can be set.
	 * @since 1.0.0
	 */
	@Override
	public boolean canSet(Location loc) {
		if (loc == null) {
			return false;
		}
		WorldMapCache cache = this.caches.get(loc.getWorld().getName());
		if (cache == null) {
			this.plugin.getLogger().info("Cache fault");
		}
		if (cache.get(loc) == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Used to see if a plot can be set at that location.
	 * 
	 * @param loc
	 * @param coll
	 * @return if a plot can be set there.
	 * @since 1.0.0
	 */
	@Override
	public boolean canSet(Location loc, Collection coll) {
		return false;
		// TODO
	}

	/**
	 * Sets the data for that location. This method will overwrite all previous
	 * data! This method is equal to set(Location loc, Collection coll, Plot
	 * plot, short -1, short -1)
	 * 
	 * @param loc
	 *            The loaction to put the data.
	 * @param coll
	 *            The collection to set. If collection is null, then the data
	 *            will be deleted.
	 * @param plot
	 *            the plot to set. This may be null.
	 * @since 1.0.0
	 */
	@Override
	public void set(Location loc, Collection coll, Plot plot) {
		this.set(loc, coll, plot, (short) -1, (short) -1);
	}

	/**
	 * Sets the data for that location. This method will overwrite all previous
	 * data!
	 * 
	 * @param loc
	 *            The loaction to put the data.
	 * @param coll
	 *            The collection to set. If collection is null, then the data
	 *            will be deleted.
	 * @param plot
	 *            the plot to set. This may be null.
	 * @since 1.0.0
	 */
	@Override
	public void set(Location loc, Collection coll, Plot plot, short maxheight,
			short minheight) {
		this.caches.get(loc.getWorld().getName()).set(loc, coll, plot,
				maxheight, minheight);
	}
}
