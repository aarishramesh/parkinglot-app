package com.parkinglot.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.parkinglot.exception.SlotsUnavailableException;
import com.parkinglot.exception.VehicleNotFoundException;
import com.parkinglot.model.Car;
import com.parkinglot.model.CarParkingLot;
import com.parkinglot.model.EntryPoint;
import com.parkinglot.model.Slot;
import com.parkinglot.util.ParkinglotUtil;

public class CarParkingLotServiceImpl {

	private CarParkingLot parkingLot;
	private Map<String, Integer> regNoVsSlotNo;
	private Map<String, Set<Car>> colourVsCar;
	private Map<Integer, Set<String>> slotVsCarRegNos;

	public String createParkingLot(long slots, long entryPoints) {
		this.parkingLot = new CarParkingLot(slots, entryPoints);
		this.regNoVsSlotNo = new HashMap<String, Integer>();
		this.colourVsCar = new HashMap<String, Set<Car>>();
		this.slotVsCarRegNos = new HashMap<Integer, Set<String>>();
		StringBuilder sb = new StringBuilder();
		sb.append("Created a parking lot with ");
		sb.append(slots);
		sb.append(" slots and ");
		sb.append(entryPoints);
		sb.append(" entry points");
		return sb.toString();
	}

	public String park(String carRegistrationNo, String colour, int entryPoint) {
		try {
			if (carRegistrationNo != null && colour != null && entryPoint > 0) {
				if (regNoVsSlotNo.keySet().contains(carRegistrationNo)) {
					return ParkinglotUtil.duplicateRegNoMsg;
				}
				Car car = new Car(carRegistrationNo, colour);
				EntryPoint ep = new EntryPoint(entryPoint);
				synchronized(this) {
					int slotNo = parkingLot.park(car, ep);
					car.setSlotNo(slotNo);
					regNoVsSlotNo.put(carRegistrationNo, slotNo);
					Set<Car> cars = colourVsCar.get(colour);
					if (cars == null) {
						colourVsCar.put(colour, new HashSet<Car>());
					}
					colourVsCar.get(colour).add(car);
					Set<String> registrationNumbers = slotVsCarRegNos.get(slotNo);
					if (registrationNumbers == null)
						slotVsCarRegNos.put(slotNo, new HashSet<String>());
					slotVsCarRegNos.get(slotNo).add(carRegistrationNo);
					return ParkinglotUtil.parkSuccessMsg + slotNo;
				}
			} else {
				return "";
			}
			
		} catch (SlotsUnavailableException ex) {
			return ex.getMessage();
		}
	}

	public String unpark(int slotNumber) {
		long startTime = System.currentTimeMillis();
		try {
			if (slotNumber > 0 && slotNumber <= parkingLot.getTotalSlots()) {
				Slot slot = new Slot(slotNumber);
				synchronized(this) {
					Car car = (Car) this.parkingLot.peek(slot);
					this.parkingLot.unpark(slot);
					this.regNoVsSlotNo.remove(car.getRegistrationNumber());
					this.colourVsCar.get(car.getColour()).remove(car);
					this.slotVsCarRegNos.get(slotNumber).remove(car.getRegistrationNumber());
				}
				System.out.println("Unpark took" + (System.currentTimeMillis() - startTime));
				return ParkinglotUtil.unparkSuccessMsg.replaceFirst("#", slotNumber + "");
			} else
				return ParkinglotUtil.invalidSlotMsg;
		} catch (VehicleNotFoundException ex) {
			return ex.getMessage();
		}
	}

	public String status() {
		return parkingLot.status().toString();
	}

	public String fetchParkedCarRegistrationNumbersForColour(String colour) {
		if (colourVsCar.get(colour) != null) {
			return colourVsCar.get(colour).stream().map(Car::getRegistrationNumber).collect(Collectors.toList()).toString();
		}
		return "Not found";
	}

	public String fetchParkedCarSlotNumbersForColour(String colour) {
		if (colourVsCar.get(colour) != null) {
			return colourVsCar.get(colour).stream().map(Car::getSlotNo).collect(Collectors.toList()).toString();
		}
		return "Not found";
	}
	
	public String fetchSlotNumberForCar(String registrationNumber) {
		if (regNoVsSlotNo.get(registrationNumber) != null)
			return regNoVsSlotNo.get(registrationNumber).toString();
		return "Not Found";
	}
}
