package wineshop;

import manage.Product;

public class Wine extends Product {

    private String type;       // 종류
    private String country;    // 원산지
    private String winery;     // 생산 와이너리
    private String grape;      // 포도 품종
    private String year;       // 생산 연도

    public Wine(String name, int price, String type, String country,
                String winery, String grape, double alcohol, String year) {
        
    	super(name, price, alcohol);
        
        this.type = type;
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

    public String getType() {
        return type;
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
