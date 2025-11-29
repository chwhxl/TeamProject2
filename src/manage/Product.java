package manage;

public abstract class Product {
	
    protected String name;
    protected int price;
    protected double alcohol;
    protected int stock;
    protected String imgPath;

    public Product(String name, int price, double alcohol) {
        this.name = name;
        this.price = price;
        this.alcohol = alcohol;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
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

