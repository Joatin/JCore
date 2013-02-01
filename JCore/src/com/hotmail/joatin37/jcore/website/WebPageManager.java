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

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import com.hotmail.joatin37.jcore.core.Core;

public class WebPageManager {
	private final Core core;

	private byte[] bootstrap_css;
	private byte[] bootstrap_min_css;
	private byte[] glyphicons_halflings_white_png;
	private byte[] glyphicons_halflings_png;
	private byte[] bootstrap_js;
	private byte[] bootstrap_min_js;
	private final static String header = "<head><meta charset=\"utf-8\"><title>Carousel Template &middot; Bootstrap</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta name=\"description\" content=\"\"><meta name=\"author\" content=\"\"><link href=\"bootstrap.css\" rel=\"stylesheet\"></head>";

	public WebPageManager(Core core) {
		this.core = core;
		this.loadbootstrap_css();
		this.loadbootstrap_min_css();
		this.loadglyphicons_halflings_white_png();
		this.loadglyphicons_halflings_png();
		this.loadbootstrap_js();
		this.loadbootstrap_min_js();
	}

	private void loadbootstrap_css() {
		InputStream input = this.core
				.getResource("Bootstrap/css/bootstrap.css");
		LinkedList<Byte> list = new LinkedList<Byte>();
		if (input != null) {
			try {
				byte[] b = new byte[1];
				while (input.read(b) != -1) {
					list.add(b[0]);
				}
				Byte[] bytes = list.toArray(new Byte[0]);
				this.bootstrap_css = new byte[bytes.length];
				for (int i = 0; i < bytes.length; i++) {
					this.bootstrap_css[i] = bytes[i];
				}
			} catch (Exception e) {
				Core.sendDebug("Something went wrong while reading Bootstrap/css/bootstrap.css");
				e.getMessage();
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		} else {
			Core.sendDebug("Couldn't open a Stream to Bootstrap/css/bootstrap.css");
		}
	}

	private void loadbootstrap_min_css() {
		InputStream input = this.core
				.getResource("Bootstrap/css/bootstrap.min.css");
		LinkedList<Byte> list = new LinkedList<Byte>();
		if (input != null) {
			try {
				byte[] b = new byte[1];
				while (input.read(b) != -1) {
					list.add(b[0]);
				}
				Byte[] bytes = list.toArray(new Byte[0]);
				this.bootstrap_min_css = new byte[bytes.length];
				for (int i = 0; i < bytes.length; i++) {
					this.bootstrap_min_css[i] = bytes[i];
				}
			} catch (Exception e) {
				Core.sendDebug("Something went wrong while reading Bootstrap/css/bootstrap.min.css");
				e.getMessage();
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		} else {
			Core.sendDebug("Couldn't open a Stream to Bootstrap/css/bootstrap.min.css");
		}
	}

	private void loadglyphicons_halflings_white_png() {
		InputStream input = this.core
				.getResource("Bootstrap/img/glyphicons-halflings-white.png");
		LinkedList<Byte> list = new LinkedList<Byte>();
		if (input != null) {
			try {
				byte[] b = new byte[1];
				while (input.read(b) != -1) {
					list.add(b[0]);
				}
				Byte[] bytes = list.toArray(new Byte[0]);
				this.glyphicons_halflings_white_png = new byte[bytes.length];
				for (int i = 0; i < bytes.length; i++) {
					this.glyphicons_halflings_white_png[i] = bytes[i];
				}
			} catch (Exception e) {
				Core.sendDebug("Something went wrong while reading Bootstrap/img/glyphicons-halflings-white.png");
				e.getMessage();
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		} else {
			Core.sendDebug("Couldn't open a Stream to Bootstrap/img/glyphicons-halflings-white.png");
		}
	}

	private void loadglyphicons_halflings_png() {
		InputStream input = this.core
				.getResource("Bootstrap/img/glyphicons-halflings.png");
		LinkedList<Byte> list = new LinkedList<Byte>();
		if (input != null) {
			try {
				byte[] b = new byte[1];
				while (input.read(b) != -1) {
					list.add(b[0]);
				}
				Byte[] bytes = list.toArray(new Byte[0]);
				this.glyphicons_halflings_png = new byte[bytes.length];
				for (int i = 0; i < bytes.length; i++) {
					this.glyphicons_halflings_png[i] = bytes[i];
				}
			} catch (Exception e) {
				Core.sendDebug("Something went wrong while reading Bootstrap/img/glyphicons-halflings.png");
				e.getMessage();
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		} else {
			Core.sendDebug("Couldn't open a Stream to Bootstrap/img/glyphicons-halflings.png");
		}
	}

	private void loadbootstrap_js() {
		InputStream input = this.core.getResource("Bootstrap/js/bootstrap.js");
		LinkedList<Byte> list = new LinkedList<Byte>();
		if (input != null) {
			try {
				byte[] b = new byte[1];
				while (input.read(b) != -1) {
					list.add(b[0]);
				}
				Byte[] bytes = list.toArray(new Byte[0]);
				this.bootstrap_js = new byte[bytes.length];
				for (int i = 0; i < bytes.length; i++) {
					this.bootstrap_js[i] = bytes[i];
				}
			} catch (Exception e) {
				Core.sendDebug("Something went wrong while reading Bootstrap/js/bootstrap.js");
				e.getMessage();
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		} else {
			Core.sendDebug("Couldn't open a Stream to Bootstrap/js/bootstrap.js");
		}
	}

	private void loadbootstrap_min_js() {
		InputStream input = this.core
				.getResource("Bootstrap/js/bootstrap.min.js");
		LinkedList<Byte> list = new LinkedList<Byte>();
		if (input != null) {
			try {
				byte[] b = new byte[1];
				while (input.read(b) != -1) {
					list.add(b[0]);
				}
				Byte[] bytes = list.toArray(new Byte[0]);
				this.bootstrap_min_js = new byte[bytes.length];
				for (int i = 0; i < bytes.length; i++) {
					this.bootstrap_min_js[i] = bytes[i];
				}
			} catch (Exception e) {
				Core.sendDebug("Something went wrong while reading Bootstrap/js/bootstrap.min.js");
				e.getMessage();
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {

				}
			}
		} else {
			Core.sendDebug("Couldn't open a Stream to Bootstrap/js/bootstrap.min.js");
		}
	}

	public synchronized byte[] getResource(String resource, String langtag) {
		switch (resource) {
		case "bootstrap.css":
			return this.bootstrap_css;
		}
		return null;
	}

}
