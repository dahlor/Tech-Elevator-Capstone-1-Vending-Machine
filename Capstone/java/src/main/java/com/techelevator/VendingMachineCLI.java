package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*
*  It is the main process for the Vending Machine
*
*  THIS is where most, if not all, of your Vending Machine interactions should be coded
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code vending machine related code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;         // Gain access to Menu class provided for the Capstone

public class VendingMachineCLI {
	
	// Instantiate the Map of Everything.
	Map<String,Slot> itemMap = new HashMap<String,Slot>();
	
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT  = "Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT,
													    MAIN_MENU_OPTION_SALES_REPORT
													    };
	
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
													    PURCHASE_MENU_OPTION_SELECT_PRODUCT,
													    PURCHASE_MENU_OPTION_FINISH_TRANSACTION
													    };
	
	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	
	
	public VendingMachineCLI(Menu menu) {  // Constructor - user will pass a menu for this class to use
		this.vendingMenu = menu;           // Make the Menu the user object passed, our Menu		
		}
	
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	*
	*  It is invoked from the VendingMachineApp program
	*
	*  THIS is where most, if not all, of your Vending Machine objects and interactions 
	*  should be coded
	*
	*  Methods should be defined following run() method and invoked from it
	 * @throws FileNotFoundException 
	*
	***************************************************************************************************************************/
	
	public void run() throws FileNotFoundException {
		
		// Sneaky sneaky, Frank.
		
		// Code to display items in Vending Machine
		File checkLogFile = new File("./Log.txt");
		if (checkLogFile.exists()) {
		checkLogFile.delete();
		}
		
		
		//Getting all that data in the system.
		File aFile = new File("./vendingmachine.csv");
		Scanner inputDataFile = new Scanner(aFile);
		
		// Define variables to hold data from file

		while(inputDataFile.hasNext()) {	
		String aLine = inputDataFile.nextLine();
			
		// Slice and dice into String array.
		String[] valuesInLine = aLine.split("\\|");
			
		// Turning String array into manipulatable variables.
		String slotLocation = valuesInLine[0];
		String itemName = valuesInLine[1];
		double itemPrice = Double.parseDouble(valuesInLine[2]);
		String itemType = valuesInLine[3];
			
		// Creating a new item with all of our parsed information.
		Item newItem = new Item(itemName, itemPrice, itemType);
		Slot newSlot = new Slot(newItem, 5);
			
		// Put all of this into a sexy HashMap
		itemMap.put(slotLocation, newSlot);
		}
		
		realRun();
	}

	public void realRun() {

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseItems();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
					
				case MAIN_MENU_OPTION_SALES_REPORT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					break;
					
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}	
		}
		return;                               // End method and return to caller
	}
	
	public void runPurchaseMenu(){

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			System.out.println("\nCurrent Money Provided: $" + "2.00"); // <----- Change to get baLANCE>
			
			String choice = (String)vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					purchaseItems();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
					
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}	
		}
		return;                               // End method and return to caller
	}
/********************************************************************************************************
 * Methods used to perform processing
 * @throws FileNotFoundException 
 ********************************************************************************************************/
	public void displayItems() {      // static attribute used as method is not associated with specific object instance
		System.out.println(Arrays.asList(itemMap));
		}
	
	public void purchaseItems() {	 // static attribute used as method is not associated with specific object instance
		// Code to purchase items from Vending Machine
		runPurchaseMenu();
	}
	
	public void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		// Any processing that needs to be done before method ends
	}
	
	public void salesReport() {
		// Process and print out a sales report to SalesReport.txt
	}
	
	
	
	
	
	
	
	
	
	
// Methods we added ourselves that the user will never see.

	// Loading the Vending Machine stock file & converting to Array/HashMap.

	
	
}

