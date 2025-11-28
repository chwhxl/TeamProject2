package wineshop;

import java.util.*;

public class WineMain {
    public static void main(String[] args) {
        WineList.loadWineData(WineList.getFilePath());
        List<Wine> shopWineList = WineList.getWineList();
        System.out.println(shopWineList.get(0).getImgPath());
    }
}
