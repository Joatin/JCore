package com.hotmail.joatin37.jcore.util;

import java.util.concurrent.locks.ReentrantLock;

import com.hotmail.joatin37.jcore.core.Core;

public class myLock extends ReentrantLock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public myLock(boolean b) {
		super(b);
	}

	@Override
	public void lock() {
		if (!Thread.currentThread().getName()
				.equalsIgnoreCase(Core.MASTERTHREAD)) {
			super.lock();
		}
	}

	@Override
	public void unlock() {
		if (!Thread.currentThread().getName()
				.equalsIgnoreCase(Core.MASTERTHREAD)) {
			super.unlock();
		}
	}

	public void masterLock() {
		if (Thread.currentThread().getName()
				.equalsIgnoreCase(Core.MASTERTHREAD)) {
			super.lock();
		}
	}

	public void masterUnlock() {
		if (Thread.currentThread().getName()
				.equalsIgnoreCase(Core.MASTERTHREAD)) {
			super.unlock();
		}
	}
}
