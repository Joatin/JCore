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

import java.nio.ByteBuffer;
import java.util.UUID;

public final class BlockRow1 {

	public static int Size = 44;

	private final UUID collection;
	private UUID plot;
	private final int posx;
	private final int posz;
	private final short maxheight;
	private final short minheight;

	public BlockRow1(UUID collection, UUID plot, int posx, int posz,
			int maxheight, int minheight) {
		if (collection == null) {
			throw new NullPointerException();
		}
		this.collection = collection;
		this.plot = plot;
		this.posx = posx;
		this.posz = posz;
		this.maxheight = (short) maxheight;
		this.minheight = (short) minheight;
	}

	public byte[] getBytes() {
		ByteBuffer buff = ByteBuffer.wrap(new byte[44]);
		buff.putLong(this.collection.getMostSignificantBits());
		buff.putLong(this.collection.getLeastSignificantBits());
		if (this.plot != null) {
			buff.putLong(this.plot.getMostSignificantBits());
			buff.putLong(this.plot.getLeastSignificantBits());
		} else {
			buff.putLong(0l);
			buff.putLong(0l);
		}
		buff.putInt(this.posx);
		buff.putInt(this.posz);
		buff.putShort(this.maxheight);
		buff.putShort(this.minheight);

		return buff.array();

	}

	public UUID getCollectionId() {
		return this.collection;
	}

	public UUID getPlotId() {
		return this.plot;
	}

	public int getMaxHeight() {
		return this.maxheight;
	}

	public int getMinHeight() {
		return this.minheight;
	}

	public int getX() {
		return this.posx;
	}

	public int getZ() {
		return this.posz;
	}

}
