package com.hotmail.joatin37.jcore.chat;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.hotmail.joatin37.jcore.core.Core;

public final class Chat {

	private static Core core;
	private static mChatSuite mchat;

	public Chat(Core core) {
		if (Chat.core != null) {
			Chat.core = core;
		}
	}

	private void connectMChat() {
		Plugin temp = Bukkit.getServer().getPluginManager()
				.getPlugin("mChatSuite");
		if (temp != null) {
			mchat = (mChatSuite) temp;
		}
	}
}
