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

package com.hotmail.joatin37.jcore.sql;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.scheduler.BukkitTask;

import com.hotmail.joatin37.jcore.core.Core;

public class SQLInstaller extends Thread {

	private final Core core;
	private FileOutputStream file;
	private BufferedInputStream input;
	private long contentlenght;
	private int percentleft;
	private long readbytes;
	private ConsoleUpdater updater;

	public enum DataBase {
		MYSQL(
				524288000l,
				"setup.msi",
				"mysql.dmg",
				"setup.deb",
				"http://dev.mysql.com/get/Downloads/MySQL-5.5/mysql-5.5.29-win32.msi/from/http://cdn.mysql.com/",
				"http://dev.mysql.com/get/Downloads/MySQL-5.5/mysql-5.5.29-winx64.msi/from/http://cdn.mysql.com/",
				"", "", "", "");

		private final long requiredsize;
		private final String filename_windows;
		private final String filename_osx;
		private final String filename_linux;
		private final String url_windows32;
		private final String url_windows64;
		private final String url_osx32;
		private final String url_osx64;
		private final String url_linux32;
		private final String url_linux64;

		/**
		 * Constructs the DataBase enum.
		 * 
		 * @param requiredsize
		 *            The required size for installing the database
		 * @param filename_windows
		 *            File name for the windows download
		 * @param filename_osx
		 *            The file name for the mac os x download
		 * @param filename_linux
		 *            The file name for the linux download
		 * @param url_windows32
		 *            The url for windows 32bit download
		 * @param url_windows64
		 *            The url for windows 64bit download
		 * @param url_osx32
		 *            The url for mac os x 32bit download
		 * @param url_osx64
		 *            The url for mac os x 64bit download
		 * @param url_linux32
		 *            The url for linux 32bit download
		 * @param url_linux64
		 *            The url for linux 64bit download
		 * @since JCore 1.0
		 */
		private DataBase(long requiredsize, String filename_windows,
				String filename_osx, String filename_linux,
				String url_windows32, String url_windows64, String url_osx32,
				String url_osx64, String url_linux32, String url_linux64) {
			this.requiredsize = requiredsize;
			this.filename_windows = filename_windows;
			this.filename_osx = filename_osx;
			this.filename_linux = filename_linux;
			this.url_windows32 = url_windows32;
			this.url_windows64 = url_windows64;
			this.url_osx32 = url_osx32;
			this.url_osx64 = url_osx64;
			this.url_linux32 = url_linux32;
			this.url_linux64 = url_linux64;

		}
	}

	public SQLInstaller(Core core, DataBase database) {
		this.core = core;
		File file1 = new File(core.getDataFolder(), "setup.exe");

		if (file1.getUsableSpace() > database.requiredsize) {
			// TODO throw something
		} else {
			try {
				this.file = new FileOutputStream(file1);
				URL url = new URL(database.url_windows64);
				URLConnection connection = url.openConnection();
				connection.connect();
				this.contentlenght = connection.getContentLengthLong();
				this.input = new BufferedInputStream(
						connection.getInputStream());
				this.setPriority(MIN_PRIORITY);
				this.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		byte[] b = new byte[1];

		try {
			this.updater = new ConsoleUpdater(this.core);
			while (this.input.read(b) != -1) {
				this.file.write(b);
				this.readbytes++;
				this.percentleft = (int) (this.readbytes / (this.contentlenght / 100));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.updater.Close();
		}
	}

	private class ConsoleUpdater implements Runnable {

		private Core core;
		private BukkitTask task;

		private ConsoleUpdater(Core core) {
			this.core = core;
			this.task = core.getServer().getScheduler()
					.runTaskTimer(core, this, 5, 100);
		}

		public void Close() {
			this.task.cancel();
		}

		@Override
		public void run() {
			this.core.getLogger().info(
					"Percent done: " + SQLInstaller.this.percentleft + "%");

		}

	}
}
