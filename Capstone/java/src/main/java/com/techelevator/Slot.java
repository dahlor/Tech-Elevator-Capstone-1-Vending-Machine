package com.techelevator;

public class Slot {

	private Item newItem;
	private String slotNumber;
	private int itemQuant;
	
	public Slot() {
		newItem = null;
		slotNumber = "";
		itemQuant = 0;
	}
	
	public Slot(Item newItem, int itemQuant) {
		this.newItem = newItem;
		this.itemQuant = itemQuant;
	}

	public Item getNewItem() {
		return newItem;
	}

	public void setNewItem(Item newItem) {
		this.newItem = newItem;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public int getItemQuant() {
		return itemQuant;
	}

	public void setItemQuant(int itemQuant) {
		this.itemQuant = itemQuant;
	}
	
	public String getItemName() {
		return newItem.getItemName();
	}
	
	
}
