

import java.io.*;
import java.security.AllPermission;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;




public class MainApplication 
{
	
	
    public static void main( String[] args )
    {
    	System.out.println("\n *** Main Application Class of InventoryManagement system  ***\n");
    	OrderDataSource odc = new OrderDataSource();
    	odc.createOrders();
    }
    
    

}
