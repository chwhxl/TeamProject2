package wineshop;

import manage.Product;

public class Wine extends Product {
      
    private String country;    
    private String winery;     
    private String grape;      
    private String year;       

    public Wine(String name, int price, String type, String country,
                String winery, String grape, double alcohol, String year) {
        
    	super(name, price, type, alcohol);
        
        this.country = country;
        this.winery = winery;
        this.grape = grape;
        this.year = year;
        this.stock = 10;

        if (type.equals("Sparkling wine")) {
            this.imgPath = "data/wine/images/sparkling/"+ name +".png";
        } else if (type.equals("Red wine")) {
            this.imgPath = "data/wine/images/red/"+ name +".png";
        } else {
            this.imgPath = "data/wine/images/white/"+ name +".png";
        }
    }
    
    @Override
    public String getCategory() {
		return "Wine";
	}

    public String getCountry() {
        return country;
    }

    public String getWinery() {
        return winery;
    }

    public String getGrape() {
        return grape;
    }

    public String getYear() {
        return year;
    }
}
