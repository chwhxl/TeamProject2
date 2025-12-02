package liquorshop;

import manage.Product;

public class Liquor extends Product {
	 
	private String country;
	
	public Liquor(String name, int price, String type, String country, double alcohol) {
		
		super(name, price, type, alcohol);
		
		this.country = country;
		this.stock = 20;
		
		if (type.equalsIgnoreCase("whisky")) {
            this.imgPath = "data/liquor/images/whisky/"+ name +".png";
        } else if (type.equalsIgnoreCase("vodka")) {
            this.imgPath = "data/liquor/images/vodka/"+ name +".png";
        } else if (type.equalsIgnoreCase("tequila")) {
            this.imgPath = "data/liquor/images/tequila/"+ name +".png";
        } else {
			this.imgPath = "data/liquor/images/rum/"+ name +".png";
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