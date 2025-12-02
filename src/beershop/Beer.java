package beershop;

import manage.Product;

public class Beer extends Product{
	
	private String country;
	
	public Beer(String name, int price, String type, String country, double alcohol) {
		
		super(name, price, type, alcohol);
		
        this.country = country;
		this.stock = 100;
		
		if (type.equals("Lager")) {
            this.imgPath = "data/beer/images/lager/"+ name +".png";
        } else if (type.equals("Ale")) {
            this.imgPath = "data/beer/images/ale/"+ name +".png";
        } else {
            this.imgPath = "data/beer/images/nonAlc/"+ name +".png";
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
