/**
 * 
 */


/**
 * @author Akanksha
 *
 */
public class Item implements Comparable<Item>{

	private String Product;
	private int Quantity;
	
	public Item()
	{}
	
	public Item(String name,int value)
	{
		Product = name;
		Quantity = value;
	}
	public String getProduct() {
		return Product;
	}
	public void setProduct(String product) {
		this.Product = product;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		this.Quantity = quantity;
	}

	public int compareTo(Item o) {
		return this.getProduct().compareTo(o.getProduct());
			
	}
	
	

}
