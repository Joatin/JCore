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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;
import com.hotmail.joatin37.jcore.ChunkPos;
import com.hotmail.joatin37.jcore.Core;
import com.hotmail.joatin37.jcore.api.Collection;
import com.hotmail.joatin37.jcore.api.ICollectionManager;
import com.hotmail.joatin37.jcore.api.Plot;

public class WorldMapCache extends LinkedHashMap<ChunkPos, JChunk> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int VERSION = 1;

	private final int MAX_ENTRIES;

	private final List<ChunkPos> allchunks;

	private final HashMap<ChunkPos, JChunk> dirtychunks;

	private final File savefile;

	private final JavaPlugin core;

	private final String world;

	private final ICollectionManager manager;

	public WorldMapCache(int maxcapacity, File file, JavaPlugin core,
			String world, ICollectionManager manager) {
		super(maxcapacity, 0.75f, true);
		this.world = world;
		this.MAX_ENTRIES = maxcapacity;
		this.savefile = file;
		this.core = core;
		this.manager = manager;
		if (this.manager == null) {
			throw new NullPointerException();
		}
		this.allchunks = this.loadAllReferences();
		this.dirtychunks = new HashMap<ChunkPos, JChunk>();
	}

	/**
	 * Returns the world this worldmapcache is bound to.
	 * 
	 * @return The name of the world.
	 * @since 1.0.0
	 */
	public String getWorld() {
		return this.world;
	}

	protected void putJ(ChunkPos key, JChunk value) {
		this.put(key, value);
		this.dirtychunks.put(key, value);
		if (!this.allchunks.contains(key)) {
			this.allchunks.add(key);
		}

	}

	public void set(Location loc, Collection coll, Plot plot, short maxheight,
			short minheight) {
		ChunkPos pos = ChunkPos.Wrap(loc.getChunk().getX(), loc.getChunk()
				.getZ());
		JChunk chunk;
		UUID cuuid;
		UUID plotuuid = null;
		BlockRow1 row;
		if (coll == null) {
			row = null;
		} else {
			cuuid = coll.getUUID();
			if (plot != null) {
				plotuuid = plot.getUUID();
			}
			row = new BlockRow1(cuuid, plotuuid, loc.getBlockX(),
					loc.getBlockZ(), maxheight, minheight);
		}
		if ((chunk = this.dirtychunks.get(pos)) != null) {
			chunk.put(loc.getBlockX(), loc.getBlockZ(), row);
			this.putJ(pos, chunk);
		} else {
			chunk = super.get(loc);
			if (chunk == null) {
				chunk = this.getJChunk(pos);
				if (chunk == null) {
					chunk = new JChunk(loc.getChunk().getX(), loc.getChunk()
							.getZ(), this.manager);
				}
			}
			chunk.put(loc.getBlockX(), loc.getBlockZ(), row);
			this.putJ(pos, chunk);
		}
		return;
		// TODO
	}

	public void save() {
		// TODO
		File sfile;
		DataInputStream input = null;
		DataOutputStream output;
		JChunk chunk;
		try {
			sfile = File.createTempFile("tempfile", null,
					this.savefile.getParentFile());
			try {
				input = new DataInputStream(new FileInputStream(this.savefile));
			} catch (FileNotFoundException e) {
			}
			output = new DataOutputStream(new FileOutputStream(sfile));
			output.writeInt(this.VERSION);
			if (input != null) {
				try {
					input.readInt(); // reads the version int;
					while (true) {
						int size = input.readInt();
						int a = input.readInt();
						int b = input.readInt();
						ChunkPos pos = ChunkPos.Wrap(a, b);
						if ((chunk = this.dirtychunks.get(pos)) != null) {
							input.skip(size - 8);
							byte[] c = chunk.getBytes();
							output.writeInt(c.length);
							output.write(c);
						} else {
							byte[] ba = new byte[size - 8];
							input.read(ba);
							output.writeInt(size);
							output.writeInt(a);
							output.writeInt(b);
							output.write(ba);
						}
					}
				} catch (EOFException e) {
					input.close();
				}
			}
			if (this.dirtychunks.size() != 0) {
				Iterator<JChunk> dirty = this.dirtychunks.values().iterator();
				while (dirty.hasNext()) {
					byte[] w = dirty.next().getBytes();
					if (Core.isDebugg()) {
						this.core.getLogger().info(
								"Writing bytes of lenght" + w.length);
					}
					output.writeInt(w.length);
					output.write(w);
				}
			}
			output.flush();
			output.close();
			this.savefile.delete();
			Files.copy(sfile, this.savefile);
			sfile.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private List<ChunkPos> loadAllReferences() {
		Vector<ChunkPos> vec = null;
		DataInputStream input;
		if (this.manager == null) {
			throw new NullPointerException();
		}
		try {
			input = new DataInputStream(new FileInputStream(this.savefile));
			int i1 = input.readInt(); // reads the version number;
			if (Core.isDebugg()) {
				this.core.getLogger().info("" + i1);
			}
			vec = new Vector<ChunkPos>(100, 20);
			try {
				while (true) {
					int size = input.readInt();
					int a = input.readInt();
					int b = input.readInt();
					vec.add(ChunkPos.Wrap(a, b));
					byte[] q = new byte[size - 8];
					input.read(q);
					new JChunk(a, b, this.manager).reconstructFromBytes(
							this.VERSION, q);

				}
			} catch (EOFException e) {
				input.close();
			}
		} catch (FileNotFoundException e) {
			this.core.getLogger().warning(
					"The file " + this.savefile + " was missing");
		} catch (IOException e) {

		}
		if (vec == null) {
			vec = new Vector<ChunkPos>();
		}
		return vec;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<ChunkPos, JChunk> eldest) {
		return this.size() > this.MAX_ENTRIES;
	}

	public BlockRow1 get(Location loc) {
		ChunkPos pos = ChunkPos.Wrap(loc.getChunk().getX(), loc.getChunk()
				.getZ());
		if (this.containsKey(pos)) {
			return this.get(pos).get(loc);
		} else {
			JChunk jchunk = this.getJChunk(pos);
			if (jchunk == null) {
				return null;
			} else {
				return jchunk.get(loc);
			}
		}

	}

	private JChunk getJChunk(ChunkPos pos) {
		if (this.allchunks.contains(pos)) {
			DataInputStream input;
			try {
				input = new DataInputStream(new FileInputStream(this.savefile));
				input.skip(4);
				try {
					while (true) {
						int size = input.readInt();
						int a = input.readInt();
						int b = input.readInt();
						if (a == pos.getX() && b == pos.getZ()) {
							JChunk chunk = new JChunk(a, b, this.manager);
							byte[] u = new byte[size - 8];
							input.read(u);
							chunk.reconstructFromBytes(this.VERSION, u);
							input.close();
							this.put(ChunkPos.Wrap(a, b), chunk);
							return chunk;
						}

						input.skip(size - 8);

					}

				} catch (EOFException e) {
					input.close();
					return null;
				}
			} catch (FileNotFoundException e) {
				this.core.getLogger().warning(
						"No the file " + this.savefile + " was missing");
				this.savefile.mkdir();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

}
