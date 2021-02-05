package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
	
	// Here we go...
	
	// Instantiate the Map of Everything.
	TreeMap<String,Slot> itemMap = new TreeMap<String,Slot>();
	
	// Money variables, getter, and setter.
	private double balance;
	private double totalSales;
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	

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
	 * @throws IOException 
	*
	***************************************************************************************************************************/
	
	public void run() throws IOException {
		
		// Delete previous log.
		 File lastLog = new File("./Log.txt");
		 if (lastLog.exists()){
		     lastLog.delete();
		 }  
		
		//Getting all that data in the system.
		File aFile = new File("./vendingmachine.csv");
		@SuppressWarnings("resource")
		Scanner inputDataFile = new Scanner(aFile);
		
		// Define variables to hold data from file.

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
			
		// Put all of this into a sexy TreeMap. (For ordered display. We spared no expense.)
		itemMap.put(slotLocation, newSlot);
		}
		
		// Start with a bank balance of 0.00
		setBalance(0);
		setTotalSales(0);
		
		mainRun();
	}

	public void mainRun() throws IOException {

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
					salesReport();    // Invoke method to perform end of method processing
					break;
					
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}	
		}
		return;                               // End method and return to caller
	}
	
	public void runPurchaseMenu() throws IOException{

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			System.out.println("\nCurrent Money Provided: $"+String.format("%.2f", getBalance())); // <----- Change to get baLANCE>
			
			String choice = (String)vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					feedMoney();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					selectProduct();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
					
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
					finishTransaction();    // Invoke method to perform end of method processing
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
	public void displayItems() {   // static attribute used as method is not associated with specific object instance
			System.out.println("");
		for (Map.Entry<String, Slot> loopy:itemMap.entrySet()) {
			System.out.println(loopy.getValue().getItemName() +"|" + loopy.getValue().getItemQuant());
		}
	}
	
	public void purchaseItems() throws IOException {  // static attribute used as method is not associated with specific object instance
		// Code to purchase items from Vending Machine
		runPurchaseMenu();
	}
	
	public void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		// Any processing that needs to be done before method ends
	}
	
	public void salesReport() throws IOException {
		String salesWriter = new SimpleDateFormat("'Sales Report 'yyyy-MM-dd-HH.mm.ss'.txt'").format(new Date());
		PrintWriter printItemWriter = new PrintWriter(salesWriter);
		System.out.println("\nPrinting sales report...");
	for (Map.Entry<String, Slot> loopy:itemMap.entrySet()) {
		printItemWriter.write((loopy.getValue().getItemName() +"|" + loopy.getValue().getItemQuant())+"\n");
	}
		printItemWriter.write("\n");
		printItemWriter.write("Total Sales: $"+String.format("%.2f", getTotalSales()));
		printItemWriter.close();
	}
	
	/****************
	 * PURCHASE MENU
	 * @throws IOException 
	 ****************/
	
	@SuppressWarnings("resource")
	public void feedMoney() throws IOException {
		System.out.println("\nPlease type how much cash your would like to insert. (Only $1, $2, $5, and $10 bills accepted.)");
        Scanner cashScanner = new Scanner(System.in);
        String cashMoney = cashScanner.nextLine();
        try {
        	int intCashMoney = Integer.parseInt(cashMoney);
            insertMoney(intCashMoney);
        } catch (NumberFormatException ex){  
            System.err.println("I said ones, twos, fives and tens, ya big dumb!");	
        }
       }
	
	public void selectProduct() throws IOException {
		System.out.println("");
		for (Map.Entry<String, Slot> loopy:itemMap.entrySet()) {
			System.out.println(loopy.getKey()+"|"+loopy.getValue().getItemName());
		}
		System.out.println("\nPlease type the number and letter for the item you would like to purchase.");
        @SuppressWarnings("resource")
		Scanner slotScanner = new Scanner(System.in);
        String slotSelection = slotScanner.nextLine().toUpperCase();
		itemPurchase(slotSelection);
	}
	
	public void finishTransaction() throws IOException {
		System.out.println("\nCHANGE DISPENSING...\n");
		System.out.println("Your change is $"+String.format("%.2f", getBalance())+".\n");
		System.out.println("This will be returned to you as " + returnChange()+".");
		
		// Instantiates the log file and flags for appended writing
		FileWriter itemWriter = new FileWriter("./Log.txt", true);
		PrintWriter printItemWriter = new PrintWriter(itemWriter);

		printItemWriter.println(dateTime() + " GIVE CHANGE: $"+String.format("%.2f", getBalance())+" $0.00\n");
		printItemWriter.close();
		
		// Change has been returned, so... 
		setBalance(0.00);
	}
		
	// Date and time formatted as requested.
	public static String dateTime() {
		DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		String dateString2 = dateFormat2.format(new Date()).toString();
		return dateString2;
	}
	
	// When an item is purchased.
	// The method for purchasing an item from the vending machine.
	public void itemPurchase(String itemSlot) throws IOException {

		// Making sure the entered slot exists.
		if (itemMap.containsKey(itemSlot)){
				
			//If you have the money...
			if (itemMap.get(itemSlot).getNewItem().getItemPrice() <= getBalance()){
			
				//If it's in stock...
				if (itemMap.get(itemSlot).getItemQuant() > 0) {
				
					// The inventory part
					itemMap.get(itemSlot).setItemQuant(itemMap.get(itemSlot).getItemQuant() - 1);
							
					
					// Subtract the item price from the balance.
					double balanceMath = getBalance() - itemMap.get(itemSlot).getNewItem().getItemPrice();
					setBalance(balanceMath);
							
					// Add to total sales.
					double calcTotalSales = getTotalSales() + itemMap.get(itemSlot).getNewItem().getItemPrice();
					setTotalSales(calcTotalSales);
					
					//Print the transaction to the console.
					System.out.println("\nYour "+ itemMap.get(itemSlot).getItemName() +" has been dispensed. It cost $"+String.format("%.2f", itemMap.get(itemSlot).getNewItem().getItemPrice())+". Your remaining balance is $"+String.format("%.2f", getBalance()));
				
					//Audit Log the time, item purchased, slot #, price, and remaining balance.
					// Instantiates the log file and flags for appended writing
					FileWriter saleWriter = new FileWriter("./Log.txt", true);
					PrintWriter saleItemWriter = new PrintWriter(saleWriter);
					saleItemWriter.println(dateTime()+" "+itemMap.get(itemSlot).getItemName() +" "+itemSlot +" $"+String.format("%.2f", itemMap.get(itemSlot).getNewItem().getItemPrice())+" $"+String.format("%.2f", getBalance()));
					saleItemWriter.close();
					
						// R E S P O N S E  P L I N K O !
						if (itemMap.get(itemSlot).getNewItem().getItemType().equals("Chip")){
								System.out.println("\nCrunch Crunch, Yum!");
						}
						if (itemMap.get(itemSlot).getNewItem().getItemType().equals("Candy")){
							System.out.println("\nMunch Munch, Yum!");
						}
						if (itemMap.get(itemSlot).getNewItem().getItemType().equals("Drink")){
							System.out.println("\nGlug Glug, Yum!");
						}
						if (itemMap.get(itemSlot).getNewItem().getItemType().equals("Gum")){
						System.out.println("\nChew Chew, Yum!");
						}
						
					} else {
					// ...it's not in stock.
					System.out.println("\nThat item is SOLD OUT.\n");
					}
				} else {
				//...you don't have the money.
				System.out.println("\nNot enough money has been inserted to make this purchase.\n");
				}
			} else {
			// User had a stroke.
			System.out.println("\n"+itemSlot+" is not a valid choice. Please try again.\n");
		}
	}
	
	// When money is inserted.
	public void insertMoney(int moneyFed) throws IOException {
		if ((moneyFed == 1) || (moneyFed == 2) || (moneyFed == 5) || (moneyFed == 10)) {
			
		// Instantiates the log file and flags for appended writing
		FileWriter feedWriter = new FileWriter("./Log.txt", true);
		PrintWriter printFeedWriter = new PrintWriter(feedWriter);
			
		//Add the money to the books.
		double newBalance = (getBalance() + moneyFed);
		setBalance(newBalance);
		
		//Print the time, money inserted, and new balance.
		printFeedWriter.println(dateTime() + " FEED MONEY: $"+moneyFed+".00 $"+String.format("%.2f", getBalance()));  //New line
		printFeedWriter.close();
		}
	System.out.println("Invalid dollar amount entered. Please try again.");
	}
	
	// Change return calculator.
	public String returnChange() {

	      double quarter = 0.25;
	      double nickel = 0.05;
	      double dime = 0.10;
	      
	      double changeDue = ( (double)((int) Math.round((getBalance())*100)) / 100.0 );
	      double modQuarters = ( (double)((int) Math.round((changeDue % quarter)*100)) / 100.0 );
	      double modDimes = ( (double)((int) Math.round((modQuarters % dime)*100)) / 100.0 );
	      double modNickels = ( (double)((int) Math.round((modQuarters % nickel)*100)) / 100.0 );
	      
	      int numQuarters = (int)((changeDue - modQuarters) / (quarter));
	      int numDimes = (int)((modQuarters - modDimes) / (dime));
	      int numNickels = (int)((modDimes - modNickels) / (nickel));
	      
	      String total = (numQuarters + " quarter(s), " + numDimes + " dime(s), and " + numNickels + " nickel(s)");
	      return total;
	}	
}