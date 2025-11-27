package User;

import java.util.ArrayList;
//swing 사용
public class payment {
	static product[] carts = cart.list;
	public static ArrayList<product> cartitt = pay.cartitem;
	static ArrayList<product> histori = history.historyitem; //구매내역 레퍼런스
	
	public static void payments(int j) {

		int k=0;
	    //체크박스 눌러서 체크 된 오브젝트들만 결제되도록.
	    
	    k += cartitt.get(j).getprice() * cartitt.get(j).getnum(); //가격 계산
	    System.out.println(k+"원 결제.");
	    int u = 0;
	    // 유저.돈 - k; 
	    for(int i=0;i<histori.size();i++) {
	    	if(histori.get(i).name.equalsIgnoreCase(carts[j].name)){ //hisroti배열에 같은 값이 있다면
	    		histori.get(i).AddtoCart(cartitt.get(j).getnum()); //그 아이템을 카트에 있는 갯수만큼 증가시키기
	    		u++; 
	    	}
	    }
	    if(u==0) {histori.add(carts[j]);}//카트에 없으면 새로 넣기
	    carts[j].RemovefromCart(cartitt.get(j).getnum());//카트에서 물건 빼기

	}
	
}
