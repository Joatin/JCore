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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class ConnectionHandler extends Thread {

	private final Socket socket;
	private final WebPageManager manager;

	// Variables
	private String request;
	private String resource;
	private String httpversion;
	private String host;
	private String preferedlang;
	private String connectiontype;
	private final HashMap<String, String> cookies;
	private boolean DNT = false;

	public ConnectionHandler(Socket socket, WebPageManager manager) {
		this.socket = socket;
		this.manager = manager;
		this.cookies = new HashMap<String, String>();
		this.start();
	}

	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
			BufferedOutputStream output = new BufferedOutputStream(
					this.socket.getOutputStream());
			this.handle(input, output);

			output.write("Hello world!!!".getBytes());
			output.flush();
			this.socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handle(BufferedReader input, BufferedOutputStream output) {
		try {
			String req;
			req = input.readLine();
			String[] s2 = req.split(" ");
			if (s2[0].equalsIgnoreCase("GET") || s2[0].equalsIgnoreCase("HEAD")) {
				this.request = s2[0];
				this.resource = s2[1].replace("/", "");
				this.httpversion = s2[2];
			} else {
				this.onUnknownRequest(input, output);
				return;
			}

			String[] s;
			String s5;
			while ((s5 = input.readLine()) != null) {
				s = s5.split(" ");
				if (s[0].toUpperCase().startsWith(
						"Accept-Language".toUpperCase())) {
					this.preferedlang = s[1].split(",")[0];
				}
				if (s[0].toUpperCase().startsWith("Connection".toUpperCase())) {
					this.connectiontype = s[1];
				}
				if (s[0].toUpperCase().startsWith("Cookie".toUpperCase())) {
					for (int i = 1; i < s.length; i++) {
						String[] split = s[i].split("=");
						this.cookies.put(split[0], split[1].replace(";", ""));
					}
				}
				if (s[0].toUpperCase().startsWith("Host".toUpperCase())) {
					this.host = s[1];
				}
				if (s[0].toUpperCase().startsWith("DNT".toUpperCase())) {
					if (Integer.parseInt(s[1]) == 1) {
						this.DNT = true;
					}
				}
			}
			if (this.request.equalsIgnoreCase("GET")) {
				this.handleGet(input, output);
			}
			if (this.request.equalsIgnoreCase("HEAD")) {
				this.handleHead(input, output);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void handleGet(BufferedReader input, BufferedOutputStream output) {
		if (this.resource.equalsIgnoreCase("StyleSheet.css")) {
			this.returnPage(input, output, true);
			return;
		}
		if (this.cookies.get("UserID") != null
				&& this.cookies.get("Password") != null) {
			String s1 = this.cookies.get("UserID");
			String s2 = this.cookies.get("Password");
		}
	}

	private void returnLogin(BufferedReader input, BufferedOutputStream output) {

	}

	private void returnPage(BufferedReader input, BufferedOutputStream output,
			boolean withContent) {

	}

	private void handleHead(BufferedReader input, BufferedOutputStream output) {
		if (this.resource.equalsIgnoreCase("StyleSheet.css")) {
			this.returnPage(input, output, false);
		}
	}

	private void onUnknownRequest(BufferedReader input,
			BufferedOutputStream output) {

	}

}
