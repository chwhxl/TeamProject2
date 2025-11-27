package User;

import java.util.ArrayList;

public class cart {

	private static final int ITEM_MAX = 20;
	public static ArrayList<Wine> itemsInCart = new ArrayList<>(ITEM_MAX);
	private static int itemCount = 0;
	private static int totalPrice =0;
	
	
//	public void Cart() {
//		this.itemsInCart = new ArrayList<>(20);
//	} 
	
	static void addtoCart(Wine item) {
		if (itemCount < ITEM_MAX ) {
			itemsInCart.add(itemCount, item);
			itemCount++;
			totalPrice += item.getPrice();
		}else {
			System.out.println("장바구니가 가득 찼습니다😅");
		}
	}
	
	static void showCart() {
		if (itemCount==0) {
			System.out.println("😥 장바구니가 비어있습니다 ");
		}else {
			System.out.println("🛒 장바구니 목록 ");
			for (int i=0; i< itemCount ; i++) {
				Wine item = itemsInCart.get(i);
				System.out.println((i + 1) + ". " + item.getName() + " | " + item.getPrice() + "원");
			}
			System.out.println("Total: " + totalPrice + " 원" );
		}
		
		 
	}
}