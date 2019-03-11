package com.parkinglot.service;

import com.parkinglot.exception.SlotsUnavailableException;
import com.parkinglot.exception.VehicleNotFoundException;
import com.parkinglot.model.EntryPoint;
import com.parkinglot.model.Slot;
import com.parkinglot.model.Vehicle;

public interface Parkable {
	public int park(Vehicle vehicle, EntryPoint ep) throws SlotsUnavailableException;
	public Vehicle unpark(Slot slot) throws VehicleNotFoundException;
	public Vehicle peek(Slot slot);
}
