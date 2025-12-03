package manage;

public abstract class Product {
	
    protected String name;
    protected int price;
    protected String type;
    protected double alcohol;
    protected int stock;
    protected String imgPath;

    public Product(String name, int price, String type, double alcohol) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.alcohol = alcohol;
    }
    
    public abstract String getCategory();

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    
    public String getType() {
		return type;
	}

    public double getAlcohol() {
        return alcohol;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImgPath() {
        return imgPath;
    }
}
