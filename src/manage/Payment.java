package manage;

import java.util.*;
import wineshop.*;

public class Payment {
	
    public static boolean payOneProduct(CartProduct cartProduct, String user) {
    	
		Product product = cartProduct.getProduct();	
		int buyQuantity = cartProduct.getQuantity();
    	
    	if (product.getStock() < buyQuantity) {
			System.out.println("재고가 부족합니다." + product.getName());
			return false;	
		} 
			
    	product.setStock(product.getStock() - buyQuantity);
    	HistoryManage.addHistory(user, product.getName(), product.getPrice(), buyQuantity);
    	return true;
    }
}
