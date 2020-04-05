package pl.pecak.joanna;

import java.util.*;

import static pl.pecak.joanna.Utility.getDataFromFile;

public class Calculations {

    String path="";
    private Map<Double,List<String>> decisionDict = new HashMap<>();
    List<StockData> singleFile = getDataFromFile(path);

    void fancyMethod(List<StockData> allStockData){

        double avgChange = 0.00;
        double avgPrice = 0.00;
        double movingAvgPrice = 0.00;
        double movingAvgPriceTotal = 0.00;
        double previous = -1.0;
        int count = 1;
        for (var data:allStockData) {
            double closing=Double.parseDouble(data.getClose());
            if (previous != -1.0) {
                avgChange += (previous  - closing) ;
            }
            List<Double> movingAverage = new ArrayList<>();
            if (count%100==0){
                double currentMovingAvgPrice = movingAvgPrice / 100;
                movingAvgPriceTotal += currentMovingAvgPrice;
                movingAverage.add(currentMovingAvgPrice);
                movingAvgPrice = 0;
            }
            avgPrice += closing;
            movingAvgPrice += closing;
            previous = closing;
            count++;

        }
        avgPrice /= allStockData.size();
        avgChange /= allStockData.size() - 1;

    }


}
