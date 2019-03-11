package com.parkinglot.model;

import java.util.Date;

public abstract class Vehicle {
	private String registrationNumber;
	private String colour;
	private String ownerName;
	private String ownerPhoneNumber;
	private Date parkedTime;
	private long slotNo;

	public Vehicle(String regNo, String colour) {
		this.registrationNumber = regNo;
		this.colour = colour;
	}
	
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}
	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}
	public Date getParkedTime() {
		return parkedTime;
	}
	public void setParkedTime(Date parkedTime) {
		this.parkedTime = parkedTime;
	}
	
	public long getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(long slotNo) {
		this.slotNo = slotNo;
	}

	@Override
	public String toString() {
		return this.registrationNumber + " : " + this.colour + " : " + (slotNo != 0 ? slotNo : "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (colour == null) {
			if (other.colour != null)
				return false;
		} else if (!colour.equals(other.colour))
			return false;
		if (registrationNumber == null) {
			if (other.registrationNumber != null)
				return false;
		} else if (!registrationNumber.equals(other.registrationNumber))
			return false;
		return true;
	}
	
	
}
