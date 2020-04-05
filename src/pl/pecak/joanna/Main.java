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

import static pl.pecak.joanna.Utility.extractAllDataFromFiles;

public class Main {

    public static void main(String[] args) {
        String pathToMainDir ="C:\\Users\\ASIA\\Desktop\\magisterkaDane";
        File folder = new File(pathToMainDir);
        File[] listOfFiles = folder.listFiles();
        List<List<StockData>> allFiles = new ArrayList<>();
        extractAllDataFromFiles(listOfFiles, allFiles);

    }
}
