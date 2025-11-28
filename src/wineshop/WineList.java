package wineshop;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class WineList {

    private static ArrayList<Wine> wineList = new ArrayList<>();
    private String filePath = "data/wine/wine.csv";
    
    public WineList() {
        readWineCSV(filePath, wineList);
    }

    public static ArrayList<Wine> getWineList() {
        return wineList;
    }

    public void readWineCSV(String filePath, List<Wine> wineList) {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
            	if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
            	
                String[] aLine = line.split(",");
                Wine wine = new Wine(aLine[0], Integer.parseInt(aLine[1]), aLine[2], aLine[3], aLine[4],
                                     aLine[5], Double.parseDouble(aLine[6]), aLine[7]);
                wineList.add(wine);
            }

        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file from" + filePath);
        }
    }
}
