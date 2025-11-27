package WineShop;

enum WineType {
    RED, WHITE, SPARKLING
}

enum WineCountry {
    FRANCE, ITALY, SPAIN
}

public class Wine {

    private String name; // 상품명
    private int price; // 가격
    private WineType type; // 종류
    private WineCountry country; // 원산지
    private String winery; // 생산 와이너리
    private String grape; // 포도 품종
    private double alcohol; // 알코올 도수
    private int year; // 생산 연도
    private int stock; // 재고 수량
    private String imgPath; // 이미지 경로

    public Wine(String name, int price, WineType type, WineCountry country, String winery, String grape, double alcohol, int year, int stock) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.country = country;
        this.winery = winery;
        this.grape = grape;
        this.alcohol = alcohol;
        this.year = year;
        this.stock = 0;
        this.imgPath = null;
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

    public int getStock() {
        return stock;
    }

    public String getImgPath() {
        return imgPath;
    }

    @Override
    public String toString() {
        // 상품 상세 정보 출력
        return "";
    }
}
