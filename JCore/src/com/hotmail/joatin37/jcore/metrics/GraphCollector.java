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

package com.hotmail.joatin37.jcore.util;

import java.io.IOException;
import java.util.Iterator;

import com.hotmail.joatin37.jcore.core.Core;
import com.hotmail.joatin37.jcore.util.BukkitMetrics.Graph;

public class GraphCollector {
	private BukkitMetrics metrics;
	private final Core core;

	public GraphCollector(Core core) {
		this.core = core;

		try {
			this.metrics = new BukkitMetrics(this.core);
			this.metrics.start();
		} catch (IOException e) {
			if (Core.isDebugg()) {
				core.getLogger().info("Couldn't create metrics");
			}
		}
		this.CreateExtensionGraph();

	}

	public void CreateExtensionGraph() {
		Graph graph = this.metrics.createGraph("Plugins using JCore");
		Iterator<String> it = this.core.getExtensionNames().iterator();
		while (it.hasNext()) {
			graph.addPlotter(new exPlotter(it.next()));
		}
		this.metrics.addGraph(graph);
	}

	public class exPlotter extends BukkitMetrics.Plotter {
		protected exPlotter(String name) {
			super(name);
		}

		@Override
		public int getValue() {
			return 1;
		}
	}
}
