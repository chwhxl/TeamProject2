package liquorshop;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class LiquorList {

    private static List<Liquor> liquorList = new ArrayList<>();
    private static String filePath = "data/liquor/liquor.csv";

    public static List<Liquor> getLiquorList() {
        return liquorList;
    }
    
    public static String getFilePath() {
		return filePath;
	}

    // 리큐르 데이터 로드
    public static void loadLiquorData() {
        if (!liquorList.isEmpty()) {
            System.out.println("리큐르 데이터가 이미 존재합니다.");
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
                Liquor liquor = new Liquor(aLine[0], Integer.parseInt(aLine[1]), aLine[2], aLine[3], Double.parseDouble(aLine[4]));
                liquorList.add(liquor);
            }

        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file from" + filePath);
        }
    }
}
