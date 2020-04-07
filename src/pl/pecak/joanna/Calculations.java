package pl.pecak.joanna;

import java.util.*;

import static pl.pecak.joanna.Utility.getDataFromFile;

public class Calculations {

    String path="";


    private static TreeMap<Double,List<String>> decisionDict = new TreeMap<>();
    public static TreeMap<Double, List<String>> getDecisionDict() {
        return decisionDict;
    }

    List<StockData> singleFile = getDataFromFile(path);

    static String[] prepareCalculations(List<StockData> allStockData){
        int batchSize=100;
        double avgChange = 0.00;
        double avgPrice = 0.00;
        double movingAveragePrice = 0.00;
        double movingAveragePriceTotal = 0.00;
        double previous = -1.0;
        int count = 1;
        double batchMovingAveragePrice;
        List<Double> movingAverage = new ArrayList<>();
        for (var data:allStockData) {
            double closing=Double.parseDouble(data.getClose());
            if (previous != -1.0) {
                avgChange += (previous  - closing) ;
            }

            if (count % batchSize==0){
                batchMovingAveragePrice  = movingAveragePrice / batchSize;
                movingAveragePriceTotal += batchMovingAveragePrice;
                movingAverage.add(batchMovingAveragePrice);
                movingAveragePrice = 0;
            }
            avgPrice += closing;
            movingAveragePrice += closing;
            previous = closing;
            count++;

        }
        avgChange /= allStockData.size() - 1;

        batchMovingAveragePrice = movingAveragePrice / (count % batchSize);
        movingAverage.add(batchMovingAveragePrice);

        double[] closings = new double[3];
        closings[0]=Double.parseDouble(allStockData.get(allStockData.size()-1).getClose()) + avgChange;
        for (int i = 1;i<closings.length;i++){
            closings[i]=closings[i-1]+avgChange;
        }

        String[] returnValues = new String[5];
        returnValues[0]=allStockData.get(0).getTicker();
        for (int i = 1;i < 4;i++){
            returnValues[i]=Double.toString(closings[i-1]);
        }
        returnValues[4]="0";
        List<String> names = decisionDict.get(avgChange);
        if (names==null){
            names=new ArrayList<>();
        }
        if (!names.contains(returnValues[0])){
            names.add(returnValues[0]);
        }
        decisionDict.put(avgChange,names);
        return returnValues;
    }


}
