package com.parkinglot.exception;

public class VehicleNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VehicleNotFoundException() {
		super("Car not found in the parking");
	}
}
