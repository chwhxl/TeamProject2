package wineshop;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class WineList {

    private static List<Wine> wineList = new ArrayList<>();
    private static String filePath = "data/wine/wine.csv";

    public static List<Wine> getWineList() {
        return wineList;
    }
    
    public static String getFilePath() {
		return filePath;
	}

    // 와인 데이터 로드
    public static void loadWineData(String filePath) {
        if (!wineList.isEmpty()) {
            System.out.println("와인 데이터가 이미 존재합니다.");
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("파일을 찾을 수 없습니다: " + filePath);
            return;
        }

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
