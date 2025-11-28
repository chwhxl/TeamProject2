package wineshop;

import java.util.ArrayList;

public class WineMain {
    public static void main(String[] args) {
        WineList wineManager = new WineList();
        ArrayList<Wine> shopWineList = wineManager.getWineList();
        System.out.println(shopWineList.get(0).getImgPath());
    }
}
