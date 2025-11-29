package manage;

import java.util.ArrayList;
import wineshop.*;

public class payment {

    /** 
     * j : winelist 에서 선택된 와인의 인덱스
     * return : 결제 금액
     */
    public static int payments(int j) {

        var winelist = WineList.getWineList();
        if (winelist == null || j < 0 || j >= winelist.size()) {
            System.err.println("payments: 잘못된 인덱스 " + j);
            return 0;
        }

        Wine original = winelist.get(j);

        int buyQty = original.getStock();              // 현재 설계에서는 “전체 재고 구매”
        int amount = original.getPrice() * buyQty;

        // -------------------------------
        // 1) HISTORY에 같은 이름 있는지 확인
        // -------------------------------
        boolean found = false;

        for (int i = 0; i < history.historyitem.size(); i++) {
            Wine hw = history.historyitem.get(i);

            if (hw.getName().equalsIgnoreCase(original.getName())) {
                hw.increaseStock(buyQty);  // ★ 기존 히스토리에 수량 누적
                found = true;
                break;
            }
        }

        // -------------------------------
        // 2) 같은 이름이 없다면 복사본 추가
        // -------------------------------
        if (!found) {
            Wine copy = new Wine(
            		original.getName(),
            		original.getPrice(),
            		original.getType(),
            		original.getCountry(),
            		original.getWinery(),
            		original.getGrape(),
            		original.getAlcohol(),
            		original.getYear()
            		//original.getStock()
            		//original.getImgPath()
            );
            //copy.imgPath = original.getImgPath();
            history.historyitem.add(copy);
        }

        // -------------------------------
        // 3) 원본 리스트에서 재고 감소 (0되면 삭제)
        // -------------------------------
        original.decreaseStock(buyQty);   // 재고 감소

        if (original.getStock() == 0) {
            winelist.remove(j);          // 재고 0이면 삭제
        }

        return amount;
    }
}
