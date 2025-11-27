package manage;

enum WineType {
    RED, WHITE, SPARKLING
}

enum WineCountry {
    FRANCE, ITALY, SPAIN
}

public class Wine {

    protected String name; // 상품명
    protected int price; // 가격
    protected WineType type; // 종류
    protected WineCountry country; // 원산지
    protected String winery; // 생산 와이너리
    protected String grape; // 포도 품종
    protected double alcohol; // 알코올 도수
    protected int year; // 생산 연도
    protected int stock; // 재고 수량
    protected String imgPath // 이미지 경로

    public Wine(String name, int price, WineType type, WineCountry country, String winery, String grape, double alcohol, int year, String imgPath) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.country = country;
        this.winery = winery;
        this.grape = grape;
        this.alcohol = alcohol;
        this.year = year;
        this.stock = 0;
        this.imgPath = imgPath;
        
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public WineType getType() {
        return type;
    }

    public WineCountry getCountry() {
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

    public int getYear() {
        return year;
    }
    
    public String imgPath() {
        return imgPath;
    }
    
    @Override
    public String toString() {
        // 상품 상세 정보 출력
        return "";
    }
}
