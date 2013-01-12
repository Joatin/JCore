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

package com.hotmail.joatin37.jcore.worldmap;

import java.util.UUID;

import org.bukkit.Location;


/**
 * The interface for the collectionmanager. It provides all usefull methods.
 * 
 * @author Joatin
 * 
 * @since 1.0.0
 * 
 */
public interface ICollectionManager {
	public Plot reconstructPlot(String plugin, String type, Collection parent,
			UUID uuid);

	/**
	 * The method to add a new collection. The manger does not take care of any
	 * money handling, or anything else like that. All it does it is add it to
	 * the list. All collections gets a 3x3 area registered to them on creation,
	 * based on the location provided. If this 3x3 area is already claimed by
	 * another collection, the collection won't be added and the method will
	 * return false.
	 * 
	 * @param coll
	 *            The collection to add.
	 * @param baseloc
	 *            The center for the 3x3 area that will be claimed for that
	 *            collection. The y axis doesn't matter.
	 * @return True if the collection was added, false otherwise.
	 * 
	 * @since 1.0.0
	 */
	public boolean putNewCollection(Collection coll, Location baseloc);

	/**
	 * Used to get the WorldMap interface. The world map can be used in order to
	 * check if some rows are already claimed by other collections/plots.
	 * 
	 * @return The WorldMap interface, containing all useful commands.
	 * 
	 * @since 1.0.0
	 */
	public IWorldMap getWorldMap();

	/**
	 * Use this to get the current collection the specified player is standing
	 * in.
	 * 
	 * @param player
	 *            The name of the player you wan't to get info on.
	 * @return The collection if the player is inside one. Null if he isn't.
	 * 
	 * @since 1.0.0
	 */
	public Collection getCurrentCollection(String player);

	/**
	 * Use this to get a collection based on its UUID.
	 * 
	 * @param uuid
	 *            The UUID of the collection you are requesting.
	 * 
	 * @return The collection with the UUID specified. Null if that collection
	 *         doesn't exists.
	 * 
	 * @since 1.0.0
	 */
	public Collection getCollection(UUID uuid);
}
