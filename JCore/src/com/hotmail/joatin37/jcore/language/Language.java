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

package com.hotmail.joatin37.jcore.language;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.core.Core;

public class Language extends HashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isLatin;

	public Language(Core core, JavaPlugin plugin, String language)
			throws Exception {
		if (plugin == null) {
			throw new NullPointerException("JavaPlugin was null");
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(
				plugin.getResource(language)));
		String s;
		int i = 1;
		String[] latin = input.readLine().split(":", 2);
		if (!latin[0].equalsIgnoreCase("latin")) {
			core.getLogger()
					.warning(
							"Line latin was misspelled or missing att the first line of the file, the line \"latin: true\" or \"latin: false\" must be at the very first line of the file.\n"
									+ "Error was found in the plugin: "
									+ plugin.getName());
			throw new Exception();
		} else {
			this.isLatin = Boolean.parseBoolean(latin[1].toLowerCase().trim());
		}
		while ((s = input.readLine()) != null) {
			i++;
			String[] split = s.split(":", 2);
			if (split.length == 2) {
				if (split[0].trim().length() != 2) {
					core.getLogger()
							.warning(
									"Malformed tag at line: "
											+ i
											+ ", in plugin: "
											+ plugin.getName()
											+ "\n A tag may only be exactly two uppercase caracters ranging from AA-ZZ");
					throw new Exception();
				} else {
					this.put(split[0].trim().toUpperCase(), split[1]);
				}
			}
		}
		try {
			input.close();
		} catch (Exception e) {
		}
	}
}
