package com.parkinglot;

import java.util.Scanner;

import com.parkinglot.service.CarParkingLotServiceImpl;

/**
 * Main Application for Car parking functionality
 * 
 */
public class CarParkingLotApplication {
	private CarParkingLotServiceImpl service = new CarParkingLotServiceImpl();

	public static void main(String[] args) {
		CarParkingLotApplication app = new CarParkingLotApplication();
		Scanner sc = new Scanner(System.in);
		String input = "";
		while (!(input = sc.nextLine()).equals("exit")) {
			try {
				String[] inputTokens = input.split(" ");
				if (inputTokens != null) {
					String command = inputTokens[0];
					if (command.equals(Command.create_parking_lot.name())) {
						long noOfSlots = Integer.parseInt(inputTokens[1]);
						long entryPoints = Integer.parseInt(inputTokens[2]);
						System.out.println(app.service.createParkingLot(noOfSlots, entryPoints));
					} else if (command.equals(Command.park.name())) {
						String regNo = inputTokens[1];
						String colour = inputTokens[2];
						String entryPointId = inputTokens[3];
						System.out.println(app.service.park(regNo, colour, Integer.parseInt(entryPointId)));
					} else if (command.equals(Command.leave.name())) {
						int slotNo = Integer.parseInt(inputTokens[1]);
						System.out.println(app.service.unpark(slotNo));;
					} else if (command.equals(Command.Status.name())) {
						System.out.println(app.service.status());
					} else if (command.equals(Command.slot_numbers_for_cars_with_colour.name())) {
						String colour = inputTokens[1];
						System.out.println(app.service.fetchParkedCarSlotNumbersForColour(colour));
					} else if (command.equals(Command.registration_numbers_for_cars_with_colour.name())){
						String colour = inputTokens[1];
						System.out.println(app.service.fetchParkedCarRegistrationNumbersForColour(colour));
					} else if (command.equals(Command.slot_number_for_registration_number.name())) { 
						String regNo = inputTokens[1];
						System.out.println(app.service.fetchSlotNumberForCar(regNo));
					} else {
						System.out.println("invalid command");
					}
				}
			} catch (Exception ex) {
				System.out.println("Invalid input. Please enter a valid input");
			}
		}
		sc.close();
	}


	public static enum Command {
		create_parking_lot, park, leave, Status, slot_numbers_for_cars_with_colour, slot_number_for_registration_number
		, registration_numbers_for_cars_with_colour;
		
	}

}
