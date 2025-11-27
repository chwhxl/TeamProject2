package User;


public class product { //제품 클래스 (super class)
	public String name; //제품 이름
	int num; //갯수 default 로 선언 -> 다른 클래스에서 접근 불가
	int price; //가격
	
	public void show() {	//Override 를 위한 함수 정의
		System.out.println( name + " : " + num + " / Num: " + price + " Won.");//제품 검색 출력
	}
	public int RemovefromStorage(int num) { //storage 에서 추가할 만큼 꺼냄
		if((this.num -=num)<= 0) {
			this.num += num;
			return 1;
		}
		return 0;
	}
	public int getnum() { //num의 값을 다른 클래스에서 보기 위한 함수 (encapsulation)
		return this.num;
	}
	public int getprice() {//price 값을 리턴해주는 함수
		return this.price;
	}
	public void setnum(int num) {
		this.num = num;
	}
	public void AddtoCart(int num) { //num 만큼 카트에 추가 (Cart에 추가)
		this.num += num;
	}
	public String gettheme() {//Override를 위한 매서드
		return "";
	}
	public int AddtoStorage(int num) { // storage에 추가
		if((this.num += num)<= 0) {
			this.num -= num;
			return 1;
		}
		return 0;
	}
	public void RemovefromCart(int num) { //num 만큼 카트에서 빼기
		this.num -= num;
	}
	
}


