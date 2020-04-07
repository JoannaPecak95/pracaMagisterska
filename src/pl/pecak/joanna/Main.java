package pl.pecak.joanna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import static pl.pecak.joanna.Calculations.getDecisionDict;
import static pl.pecak.joanna.Calculations.prepareCalculations;
import static pl.pecak.joanna.Utility.extractAllDataFromFiles;

public class Main {

    private static int budget=10000000;
    private static int stockCap = 1000000;
    public static void main(String[] args) {
        String pathToMainDir ="C:\\Users\\ASIA\\Desktop\\magisterkaDane";
        File folder = new File(pathToMainDir);
        File[] listOfFiles = folder.listFiles();
        List<List<StockData>> allFiles = new ArrayList<>();
        extractAllDataFromFiles(listOfFiles, allFiles);
        List<Double> prices=new ArrayList<>();
        List<String[]> stockInfo = new ArrayList<>();
        for (List<StockData> file: allFiles) {
            stockInfo.add(prepareCalculations(file));
            for (var closings:file) {
                prices.add(Double.valueOf(closings.getClose()));
            }
        }
        SortedMap<Double, List<String>> buyMap = getDecisionDict().subMap(-1000000.00, 1000000.00);

        int buyCounter=0;
        while (budget>0 && buyMap.size()>0){
            double highest= buyMap.lastKey();
            List<String> stocks=buyMap.get(highest);
            for (var stockId:stocks) {
                buyMap.get(highest);
                double lastClosingPrice;
                int quantity;
                for (String[] strings : stockInfo) {
                    if (strings[0].equals(stockId)) {
                        lastClosingPrice = prices.get(prices.size() - 1);
                        if (budget < stockCap) {
                            quantity = (int) (budget / lastClosingPrice);
                        } else {
                            quantity = (int) (stockCap / lastClosingPrice);
                        }
                        budget -= quantity * lastClosingPrice;
                        //todo ogarnać stockDetails
                        if (quantity > 0) {
                            buyCounter++;
                        }
                        //todo ogarnąć closing entreis
                        if (buyCounter > 10 && budget < 10000) {
                            budget = 0;
                            break;
                            //zakładamy, że jeśli kupujemy powyżej 10 akcji, a budżet zejdzie poniżej 10k to dalej nie kupujemy
                        }
                        break;
                    }
                }
                buyMap.remove(highest);
            }
        }
        for (var element:buyMap.entrySet()) {
            System.out.println(element.getKey() + "  -  " + element.getValue());
        }
    }
}
