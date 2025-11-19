package project01;

class Item {

    private final String itemName;
    private String category;
    private final int price;
    private int currentStock = 0;

    protected Item(String itemName, int price, int initialStock) {
        this.itemName = itemName;
        this.price = price;
        this.currentStock = initialStock;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getCategory() {
        return this.category;
    }

    public int getPrice() {
        return this.price;
    }

    public int getCurrentStock() {
        return this.currentStock;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void reduceStock(int quantity) {
        if (this.currentStock >= quantity) {
            this.currentStock -= quantity;
        }
        else {
            this.currentStock = 0;
        }
    }

    public boolean isOutOfStock() {
        return this.currentStock <= 0;
    }

    public void getInfo() {
        System.out.printf("상품명: %s | 가격: %d | 재고: %d\n",
                this.getItemName(), this.getPrice(), this.getCurrentStock());
    }
}

class BlackCoffee extends Item {
    BlackCoffee(String itemName, int price, int initialStock) {
        super(itemName, price, initialStock);
        this.setCategory("Black Coffee");
    }

    BlackCoffee(String itemName, int price) {
        super(itemName, price, 10);
        this.setCategory("Black Coffee");
    }
}

class WhiteCoffee extends Item {
    WhiteCoffee(String itemName, int price, int initialStock) {
        super(itemName, price, initialStock);
        this.setCategory("White Coffee");
    }

    WhiteCoffee(String itemName, int price) {
        super(itemName, price, 10);
        this.setCategory("White Coffee");
    }
}

class Beverage extends Item {
    Beverage(String itemName, int price, int initialStock) {
        super(itemName, price, initialStock);
        this.setCategory("Beverage");
    }

    Beverage(String itemName, int price) {
        super(itemName, price, 10);
        this.setCategory("Beverage");
    }
}

class Cookie extends Item {
    Cookie(String itemName, int price, int initialStock) {
        super(itemName, price, initialStock);
        this.setCategory("Cookie");
    }

    Cookie(String itemName, int price) {
        super(itemName, price, 5);
        this.setCategory("Cookie");
    }
}

class Cake extends Item {
    Cake(String itemName, int price, int initialStock) {
        super(itemName, price, initialStock);
        this.setCategory("Cake");
    }

    Cake(String itemName, int price) {
        super(itemName, price, 5);
        this.setCategory("Cake");
    }

    @Override
    public void getInfo() {
        System.out.printf("상품명: %s | 가격: %d | 재고: %d (냉장 보관 필요)\n",
                this.getItemName(), this.getPrice(), this.getCurrentStock());
    }
}