package com.hotmail.joatin37.jcore.util;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.bukkit.scheduler.BukkitTask;

import com.hotmail.joatin37.jcore.core.Core;

public class Lock implements Runnable {

	private static List<Thread> threads = new Vector();

	private static BukkitTask task;

	private static boolean executing;

	private static long current;

	public Lock(Core core) {
		if (task != null) {
			task = core.getServer().getScheduler()
					.runTaskTimer(core, this, 1, 1);
		}
	}

	@Override
	public synchronized void run() {
		executing = true;
		Iterator<Thread> it = threads.iterator();
		while (it.hasNext()) {
			current = it.next().getId();
			it.remove();
			Thread.currentThread().notifyAll();
			try {
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
			}
		}
		executing = false;
		this.notifyAll();
	}

	@Override
	public void finalize() {
		Lock.task = null;
	}

	public synchronized static void lock() {
		if (!Thread.currentThread().getName().equals("Server thread")) {
			while (executing) {
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
				}
			}
			threads.add(Thread.currentThread());
			while (Thread.currentThread().getId() != current) {
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public synchronized static void unlock() {
		Thread.currentThread().notifyAll();
	}
}
