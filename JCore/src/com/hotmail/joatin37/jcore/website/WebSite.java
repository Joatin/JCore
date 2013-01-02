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

package com.hotmail.joatin37.jcore.website;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.bukkit.configuration.file.FileConfiguration;

import com.hotmail.joatin37.jcore.Core;

public class WebSite implements Runnable {

	private Core core;
	private FileConfiguration config = null;
	private File configfile = null;
	private ServerSocket socket;
	private final WebPageManager manager;
	private Thread thread;

	public WebSite(Core core) {
		this.manager = new WebPageManager(core);
		this.core = core;
		try {
			this.socket = new ServerSocket(this.core.getConfig().getInt(
					"website.port", 443));
		} catch (IOException e) {
			core.getLogger()
					.warning(
							"[WebSite] Couldn't create the websocket. Please check your firewall settings.");

		}
		this.thread = new Thread(this);
		this.thread.setName("WebSite");
		this.thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket csocket = this.socket.accept();
				this.core.getLogger().info("[WebSite] New connection!!!");
				this.manager.handleConnection(csocket);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
