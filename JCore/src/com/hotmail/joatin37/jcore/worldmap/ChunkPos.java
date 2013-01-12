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

/**
 * Used by the WorldMapCahce in order to represent a Chunk.
 * 
 * @author Joatin
 * @since 1.0.0
 * 
 */
public class ChunkPos {

	/**
	 * This ChunkPos X value.
	 */
	private final int x;

	/**
	 * This ChunkPos Z value.
	 */
	private final int z;

	/**
	 * Returns a new ChunkPos
	 * 
	 * @param x
	 *            The X coordinate
	 * @param z
	 *            The Z coordinate
	 * @return A new ChunkPos
	 * @since 1.0.0
	 */
	public static ChunkPos Wrap(int x, int z) {
		return new ChunkPos(x, z);
	}

	/**
	 * Constructs a new ChunkPos
	 * 
	 * @param x
	 *            The X coordinate
	 * @param z
	 *            The Z coordinate
	 */
	private ChunkPos(int x, int z) {
		this.x = x;
		this.z = z;
	}

	/**
	 * Returns the X coordinate of this ChunkPos.
	 * 
	 * @return The X coordinate as an int.
	 * @since 1.0.0
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns the Z coordinate of this ChunkPos.
	 * 
	 * @return The Z coordinate as an int.
	 * @since 1.0.0
	 */
	public int getZ() {
		return this.z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!(obj instanceof ChunkPos)) {
			return false;
		}
		ChunkPos other = (ChunkPos) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.z != other.z) {
			return false;
		}
		return true;
	}

}
