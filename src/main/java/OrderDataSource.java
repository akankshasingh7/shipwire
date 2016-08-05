import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */

/**
 * @author Akanksha
 *
 */
public class OrderDataSource {

	public int createOrders()
	{
		InventoryAllocator allocator = new InventoryAllocator();
    	OrderManager orderManager = new OrderManager();
    	allocator.initializeInventory(); // initializes the inventory
    	String filename;
        try{
	        
	        ObjectMapper objMap = new ObjectMapper();
	        Order orderObj = null;
	        List<Order> list = new LinkedList<Order>();
	        System.out.println();
	        
	        BufferedReader readerObj=null;
	        
	        readerObj = new BufferedReader(new InputStreamReader(System.in));
	        System.out.println(" ..Enter the directory containing order files..");
	        System.out.println();
	        String target_dir = readerObj.readLine();
	        
	        File dir = new File(target_dir);
	        File[] files = dir.listFiles();

	        for (File f : files) {
	            if(f.isFile()) {
	              
	                try {
	                	readerObj = new BufferedReader(
	                                    new FileReader(f));
	                    String line;

	                    while ((line = readerObj.readLine()) != null) {
	                    	System.out.println(line);
	    		        	orderObj = objMap.readValue(line,Order.class);
	    		        	list.add(orderObj);
	                    }
	                }catch(FileNotFoundException ex)
	                {
	                	ex.printStackTrace();
	                }
	            }
	        }//end of files read
	        for(Order obj : list )
	        {	
	 	        if(orderManager.checkIfValidOrder(obj))
	 	        {
	 	        	allocator.updateInventory(obj);
	 	        }
	        }
	        
        }catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        return 0;
	}
}
