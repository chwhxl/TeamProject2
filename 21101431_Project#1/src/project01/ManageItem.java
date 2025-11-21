package project01;

public class ManageItem {

    Item[] allItems = new Item[100];

    ManageItem() {
        allItems[0] = new BlackCoffee("Double Espresso", 4500, 10);
        allItems[1] = new BlackCoffee("Americano", 4800);
        allItems[2] = new BlackCoffee("Cold Brew", 5800);

        allItems[3] = new WhiteCoffee("Flat White", 5300, 10);
        allItems[4] = new WhiteCoffee("Caffe Latte", 5800);
        allItems[5] = new WhiteCoffee("Vanilla Latte", 4500);

        allItems[6] = new Beverage("Basil Tomato Soda", 7300, 10);
        allItems[7] = new Beverage("Mango Fashion Fruits Soda", 6800);
        allItems[8] = new Beverage("Melon Soda Float", 6800);

        allItems[9] = new Cookie("Chocolate Chip Cookie", 3000, 5);
        allItems[10] = new Cookie("Matcha Cookie", 3000);
        allItems[11] = new Cookie("Red Velvet Cookie", 6500);

        allItems[12] = new Cake("Cheese Cake", 6400, 5);
        allItems[13] = new Cake("Victoria Cake", 6800);
        allItems[14] = new Cake("Strawberry Shortcake", 7000);
    }

    Item[] searchItem(String name) {
        int count = 0;
        for (Item item : allItems) {
            if (item != null && item.getItemName().contains(name)) {
                count++;
            }
        }

        Item[] result = new Item[count];

        int index = 0;
        for (Item item : allItems) {
            if (item != null && item.getItemName().contains(name)) {
                result[index] = item;
                index++;
            }
        }

        return result;
    }

    Item[] searchItemByCategory(String category) {

        int count = 0;
        for (Item item : allItems) {
            if (item != null && item.getCategory().equals(category)) {
                count++;
            }
        }

        Item[] result = new Item[count];

        int index = 0;
        for (Item item : allItems) {
            if (item != null && item.getCategory().equals(category)) {
                result[index] = item;
                index++;
            }
        }

        return result;
    }

    Item[] searchItem(int maxPrice) {

        int count = 0;
        for (Item item : allItems) {
            if (item != null && item.getPrice() <= maxPrice) {
                count++;
            }
        }

        Item[] result = new Item[count];

        int index = 0;
        for (Item item : allItems) {
            if (item != null && item.getPrice() <= maxPrice) {
                result[index] = item;
                index++;
            }
        }

        return result;
    }

    void viewAllItems() {
        System.out.println("모든 상품 목록");
        for (Item item : allItems) {
            if (item != null) {
                item.getInfo();
            }
        }
    }

    Item getItemByName(String itemName) {
        for (Item item : allItems) {
            if (item != null && item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
}