package com.hotmail.joatin37.jcore.website;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.Core;

public class WebPageManager implements Runnable {

	final JavaPlugin plugin;
	private final Core core;
	private final List<ConnectionHandler> connections;

	public WebPageManager(JavaPlugin plugin, Core core) {
		this.core = core;
		this.plugin = plugin;
		this.connections = new Vector<ConnectionHandler>();
	}

	// Waring Run ASYNC
	public void handleConnection(Socket socket) {
		this.connections.add(new ConnectionHandler(socket, this));
	}

	public void HandleLogin(BufferedReader input, BufferedOutputStream output) {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
