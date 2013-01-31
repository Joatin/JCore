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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RejectedExecutionException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

import com.hotmail.joatin37.jcore.core.Core;
import com.hotmail.joatin37.jcore.language.Lang;

public class ConnectionHandler extends Thread {
	private Core core;
	private final String ksName = "keystore.jks";
	private boolean enabled = false;
	private WebPageManager manager;
	private ForkJoinPool pool;
	private ServerSocket server;
	private final int port;
	private final int backlog;

	public ConnectionHandler(Core core) {
		this.core = core;
		this.pool = new ForkJoinPool();
		this.port = core.getConfig().getInt("website.port", 80);
		this.backlog = core.getConfig().getInt("website.backlog", 100);
		if (core.getConfig().getBoolean("website.enabled", false)) {
			this.enabled = true;
			this.manager = new WebPageManager(core);
			if (core.getConfig().getBoolean("website.https", false)) {
				this.SSLConection();
			} else {
				this.OpenConnection();
			}
			this.setName("Website Thread");
			this.setDaemon(true);
			this.start();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket soc = this.server.accept();
				if (this.pool.getQueuedSubmissionCount() < this.core
						.getConfig().getInt("website.maximum", 100)) {
					this.pool.execute(new Handler(soc));
				} else {
					soc.getOutputStream()
							.write("HTTP/1.1 503 Service Unavailable\r\nServer: JCore\r\nConnection: Close\r\n\r\nThe server can't accept more requests now"
									.getBytes());
					soc.close();
				}
			} catch (IOException e) {
			} catch (RejectedExecutionException e) {

			}
		}
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	private void SSLConection() {
		char ksPass[] = "password".toCharArray();
		char ctPass[] = "tintin37".toCharArray();
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(this.core.getResource(this.ksName), ksPass);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, ctPass);
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);
			SSLServerSocketFactory ssf = sc.getServerSocketFactory();
			this.server = ssf.createServerSocket(this.port, this.backlog);
			System.out.println("Server started:");
		} catch (Exception e) {
		}
	}

	private void OpenConnection() {
		try {
			this.server = new ServerSocket(this.port, this.backlog);
		} catch (IOException e) {
			Lang.sendConsoleSevereMessage(this.core, "AG");
		}
	}

	protected class Handler implements Runnable {

		private final Socket socket;
		private String request;
		private String resource;
		private String language;
		private String username;
		private String password;

		protected Handler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream()));
				OutputStream output = this.socket.getOutputStream();
				String s = reader.readLine();
				this.loadValues(reader);
				byte[] content = ConnectionHandler.this.manager.getResource(
						this.resource, this.language);
				if (!this.request.equalsIgnoreCase("GET")
						|| !this.request.equalsIgnoreCase("HEAD")) {

					output.write("HTTP/1.1 405 bad request".getBytes());
					return;
				} else {

				}
			} catch (IOException e) {
			} finally {
				try {
					this.socket.close();
				} catch (IOException e) {
				}
			}
		}

		private void loadValues(BufferedReader reader) throws IOException {
			String s;
			while ((s = reader.readLine()) != null) {
				if (s.toUpperCase().startsWith("GET")) {
					this.request = "GET";
					this.resource = s.split(" ")[1];
				}
				if (s.toUpperCase().startsWith("ACCEPT-LANGUAGE")) {
					this.language = s.split(" ")[1].split(",")[0];
					Core.sendDebug("Accepted language: " + this.language);
				}
				if (s.toUpperCase().startsWith("COOKIE")) {
					String[] splits = s.split(" ");
					for (int i = 1; i < splits.length; i++) {
						if (splits[i].startsWith(this.username)) {
							this.username = splits[i].split("=")[1];
						}
					}
				}
			}
		}

	}
}
