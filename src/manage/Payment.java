package manage;

import java.util.*;
import wineshop.*;

public class Payment {
	
    public static boolean payOneProduct(int index) {
    	
    	List<CartProduct> cartList = CartManage.getCartList();
    	
    	CartProduct cartProduct = cartList.get(index);
		Product product = cartProduct.getProduct();	
		int buyQuantity = cartProduct.getQuantity();
    	
    	if (product.getStock() < buyQuantity) {
			System.out.println("재고가 부족합니다.");
			return false;	
		} 
			
    	product.setStock(product.getStock() - buyQuantity);
    	addHistoryProduct(product.getName(), product.getPrice(), buyQuantity);
    	return true;
    }
    
    public static void addHistoryProduct(String name, int price, int quantity) {
    	
    	boolean found = false;
    	
    	for (HistoryProduct hp : History.historyList) {
			if (hp.getName().equals(name)) {
				hp.setQuantity(hp.getQuantity() + quantity);
				found = true;
				break;
			}
		}
    	
    	if (!found) {
    		HistoryProduct historyProduct = new HistoryProduct(name, price, quantity);
    		History.historyList.add(historyProduct);
    	}
    }
}
