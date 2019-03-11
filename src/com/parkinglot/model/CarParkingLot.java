package com.parkinglot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.parkinglot.exception.SlotsUnavailableException;
import com.parkinglot.exception.VehicleNotFoundException;
import com.parkinglot.service.Parkable;

/**
 *  POJO which abstracts parking lot with the slot Vs car mapping. 
 *  
 *  It also maintains a priority queue to return nearest slots 
 *
 *  This implementation assumes an one dimensional parking and a single entry point 
 *  
 *  It can be easily extended to 2D/3D parking with multiple entry points
 *  
 *  park and unpark operations in the parking lot is synchronized by default
 */
public class CarParkingLot implements Parkable {
	private Map<Slot, Car> slotVsCar;
	private PriorityBlockingQueue<Slot> slots; 
	private long entryPoints;
	private long totalSlots;
	private long occupiedSlots;
	
	public CarParkingLot(long numSlots, long entryPoints) {
		this.totalSlots = numSlots;
		this.entryPoints = entryPoints;
		slots = new PriorityBlockingQueue<Slot>((int)numSlots);
		slotVsCar = new ConcurrentHashMap<Slot, Car>();
		init();
	}
	
	private void init() {
		for (int i = 1; i <= totalSlots; i++) {
			Slot slot = new Slot(i);
			slots.add(slot);
		}
	}
	
 	@Override
	public int park(Vehicle vehicle, EntryPoint ep) throws SlotsUnavailableException {
		return parkCarInternal((Car)vehicle);
	}
	
	private synchronized int parkCarInternal(Car car) throws SlotsUnavailableException {
		Slot slot = getFreeSlot();
		if (slot != null) {
			slotVsCar.put(slot, car);
			occupiedSlots++;
			return slot.getSlotNumber();
		} else
			throw new SlotsUnavailableException();
	}
	
	@Override
	public Vehicle unpark(Slot slot) throws VehicleNotFoundException {
		return unparkInternal(slot);
	}
	
	private synchronized Car unparkInternal(Slot slot) throws VehicleNotFoundException {
		if (slotVsCar.get(slot) != null) {
			Car car = slotVsCar.get(slot);
			slotVsCar.remove(slot);
			addBackEmptySlot(slot);
			occupiedSlots--;
			return car;
		} else 
			throw new VehicleNotFoundException();
	}
	
	public List<String> status() {
		List<String> parkingStatus = new ArrayList<String>();
		if (slotVsCar != null) {
			for(Entry<Slot, Car> entry : slotVsCar.entrySet()) {
				parkingStatus.add((entry.getKey().toString()) + " : " + ((Car)entry.getValue()).toString());
			}
			return parkingStatus;
		}
		return parkingStatus;
	}
	
	private Slot getFreeSlot() {
		try {
			return slots.poll(20, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void addBackEmptySlot(Slot s) {
		this.slots.offer(s, 20, TimeUnit.MILLISECONDS);
	}

	public PriorityBlockingQueue<Slot> getSlots() {
		return slots;
	}

	public void setSlots(PriorityBlockingQueue<Slot> slots) {
		this.slots = slots;
	}

	public long getTotalSlots() {
		return totalSlots;
	}

	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	public long getOccupiedSlots() {
		return occupiedSlots;
	}

	public void setOccupiedSlots(int occupiedSlots) {
		this.occupiedSlots = occupiedSlots;
	}

	public Map<Slot, Car> getSlotVsCar() {
		return slotVsCar;
	}

	public void setSlotVsCar(Map<Slot, Car> slotVsCar) {
		this.slotVsCar = slotVsCar;
	}

	public long getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(int entryPoints) {
		this.entryPoints = entryPoints;
	}

	@Override
	public Vehicle peek(Slot slot) {
		return this.slotVsCar.get(slot);
	}
}
