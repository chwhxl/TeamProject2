package project01;

import java.util.*;

public class Main {
    static void showMain() {
        // 메인 화면 출력 -> static 메서드로 구현 (인스턴스 생성 없이 호출 가능)
        System.out.println("\nWelcome to Blue Miles Coffee Roasters");
        System.out.println("1. 전체 상품 조회");
        System.out.println("2. 상품 검색 (이름/카테고리/가격)");
        System.out.println("3. 장바구니에 상품 담기");
        System.out.println("4. 장바구니 조회");
        System.out.println("5. 결제하기");
        System.out.println("6. 프로그램 종료");
        System.out.print("원하시는 항목의 번호를 입력하세요: ");
    }

    static void searchItem(ManageItem manageitem, Scanner scanner) {
        while (true) {
            System.out.println("\n상품 검색 방법을 선택하세요.");
            System.out.println("1. 이름으로 검색");
            System.out.println("2. 카테고리로 검색");
            System.out.println("3. 가격대로 검색");
            System.out.println("4. 이전으로 돌아가기");
            System.out.print("번호를 입력하세요: ");

            Scanner scannerSearch = new Scanner(System.in);
            int searchOption = scannerSearch.nextInt();

            switch (searchOption) {
                case (1):
                    System.out.print("\n검색할 상품 이름을 입력하세요: ");
                    String itemName = scanner.next();
                    Item[] itemList = manageitem.searchItem(itemName);
                    System.out.println("\n[검색 결과]");

                    if (itemList.length == 0) {
                        System.out.println("검색된 상품이 없습니다.");
                    }
                    else {
                        for (Item item : itemList) {
                            item.getInfo();
                        }
                    }
                    break;

                case (2):
                    System.out.print("\n검색할 카테고리명를 입력하세요: ");
                    // 공백 포함 입력 처리 -> 공백 포함된 카테고리명만 검색이 안돼서 이거 원인 찾느라 1시간 걸렸다 허무하다...
                    // scanner.next() -> scanner.nextLine() 으로 변경했더니 해결 완료
                    String category = scanner.nextLine();
                    Item[] categoryList = manageitem.searchItemByCategory(category);
                    System.out.println("\n[검색 결과]");

                    if (categoryList.length == 0) {
                        System.out.println("검색된 상품이 없습니다.");
                    }
                    else {
                        for (Item item : categoryList) {
                            item.getInfo();
                        }
                    }
                    break;

                case (3):
                    System.out.print("\n최대 가격을 입력하세요: ");
                    int maxPrice = scanner.nextInt();
                    Item[] priceList = manageitem.searchItem(maxPrice);
                    System.out.println("\n[검색 결과]");

                    if (priceList.length == 0) {
                        System.out.println("검색된 상품이 없습니다.");
                    }
                    else {
                        for (Item item : priceList) {
                            item.getInfo();
                        }
                    }
                    break;

                case (4):
                    return;

                default:
                    System.out.println("번호를 다시 입력해주세요.");
            }
        }
    }

    public static void main(String[] args) {
        ManageItem cafeManager = new ManageItem(); // ManageItem 인스턴스 생성 (카페 상품 초기화)
        User user = new User("문창욱", 50000); // User 인스턴스 생성 (사용자 정보 초기화)
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMain();
            try {
                int input = scanner.nextInt();
                scanner.nextLine();

                switch (input) {
                    case (1):
                        System.out.println();
                        cafeManager.viewAllItems();
                        break;

                    case (2):
                        searchItem(cafeManager, scanner);
                        break;

                    case (3):
                        do {
                            System.out.print("장바구니에 담을 상품명을 입력하세요: ");
                            String itemName = scanner.nextLine();
                            Item item = cafeManager.getItemByName(itemName);

                            // 잘못된 상품명을 입력했을 때 처리
                            if (item == null) {
                                System.out.println("[오류] 해당 상품을 찾을 수 없습니다.");
                                System.out.print("계속 담으시겠습니까? (Y/N): ");
                                continue;
                            }
                            boolean isSuccess = user.addItemToCart(item);
                            if (isSuccess == false) {
                                System.out.println("장바구니가 꽉 차서 메인 메뉴로 돌아갑니다.");
                                break; // do-while문 탈출
                            }
                            System.out.print("계속 담으시겠습니까? (Y/N): ");
                        } while (scanner.nextLine().equalsIgnoreCase("Y"));
                        break;

                    case (4):
                        user.viewCart();
                        break;

                    case (5):
                        int totalPrice = user.getTotalPrice();
                        System.out.println("총 결제 금액은 " + totalPrice + "₩입니다.");
                        System.out.print("결제하시겠습니까? (Y/N): ");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("Y")) {
                            user.checkout();
                        } else {
                            System.out.println("결제가 취소되었습니다.");
                        }
                        break;

                    case (6):
                        System.out.println("프로그램을 종료합니다.");
                        scanner.close();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("[오류] 문자 입력은 불가능합니다.");
                scanner.nextLine();
            }
        }
    }
}