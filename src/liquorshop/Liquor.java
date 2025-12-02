package liquorshop;

import manage.Product;

public class Liquor extends Product {
	 
	private String country;
	
	public Liquor(String name, int price, String type, String country, double alcohol) {
		
		super(name, price, type, alcohol);
		
		this.country = country;
		this.stock = 20;
		
		if (type.equals("whiskey")) {
            this.imgPath = "data/liqour/images/whisky/"+ name +".png";
        } else if (type.equals("vodka")) {
            this.imgPath = "data/liqour/images/vodka/"+ name +".png";
        } else if (type.equals("tequila")) {
            this.imgPath = "data/liqour/images/tequila/"+ name +".png";
        } else {
			this.imgPath = "data/liqour/images/rum/"+ name +".png";
		}
	}
	
	@Override
	public String getCategory() {
		return "Liquor";
	}
	
	public String getCountry() {
		return country;
	}
}