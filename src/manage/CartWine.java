package manage;

import wineshop.Wine;

public class CartWine {
    private Wine wine;
    private int quantity;

    public CartWine(Wine wine, int quantity) {
        this.wine = wine;
        this.quantity = quantity;
    }

    public Wine getWine() {
        return wine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getWineTotalPrice() {
        return wine.getPrice() * quantity;
    }
}
