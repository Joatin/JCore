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

package com.hotmail.joatin37.jcore.economy;

/**
 * A exception thrown when there is a lack of money in a transaction.
 * 
 * @author Joatin
 * 
 * @since 1.0.0
 * 
 */
public class InsufficientMoneyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final double missing;

	private final String owner;

	private final IEconomy econ;

	/**
	 * Constructs a new InsufficientMoneyException.
	 * 
	 * @param amountMissing
	 *            The missing money
	 * @param owner
	 *            The player or bank that was involved
	 * @param econ
	 *            The plugin that throwed this
	 * @since 1.0.0
	 */
	public InsufficientMoneyException(double amountMissing, String owner,
			IEconomy econ) {
		super();
		this.missing = amountMissing;
		this.owner = owner;
		this.econ = econ;
	}

	/**
	 * Returns the money that was missing in the transaction.
	 * 
	 * @return The missing money
	 * @since 1.0.0
	 */
	public double getMissingMoney() {
		return this.missing;
	}

	@Override
	public String getMessage() {
		return this.owner + " is missing " + this.econ.format(this.missing);

	}

}
