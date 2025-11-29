package wineshop;

public class Wine {

    private String name;       // 상품명
    private int price;         // 가격
    private String type;       // 종류
    private String country;    // 원산지
    private String winery;     // 생산 와이너리
    private String grape;      // 포도 품종
    private double alcohol;    // 알코올 도수
    private String year;       // 생산 연도
    private int stock;         // 재고 수량
    private String imgPath;    // 이미지 경로

    public Wine(String name, int price, String type, String country,
                String winery, String grape, double alcohol, String year)
    {
        this.name = name;
        this.price = price;
        this.type = type;
        this.country = country;
        this.winery = winery;
        this.grape = grape;
        this.alcohol = alcohol;
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

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
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

    public double getAlcohol() {
        return alcohol;
    }

    public String getYear() {
        return year;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock(int amount) {
        if (amount <= stock) {
            stock -= amount;
        } else {
            System.out.println("재고가 부족합니다.");
        }
    }

    public String getImgPath() {
        return imgPath;
    }
}
