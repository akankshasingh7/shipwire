/**
 * 
 */


import java.util.List;

public class Order {
	String header;
	List<Item> lines;
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	public void setLines(List<Item> items) {
		this.lines = items;
	}
	
	public List<Item> getLines() {
		return lines;
	}
}
