package com.parkinglot.exception;

public class SlotsUnavailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public SlotsUnavailableException() {
		super("Slots not Available");
	}
}
