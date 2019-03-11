package com.parkinglot.model;

/**
 * Represents an identification for slot
 * 
 * Currently implemented for 1d parking slot
 * 
 */
public class Slot implements Comparable<Slot>{
	private int slotNumber;

	public Slot(int slotNumber) {
		this.slotNumber = slotNumber;
	}
	
	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + slotNumber;
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
		Slot other = (Slot) obj;
		if (slotNumber != other.slotNumber)
			return false;
		return true;
	}

	@Override
	public int compareTo(Slot obj) {
		if (obj != null) {
			if (this.slotNumber < obj.slotNumber)
				return -1;
			else
				return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return this.slotNumber + "";
	}
}
