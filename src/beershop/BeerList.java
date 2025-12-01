package beershop;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class BeerList {

    private static List<Beer> beerList = new ArrayList<>();
    private static String filePath = "data/beer/beer.csv";

    public static List<Beer> getBeerList() {
        return beerList;
    }
    
    public static String getFilePath() {
		return filePath;
	}

    // 맥주 데이터 로드
    public static void loadBeerData() {
        if (!beerList.isEmpty()) {
            System.out.println("맥주 데이터가 이미 존재합니다.");
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
                Beer beer = new Beer(aLine[0], Integer.parseInt(aLine[1]), aLine[2], aLine[3], Double.parseDouble(aLine[4]));
                beerList.add(beer);
            }

        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file from" + filePath);
        }
    }
}