package beershop;

import manage.Product;

public class Beer extends Product{
	
	private String country;
	
	public Beer(String name, int price, String type, String country, double alcohol) {
		
		super(name, price, type, alcohol);
		
        this.country = country;
		this.stock = 100;
		
		if (type.equals("Lager")) {
            this.imgPath = "data/beer/images/Lager/"+ name +".png";
        } else if (type.equals("Ale")) {
            this.imgPath = "data/beer/images/Ale/"+ name +".png";
        } else {
            this.imgPath = "data/beer/images/NonAlc/"+ name +".png";
        }
	}
	
	@Override
	public String getCategory() {
		return "Beer";
	}

	public String getCountry() {
		return country;
	}
}
