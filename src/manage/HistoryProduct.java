package manage;

public class HistoryProduct {
	private String user;
	private String name;
	private int price;
    private int quantity;

    public HistoryProduct(String user, String name, int price, int quantity) {
    	this.user = user;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public String getUser() {
		return user;
	}
    public String getName() {
		return name;
	}
    
    public int getPrice() {
    	return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getProductTotalPrice() {
        return this.price * this.quantity;
    }
}
