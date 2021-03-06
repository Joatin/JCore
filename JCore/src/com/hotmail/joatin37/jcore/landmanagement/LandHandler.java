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

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.core.ICore;

/**
 * Your plugin should extend this class instead of the JavaPlugin class. This
 * class brings all functions of the JavaPlugin class, and some additional
 * functions to make the interaction with JTown easier.
 * 
 * @see JavaPlugin
 * @see ICore
 * 
 * @author Joatin
 * 
 * @since 1.0.0
 * 
 */
public abstract class LandHandler {

	private final JavaPlugin plugin;
	private final ICore core;
	private final ICollectionManager manager;
	private final IWorldMap map;

	public LandHandler(JavaPlugin plugin) {
		this.plugin = plugin;
		this.core = (ICore) plugin.getServer().getPluginManager()
				.getPlugin("JCore");
		this.core.addExtension(this, plugin);
		this.manager = this.core.getManager();
		this.map = this.manager.getWorldMap();
	}

	public JavaPlugin getPlugin() {
		return this.plugin;
	}

	public ICollectionManager getICollectionManager() {
		return this.manager;
	}

	public void addCollection(Collection coll, Location baseloc) {
		this.manager.putNewCollection(coll, baseloc);
	}

	/**
	 * This method will be called from the CollectionManager in order to
	 * reconstruct a collection.
	 * 
	 * @param kind
	 *            The kind of plot that should be constructed.
	 * @param parent
	 *            The CollectionManager that holds this collection
	 * @param uuid
	 *            The UUID of the collection to be constructed.
	 * @param config
	 *            The Yaml file to load all the data from.
	 * @return The collection constructed.
	 * 
	 * @since 1.0.0
	 */
	public abstract Collection constructCollection(String kind,
			ICollectionManager parent, UUID uuid, FileConfiguration config);

	public abstract Plot constructPlot(String kind, Collection parent, UUID uuid);

	/**
	 * This method should return the plugins name.
	 * 
	 * @return The plugins name
	 * @since 1.0.0
	 */
	public String getName() {
		return this.plugin.getName();
	}

}
