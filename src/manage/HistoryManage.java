package manage;

import java.util.ArrayList;
import java.util.List;

public class HistoryManage {
	// 히스토리 리스트
    private static List<HistoryProduct> historyList = new ArrayList<>();

    // 히스토리 추가 (이미 있으면 수량만 증가, 없으면 추가)
    public static void addHistory(String user, String name, int price, int quantity) {
    	
        boolean found = false;
        
        for (HistoryProduct hp : historyList) {
            if (hp.getName().equals(name)) {
                hp.setQuantity(hp.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        
        if (!found) {
            historyList.add(new HistoryProduct(user, name, price, quantity));
        }
    }
    
	// 히스토리 리스트 반환
    public static List<HistoryProduct> getHistoryList() {
        return historyList;
    }
}
