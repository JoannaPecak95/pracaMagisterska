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

public class Utility {
    public static void extractAllDataFromFiles(File[] listOfFiles, List<List<StockData>> allFiles) {
        for (File file : listOfFiles) {
            if (file.isFile()) {
                allFiles.add(getDataFromFile(file.getAbsolutePath()));
            }
        }
    }

    public static List<StockData> getDataFromFile(String path) {
        List<StockData> file=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine(); //ignorujemy pierwszą linię z legendą
            line=reader.readLine();
            while (line != null) {
                file.add(parseLineToData(line));
                line = reader.readLine();

            }
        } catch (IOException e) {
            System.out.println("Cannot read file");
            e.printStackTrace();
        }
        return file;
    }

    public static StockData parseLineToData(String line){
        String [] dataArray=new String[7];
        try {
            dataArray=line.split(",",-1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Date date= null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(dataArray[1]);
        } catch (ParseException e) {
            System.out.println("Cannot parse data");
            e.printStackTrace();
        }
        return new StockData(dataArray[0],date,dataArray[2],dataArray[3],dataArray[4],dataArray[5],dataArray[6]);
    }
}
