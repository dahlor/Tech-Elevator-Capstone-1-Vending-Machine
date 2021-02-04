package com.techelevator;

public class Item {
	
	private String itemName;
	private double itemPrice;
	private String itemType;
	
	public Item( ) {
		itemName = "";
		itemPrice = 0.0;
		itemType = "";
	}
	
	public Item(String itemName, double itemPrice, String itemType)	{
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	
}
