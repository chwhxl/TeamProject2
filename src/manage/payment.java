package manage;

import java.util.ArrayList;

import wineshop.Wine;
//swing 사용
public class payment {
	static ArrayList<Wine> histori = new ArrayList<>(); //구매내역 레퍼런스
	
	public static int payments(int j) {
		int k=0;
	    //체크박스 눌러서 체크 된 오브젝트들만 결제되도록.
	    
	    k += pay.cartitem.get(j).getPrice() * pay.cartitem.get(j).getStock(); //가격 계산
	    System.out.println(k + "원 결제.");
	    int u = 0;
	    // 유저.돈 - k; 
	    for(int i=0;i<history.historyitem.size();i++) {
	    	if(history.historyitem.get(i).getName().equalsIgnoreCase(pay.cartitem.get(j).getName())){ //hisroti배열에 같은 값이 있다면
	    		history.historyitem.get(j).AddtoCart(pay.cartitem.get(j).getStock()); //그 아이템을 카트에 있는 갯수만큼 증가시키는 함수 필요
	    		u++; 
	    	//}
	    	}
	    }
	    if(u==0) {history.historyitem.add(pay.cartitem.get(j));}//카트에 없으면 새로 넣기 (아이템 배열 추가 필요)
	    pay.cartitem.get(j).RemovefromCart(pay.cartitem.get(j).getStock());//카트에서 물건 빼기
	    return k;
	}	
}