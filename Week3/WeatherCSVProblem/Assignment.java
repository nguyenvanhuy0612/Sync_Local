
/**
 * Write a description of Assignments here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Assignment {

    //Temperature

    public CSVRecord getLowestTemperatureOfTwoRow(CSVRecord lowestSoFar, CSVRecord currentRow){
        String currTemp = currentRow.get("TemperatureF");
        if (!(currTemp.matches("-?\\d+(\\.\\d+)?"))){
            return lowestSoFar; //maybe null
        }
        if (lowestSoFar == null){
            return currentRow; //not null
        }
        String lowestTemp = lowestSoFar.get("TemperatureF");
        double currentTemperature = Double.parseDouble(currTemp);
        double lowestTemperature = Double.parseDouble(lowestTemp);
        if (lowestTemperature > currentTemperature && currentTemperature != -9999){
            return currentRow; //not null
        }
        return lowestSoFar; //not null
    }

    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord lowestSoFar = null;
        for (CSVRecord currentRow : parser){
            lowestSoFar = getLowestTemperatureOfTwoRow(lowestSoFar, currentRow);
        }
        return lowestSoFar; //maybe null
    }

    public String fileWithColdestTemperature(){
        File fileLowesTemp = null;
        CSVRecord lowestTempRec = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser currentParser = fr.getCSVParser();
            CSVRecord currentRecord = coldestHourInFile(currentParser);// Row in file f that min temperature

            while (true){
                if (currentRecord == null){
                    System.out.println(f.getName() + " is null");
                    break;
                }
                if (lowestTempRec == null){
                    lowestTempRec = currentRecord;
                    fileLowesTemp = f;
                    break;
                }
                double currentTemperature = Double.parseDouble(currentRecord.get("TemperatureF"));
                double lowestTemperature = Double.parseDouble(lowestTempRec.get("TemperatureF"));
                if (lowestTemperature > currentTemperature && currentTemperature != -9999){
                    lowestTempRec = currentRecord;
                    fileLowesTemp = f;
                }
                break;
            }
        }
        if (fileLowesTemp == null){
            return "File not found";
        }
        return fileLowesTemp.getName(); //not null
    }

    //=======================================================================================
    //Humidity

    public CSVRecord getLowestHumidityOfTwoRow(CSVRecord lowestSoFar, CSVRecord currentRow){
        if (lowestSoFar == null){
            lowestSoFar = currentRow;
        }else{
            String currHum = currentRow.get("Humidity");
            String lowestHum = lowestSoFar.get("Humidity");
            if (currHum.matches("-?\\d+(\\.\\d+)?") && lowestHum.matches("-?\\d+(\\.\\d+)?")){
                double currentHumidity = Double.parseDouble(currHum);
                double lowestHumidity = Double.parseDouble(lowestHum);
                if (lowestHumidity > currentHumidity){
                    lowestSoFar = currentRow;
                }
            }
        }
        return lowestSoFar; //maybe null
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestSoFar = null;
        for (CSVRecord currentRow : parser){
            lowestSoFar = getLowestHumidityOfTwoRow(lowestSoFar, currentRow);
        }
        //System.out.println(lowestSoFar.toString());
        //System.out.println("This return the first record that was found with lowest humidity");
        return lowestSoFar;
    }

    public String fileWithLowestHumidity(){
        File fileLowestHum = null;
        CSVRecord lowestHumRow = null;

        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser currentParser = fr.getCSVParser();

            CSVRecord currentRecord = lowestHumidityInFile(currentParser);
            if (lowestHumRow == null){
                lowestHumRow = currentRecord;
            }else{
                double currentHumidity = Double.parseDouble(currentRecord.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestHumRow.get("Humidity"));
                if (lowestHumidity > currentHumidity){
                    lowestHumRow = currentRecord;
                    fileLowestHum = f;
                }
            }
        }
        String fileName = fileLowestHum.getName();
        return fileName; //not null
    }

    //=======================================================================================
    //Average Temperature

    public double averageTemperatureInFile(CSVParser parser){
        double totalTemperature = 0;
        int totaRow = 0;
        for (CSVRecord currentRow : parser){
            double currentTemperature = Double.parseDouble(currentRow.get("TemperatureF"));
            totalTemperature += currentTemperature;
            totaRow++;
        }
        return totalTemperature/totaRow;
    }

    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double totalTemperature = 0;
        int totalRow = 0;
        for (CSVRecord currentRow : parser){
            double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
            if (currentHumidity >= value){
                double currentTemperature = Double.parseDouble(currentRow.get("TemperatureF"));
                totalTemperature += currentTemperature;
                totalRow++;
            }
        }
        if (totalRow == 0){
            System.out.println("No temperature with that humidity");
            totalRow = -1;
        }
        return totalTemperature/totalRow;
    }

    //=======================================================================================
    //TEST

    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestSoFar = coldestHourInFile(parser);
        System.out.println("The lowest temperature was "+lowestSoFar.get("TemperatureF")+" at "+lowestSoFar.get(0)+" in "+lowestSoFar.get("DateUTC"));
    }

    public void testFileWithColdestTemperature(){
        String fileName = fileWithColdestTemperature();
        System.out.println("Coldest day was in the file "+fileName);
        System.out.println("File: "+"nc_weather\\2014\\"+fileName);

        FileResource fr = new FileResource("nc_weather\\2014\\"+fileName);
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestTemp = coldestHourInFile(parser);
        System.out.println("Coldest temperature on that day was "+lowestTemp.get("TemperatureF"));
        System.out.println("All the Temperature on the coldest day were: ");

        parser = fr.getCSVParser();// Xai lai cai parser can khai bao bien lai parser
        for (CSVRecord row : parser){       
            System.out.println(row.get("DateUTC")+" "+row.get("TemperatureF"));
        }
    }

    public void testFileWithLowestHumidity(){
        String fileName = fileWithLowestHumidity();
        System.out.println("Lowest Humidity of day was in the file "+fileName);
        System.out.println("File: "+"nc_weather\\2014\\"+fileName);

        FileResource fr = new FileResource("nc_weather\\2014\\"+fileName);
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHum = coldestHourInFile(parser);
        System.out.println("Lowest Humidity on that day was "+lowestHum.get("Humidity"));
        System.out.println("All the Humidity on the coldest day were: ");

        parser = fr.getCSVParser();// Xai lai cai parser can khai bao bien lai parser
        for (CSVRecord row : parser){       
            System.out.println(row.get("DateUTC")+" "+row.get("Humidity"));
        }
    }

    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHumidity = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was "+lowestHumidity.get("Humidity")+" at "+lowestHumidity.get("DateUTC"));
    }

    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        System.out.println("Average temperature in file is " + averageTemperatureInFile(fr.getCSVParser()) + ".");
    }

    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemperature = averageTemperatureWithHighHumidityInFile(parser, 80);
        if ( avgTemperature > 0){
            System.out.println("Average Temp when high Humidity is "+avgTemperature);
        }
    }
}
