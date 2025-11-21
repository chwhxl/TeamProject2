package project01;

class CartFullException extends Exception {
    public CartFullException(String message) {
        super(message);
    }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}

class CartItem {
    Item cartItem;
    int quantity;

    CartItem(Item item, int quantity) {
        this.cartItem = item;
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }
}

public class User {
    static final int MAX_TOTAL_ITEMS = 10;
    static final int MAX_ITEM_TYPES = 10;
    private final String userName;
    private int currentMoney;

    CartItem[] cart;
    int cartItemTypeCount;
    int cartTotalItemCount;

    User(String name, int initialMoney) {
        this.userName = name;
        this.currentMoney = initialMoney;
        this.cart = new CartItem[MAX_ITEM_TYPES];
        this.cartItemTypeCount = 0;
        this.cartTotalItemCount = 0;
    }
    // 장바구니에 아이템 추가하는 메서드가 제일 구현하기 힘들었다
    // throw문을 통한 사용자 정의 예외 처리도 처음 해봐서 어렵기도 했고
    // 장바구니에 동일한 아이템이 있는지 확인하는 로직도 까다로웠다
    // 무엇보다 가장 어려웠던 점은 장바구니에 담긴 아이템의 총 개수와 종류별 개수를 따로 관리하는 것이었다
    // 장바구니에 담긴 아이템의 총 개수는 10개를 넘을 수 없지만, 종류별로는 최대 10종류까지 담을 수 있기 때문에
    // 두 가지를 수량을 따로 구분해서 관리하는 로직을 결국 완성했다
    // 자바를 학습하면서 배열에 대해서만 배웠고, 리스트같은 컬렉션에 대해 아직 배우지 않아 복잡한 자료형 관리를 어떻게 배열로만 구현해야 할지 고민이 많았다
    // 그래서 결국은 CartItem이라는 별도의 클래스를 만들어서 Item 객체와 수량을 함께 관리하도록 했다
    public boolean addItemToCart(Item item) {
        try {
            if (item.isOutOfStock()) {
                throw new OutOfStockException("'" + item.getItemName() + "' 상품은 재고가 없습니다.");
            }

            if (this.cartTotalItemCount >= MAX_TOTAL_ITEMS) {
                throw new CartFullException("장바구니가 꽉 찼습니다. (최대 " + MAX_TOTAL_ITEMS + "개)");
            }
            // 가장 먼저 카트 안에 동일한 상품이 있는지 확인
            CartItem existingCartItem = null;
            for (int i = 0; i < this.cartItemTypeCount; i++) {
                if (this.cart[i].cartItem.getItemName().equals(item.getItemName())) {
                    existingCartItem = this.cart[i];
                    break;
                }
            }
            // 카트 안에 이미 담긴 상품인 경우 상품 객체의 수량만 1 증가
            if (existingCartItem != null) {
                if (existingCartItem.quantity >= item.getCurrentStock()) {
                    throw new OutOfStockException("'" + item.getItemName() + "' 상품은 재고(" + item.getCurrentStock() + "개)보다 더 담을 수 없습니다.");
                }
                existingCartItem.increaseQuantity();
            }
            // 카트 안에 담기지 않은 새로운 상품인 경우 카트에 상품 객체 추가 후 타입 카운트 +1
            else {
                this.cart[this.cartItemTypeCount] = new CartItem(item, 1);
                this.cartItemTypeCount++;
            }
            // 장바구니에 담긴 전체 상품 개수 1 증가
            this.cartTotalItemCount++;
            System.out.println("'" + item.getItemName() + "' 상품을 장바구니에 담았습니다.");
            return true; // 성공적으로 담았을 때는 true 반환

        } catch (OutOfStockException e) {
            System.out.println("[오류] " + e.getMessage());
            return true; // 재고가 없을 때는 루프를 계속 돌 수 있도록 true 반환
        } catch (CartFullException e) {
            System.out.println("[오류] " + e.getMessage());
            return false; // 장바구니 꽉 찼을 때는 루프를 중단하도록 false 반환
        }
    }

    void viewCart() {
        System.out.println();
        System.out.println("--- " + this.userName + "님의 장바구니 ---");
        if (this.cartTotalItemCount == 0) {
            System.out.println("장바구니가 비어 있습니다.");

        } else {
            for (int i = 0; i < this.cartItemTypeCount; i++) {
                CartItem item = this.cart[i];
                System.out.println(item.cartItem.getItemName() + " | 수량: " + item.quantity + " | 가격: " + item.cartItem.getPrice() + "₩");
            }
            System.out.println("총 결제 금액: " + getTotalPrice() + "₩");
        }
    }

    int getTotalPrice() {
        int total = 0;
        for (int i = 0; i < this.cartItemTypeCount; i++) {
            total += this.cart[i].cartItem.getPrice() * this.cart[i].quantity;
        }
        return total;
    }

    void checkout() {
        int totalPrice = getTotalPrice();
        if (totalPrice > this.currentMoney) {
            System.out.println("잔액이 부족합니다. 현재 잔액: " + this.currentMoney + "₩, 총 결제 금액: " + totalPrice + "₩");
        }
        else {
            this.currentMoney -= totalPrice; // 잔액에서 총 결제 금액 차감

            for (int i = 0; i < this.cartItemTypeCount; i++) {
                CartItem item = this.cart[i];
                item.cartItem.reduceStock(item.quantity); // Item의 재고 감소
            }

            System.out.println("결제가 완료되었습니다. 총 결제 금액: " + totalPrice + "₩, 남은 잔액: " + this.currentMoney + "₩");
            this.cart = new CartItem[MAX_ITEM_TYPES]; // 이전 카트는 더이상 참조하지 않으므로 가비지가 됨 -> 새로운 카트 할당
            this.cartItemTypeCount = 0;
            this.cartTotalItemCount = 0;
        }
    }
}