package csvapp;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
 

/**
 * data in RegionsAndAreas.csv, given to you in Canvas, is from 
 * http://api.worldbank.org/v2/en/indicator/AG.LND.TOTL.K2?downloadformat=csv
 * Retrieved 2022-04-22
 * data cleaned to start with header row
 */
public class CsvApp {

    public static void main(String[] args) throws IOException, FileNotFoundException, CsvValidationException {
        List<GeographicRegion> regionList = populateList("RegionsAndAreas.csv");
        Chooser<GeographicRegion> chooser = new Chooser<>(regionList);
        for(int i = 0; i < 10; i++) {
            System.out.println(chooser.choose());
        }
    }

    private static List<GeographicRegion> populateList(String fileName) throws FileNotFoundException, IOException, CsvValidationException {
        Map<String, String> values;
        List<GeographicRegion> regionList = new ArrayList<>();
        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(fileName))) {
            while ((values = reader.readMap()) != null) {
                String regionName = values.get("Country Name");
                String areaString = values.get("2020");
                if (areaString != null && !areaString.isEmpty()) {
                    Double area = Double.parseDouble(areaString);
                    regionList.add(new GeographicRegion(regionName, area));
                }
            }
        }
        return regionList;       
    }

}
