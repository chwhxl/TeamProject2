package manage;

import java.util.*;
import wineshop.Wine;

public class CartWineManage {
    private static Map<String, CartWine> cartMap = new HashMap<>();

    // Cart에 CartWine 객체 추가
    public static void addCart(Wine wine, int count) {
        String key = wine.getName();

        if (cartMap.containsKey(key)) {
            CartWine cartWine = cartMap.get(key);
            cartWine.setQuantity(cartWine.getQuantity() + count);
        } else {
            cartMap.put(key, new CartWine(wine, count));
        }
    }

    // Cart에서 CartWine 객체 수량 감소 및 제거
    public static void removeCart(CartWine cartWine, int count) {
        String key = cartWine.getWine().getName();

        if (count < cartWine.getQuantity()) {
            cartWine.setQuantity(cartWine.getQuantity() - count);
        } else {
            cartMap.remove(key);
        }
    }

    // CartMap -> CartList 가져오기 (출력용)
    public static List<CartWine> getCartList() {
        return new ArrayList<>(cartMap.values());
    }

    // Cart 총 합계 금액 계산
    public int getTotalPrice() {
        int totalPrice = 0;
        for (CartWine cartWine : cartMap.values()) {
            totalPrice += cartWine.getWineTotalPrice();
        }
        return totalPrice;
    }

    // Cart 비우기
    public static void clearCart() {
        cartMap.clear();
    }
}
