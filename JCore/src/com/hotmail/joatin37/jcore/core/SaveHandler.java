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

package com.hotmail.joatin37.jcore.core;

/**
 * This class is used to peform saving of you plugin, all your saving should be
 * put here. Subclass this class to fit your needs. JCore does then take care of
 * calling the save method. Saving is most often performed together with the
 * world saving. But it might occur less or more often.
 * <p>
 * The most common way of saving stuff is either through a loop if you want to
 * save lots of small pieces, but you could also line it all up. You could also
 * put parts of the saving in other classes, and just call them from this class.
 * However most server owners and players like to know how the save progress is
 * going. Therefore this class provides some methods for monitoring the
 * progress.
 * <p>
 * The first thing that you should do in the save() method is to set the total
 * progress. If you don't do that each time the save() method is called, JCore
 * wont be able to monitor your progress. The total progress is set as an int.
 * Try to be as detailed as possible. It doesn't matter if your total progress
 * would exceed 100000.
 * <p>
 * If you think that your plugin is so small that the saving wont take more than
 * a few millisecs at most, one example would be if you plugin only saves its
 * config and thats all, you can skip to set the total progress. JCore will
 * count your save progress as 0% until the save thread has left the save()
 * method, then it will jump to 100%, and continue with the next plugin.
 * 
 * @author Joatin
 * 
 * @since JCore 1.0
 */
public abstract class SaveHandler {

	/**
	 * The total progress set
	 */
	private int totalprogress = 0;

	/**
	 * The current progress
	 */
	private int progress = 0;

	/**
	 * Tells if the total progress has been set this round of execution
	 */
	private boolean istotalset;

	/**
	 * Here goes all your saving. The first thing you should do is to calculate
	 * your total save progress. If all you are about to do is to save a array
	 * of bytes you can set the progress like this
	 * <code>this.setTotalProgress(MyByteArray.length)</code>. The for each byte
	 * you save you then call the addProgress() method.
	 * <p>
	 * If you are saving multiple objects it might be better if you try to make
	 * the total progress more even over the objects. For example, lets say that
	 * you want to save 99 bytes, but you do also want to save one
	 * FileConfiguration. In that case if you add 99 progresses for the bytes
	 * and 1 for the config, the result will be very misleading. The config
	 * might be ten times bigger than 99 bytes, but still the config is counted
	 * as 1%. So the last percent might take longer than the rest 99 percentages
	 * together. In this case it might be better to either count the config as
	 * maybe 100 progress, or to make the bytes to one single progress. The
	 * problem with that would be if the byte array could grow much larger than
	 * the config. If you are making a block protection plugin, the best way to
	 * set the progress would be to have one progress per block since it might
	 * be possible that you are going to store up to 10 000 blocks. A way to
	 * make the config e´more even to the rest could be to calculate its size.
	 * 
	 * 
	 * @param isShutDown
	 *            Tells if the method is called right before the server shuts
	 *            down.
	 * @since JCore 1.0
	 * @see #setTotalProgress(int)
	 */
	public abstract void save(boolean isShutDown);

	public final double percentSavingDone() {
		if (this.istotalset) {
			return 100d / this.totalprogress * this.progress;
		} else {
			return 0d;
		}
	}

	protected final void setTotalProgress(int totalprogress) {
		this.totalprogress = totalprogress;
		this.istotalset = true;
	}

	protected final void addProgress() {
		this.progress++;
	}

	protected final void setProgress(int progress) {
		this.progress = progress;
	}

	protected final void clearProgress() {
		this.progress = 0;
	}

	public final void doSave(boolean isShutDown) {
		this.clearProgress();
		this.istotalset = false;
		try {
			this.save(isShutDown);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
