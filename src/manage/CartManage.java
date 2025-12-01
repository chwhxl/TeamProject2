package manage;

import java.util.*;
import wineshop.Wine;

public class CartManage {
    private static Map<String, CartProduct> cartMap = new HashMap<>();

    // Cart에 CartProduct 객체 추가
    public static void addCart(Product product, int count) {
        String key = product.getName();

        if (cartMap.containsKey(key)) {
            CartProduct cartProduct = cartMap.get(key);
            cartProduct.setQuantity(cartProduct.getQuantity() + count);
        } else {
            cartMap.put(key, new CartProduct(product, count));
        }
    }

    // Cart에서 CartWine 객체 수량 감소 및 제거
    public static void removeCart(CartProduct cartProduct, int count) {
        String key = cartProduct.getProduct().getName();

        if (count < cartProduct.getQuantity()) {
            cartProduct.setQuantity(cartProduct.getQuantity() - count);
        } else {
            cartMap.remove(key);
        }
    }

    // CartMap -> CartList 가져오기 (출력용)
    public static List<CartProduct> getCartList() {
        return new ArrayList<>(cartMap.values());
    }

    // Cart 총 합계 금액 계산
    public static int getTotalPrice() {
        int totalPrice = 0;
        for (CartProduct cartWine : cartMap.values()) {
            totalPrice += cartWine.getProductTotalPrice();
        }
        return totalPrice;
    }

    // Cart 비우기
    public static void clearCart() {
        cartMap.clear();
    }
}
