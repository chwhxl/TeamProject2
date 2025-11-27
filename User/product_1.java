package User;


public class product_1 extends product{
	String theme = "Travis Scott";
	public product_1(String name, int num, int price) {
		this.name = name;
		this.num = num;
		this.price = price;
	}
	public product_1(String name, int price) {
		this.name = name;
		this.num = 0;
		this.price = price;
	}
	@Override
	public void show() { //dynamic binding
		System.out.println("[Travis Scott]: " + name + " / Num :" + num + "  / " + price + " Won.");//제품 검색 출력
	}
	@Override
	public String gettheme() {
		return this.theme;
	};
}
