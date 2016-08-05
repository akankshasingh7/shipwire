/**
 * 
 */


import java.util.List;

/**
 * @author Akanksha
 *
 */
public class OrderManager {

	
    public boolean checkIfValidOrder(Order orderObj)
    {
    	String headerVal = orderObj.getHeader();
    	
    	// check if header is empty
    	if(headerVal.length()==0)
    		{
    			System.out.println("!!!Invalid header!!!");
    			return false;
    		}
    	
    	List<Item> orderLines = orderObj.getLines();
    	int linesLen = orderLines.size();
    	
    	//check if lines is empty
    	if(linesLen==0)
    		{
    			System.out.println(" !!!No order in placed!!!");
    			return false;
    		}
    	//check if valid quantity for each line item in order
    	for(int i=0; i<linesLen; ++i)
    	{
    		if(orderLines.get(i).getQuantity()<1 || orderLines.get(i).getQuantity()>5)
    		{			
    			System.out.println("Invalid order!!!! Please check the quantity of item ordered. The quantity should lie >0 or, <=5"); 
    			return false;
    		}
    		
    	}
    	return true;
    }

	
}
