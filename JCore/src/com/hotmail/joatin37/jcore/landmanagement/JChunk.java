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

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Location;

import com.hotmail.joatin37.jcore.core.Core;

public class JChunk extends HashMap<BlockPos, BlockRow1> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int x;
	private final int z;
	private final ICollectionManager manager;

	public JChunk(int x, int z, ICollectionManager manager) {
		this.x = x;
		this.z = z;
		if (manager == null) {
			throw new NullPointerException();
		}
		this.manager = manager;

	}

	public BlockRow1 remove(BlockPos pos) {
		BlockRow1 row = this.get(pos);
		if (row.getCollectionId() != null) {
			this.manager.getCollection(row.getCollectionId()).removeLandMass();
			if (row.getPlotId() != null) {
				this.manager.getCollection(row.getCollectionId())
						.getPlot(row.getPlotId()).removeLandMass();
			}
		}
		return super.remove(pos);

	}

	@Override
	public BlockRow1 put(BlockPos pos, BlockRow1 row) {
		if (this.containsKey(pos)) {
			this.remove(pos);
		}
		if (row.getCollectionId() != null) {
			this.manager.getCollection(row.getCollectionId()).addLandMass();
			if (row.getPlotId() != null) {
				this.manager.getCollection(row.getCollectionId())
						.getPlot(row.getPlotId()).addLandMass();
			}
		}

		super.put(pos, row);
		return row;

	}

	public void put(int x, int z, BlockRow1 row) {
		this.put(new BlockPos(x, z), row);
	}

	public BlockRow1 get(Location loc) {
		return this.get(new BlockPos(loc.getBlockX(), loc.getBlockZ()));
	}

	public void reconstructFromBytes(int VERSION, byte[] bytes) {
		if (VERSION == 1) {
			ByteBuffer buff = ByteBuffer.wrap(bytes);
			for (int i = 0; i < buff.capacity() / BlockRow1.Size; i++) {
				long c1 = buff.getLong();
				long c2 = buff.getLong();
				long p1 = buff.getLong();
				long p2 = buff.getLong();
				if (c1 == 0 && c2 == 0) {
					continue;
				}
				UUID collection = new UUID(c1, c2);
				if (Core.isDebugg()) {
					System.out.println(c1 + ";" + c2);
					System.out.print(this.manager == null);
				}
				this.manager.getCollection(collection).addLandMass();
				UUID plot = null;
				if (p1 != 0 && p2 != 0) {
					plot = new UUID(p1, p2);
					this.manager.getCollection(collection).getPlot(plot)
							.addLandMass();
				}

				int x = buff.getInt();
				int z = buff.getInt();
				short max = buff.getShort();
				short min = buff.getShort();
				this.put(new BlockPos(x, z), new BlockRow1(collection, plot, x,
						z, max, min));
			}
		}
	}

	public byte[] getBytes() {
		ByteBuffer buff = ByteBuffer.wrap(new byte[this.size() * BlockRow1.Size
				+ 8]);
		buff.putInt(this.x);
		buff.putInt(this.z);
		Iterator<BlockRow1> it = this.values().iterator();
		while (it.hasNext()) {
			buff.put(it.next().getBytes());
		}
		return buff.array();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.x;
		result = prime * result + this.z;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof JChunk)) {
			return false;
		}
		JChunk other = (JChunk) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.z != other.z) {
			return false;
		}
		return true;
	}

}
