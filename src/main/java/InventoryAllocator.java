/**
 * 
 */


/**
 * @author Akanksha
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

public class InventoryAllocator {

	Inventory invObj;
	
	private List<String> orderNumList = new LinkedList<String>();
	
	private List<List<Integer>> orderedList = new LinkedList<List<Integer>>();
	
	private List<List<Integer>> allocatedList = new LinkedList<List<Integer>>();
	
	private List<List<Integer>> backorderedList = new LinkedList<List<Integer>>();
	
	public InventoryAllocator()
	{
		invObj = new Inventory();
	}
	
	public Inventory getInvObj() {
		return invObj;
	}

	public void setInvObj(Inventory invObj) {
		this.invObj = invObj;
	}
/*
 * method to initialize the inventory
 * 
 */
	public void initializeInventory(){
		System.out.println("Initializing inventory... ");
		
		//create list of items in inventory
		Map<String,Integer> itemList = new TreeMap<String , Integer>();
		itemList.put("A",150);
		itemList.put("B",150);
		itemList.put("C",100);
		itemList.put("D",100);
		itemList.put("E",200);
		
		//add list of items in inventory
		invObj.setInventory(itemList);
		//Initialize lists
		
		System.out.println("Inventory initialization complete... ");
		
	}
	
	/*
	 * method to check if inventory is initialized or not
	 * returns boolean
	 */
	public boolean inventoryNotInitialized()
	{
		if(invObj.getInventory().isEmpty())
			return true;
		return false;
	}
	
	/*
	 * method to check if inventory is finished
	 * returns boolean
	 */
	public boolean inventoryFinished()
	{
		Map<String , Integer> map = invObj.getInventory();
		boolean check=true;
		for(Entry<String,Integer> entry : map.entrySet())
		{
			String key = entry.getKey();
			if(map.get(key)!=0)
				{
					
					check = false;
				}
		}
		return check;
	}
	
	
	/*
	 * method to update inventory status
	 * 1. update inventory
	 * 2. allocate orders when items in stock
	 * 3. backorder items when items not in stock
	 * 
	 * returns status
	 */
	public int updateInventory(Order orderObj){
		
		//check if inventory is initialized
		if(inventoryNotInitialized())
			return -1;
		
		//check if inventory has no items
		if(inventoryFinished())
		{
			System.out.print("Inventory finished.. generate reports----");
			generateReports();
			return 0;
		}
		
		//1.check for order item in the inventory
		Map<String , Integer> invMap = invObj.getInventory();
		Set<String> keys = invMap.keySet();
		List<Item> linesList = orderObj.getLines();
		orderNumList.add(orderObj.getHeader());
		Collections.sort(linesList);
		
		List<Integer> orderList = new LinkedList<Integer>();
		List<Integer> allocateList = new LinkedList<Integer>();
		List<Integer> backorderList = new LinkedList<Integer>();
		
		int quant,availableQuant;
		String prod;
		int i=0;
		int len= linesList.size();
		
		
		//2.check if quantity can be allocated
		//iterate thru the current inventory
		for(Entry<String, Integer> entry : invMap.entrySet())
		{
			if(i<len)
			{
				Item item = linesList.get(i);
				quant = item.getQuantity();
				prod = item.getProduct();
			
				//check if prod is the key
				if(entry.getKey().equals(prod))
				{
					
					orderList.add(quant); 
					availableQuant = invMap.get(entry.getKey());
					if(availableQuant>=quant)
					{
						//update quantity
						availableQuant = availableQuant-quant;		
						invMap.put(prod, availableQuant);	
						allocateList.add(quant);		//add 'quantity' to allocated map after allocated
						backorderList.add(0);
					}else
					{
						allocateList.add(0);		//add '0' to allocated map if not allocated
						backorderList.add(quant);	//add to back ordered map
						
					}
					i++;
				}
				else
				{
					//add '0' to all lists if key not same
					orderList.add(0);
					allocateList.add(0);		
					backorderList.add(0);
				}
				
			}else
			{
				orderList.add(0);
				allocateList.add(0);
				backorderList.add(0);
			}
		}
		
		//3.if order completed, update inventory, update orderedlist, allocatedlist , backorderedlist
		invObj.setInventory(invMap);
		orderedList.add(orderList);
		allocatedList.add(allocateList);
		backorderedList.add(backorderList);
		
		return 0;
	}
	
	/*
	 * method to generate reports when inventory is finished
	 * 1. generate order header list
	 * 2. generate order list
	 * 3. generate allocated order list
	 * 4. generate backordered list
	 * 
	 */
	public void generateReports()
	{
		Map<String , Integer> map = invObj.getInventory();
		int listSize = orderedList.size();
		Map<String , Integer> tempMap ;
		List<Integer> l1;
		try{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(" .. Enter location to generate reports.. ");
			String filePath = bf.readLine();
			
			File file = new File(filePath+"/reports.txt");
	
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//start writing to file
			for(int i=0; i<listSize ;i++)
			{
				
				bw.write(orderNumList.get(i)+" :: ");
				
				l1 = orderedList.get(i);
				for(int intObj :l1)
					{	
						bw.write(intObj+",");
					}
				
				bw.write("::");
				l1 = allocatedList.get(i);
				for(int intObj :l1)
					{		
						bw.write(intObj+",");
					}
				
				bw.write("::");
				l1 = backorderedList.get(i);
				for(int intObj :l1)
					{
						bw.write(intObj+",");
					}
				bw.write("\n");
			}
			System.out.println();
			//end writing to file
			bf.close();
			bw.close();
			System.out.println(" ****  Report generated at location "+filePath+"/reports.txt");
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
		
	}
}
