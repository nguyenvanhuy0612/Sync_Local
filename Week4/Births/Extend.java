
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import org.apache.commons.csv.*;
import edu.duke.*;
public class Extend {

    public void printNames() {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100){
                System.out.println("Name "+ rec.get(0) +
                    " Gender "+ rec.get(1) +
                    " Num Born "+ rec.get(2));
            }
        }
    }

    public void totalBirths(FileResource fr ){
        //float totalBirths = 0;
        float totalGirls = 0;
        float totalBoys = 0;
        for (CSVRecord rec : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if (rec.get(1).matches("[f|F]")){ //[fF]
                totalGirls +=numBorn;
            }else{
                totalBoys += numBorn;
            }
        }
        System.out.println("totalGirls: "+totalGirls);
        System.out.println("totalBoys: "+totalBoys);
    }

    public void testTotalBirths(){
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    public int getRank(int year, String name, String gender){
        //Input
        FileResource fr = new FileResource("us_babynames_test\\yob"+year+"short.csv");
        //File name and rank
        int birthOfName = 0;
        int posOfName = 0;
        for (CSVRecord currRow : fr.getCSVParser(false)){
            posOfName++;
            if (currRow.get(0).equals(name) && currRow.get(1).equals(gender)){
                birthOfName = Integer.parseInt(currRow.get(2));
                break;
            }
        }
        if (birthOfName == 0){ 
            return -1;
        }
        int rankOfName = 0;
        int i = 0;
        for (CSVRecord currRow : fr.getCSVParser(false)){
            i++;
            String currGender = currRow.get(1);
            if (currGender.matches(gender)){
                int currBirth = Integer.parseInt(currRow.get(2));
                if (currBirth > birthOfName || (currBirth == birthOfName && i <= posOfName)){
                    rankOfName++;
                }
            }
        }
        return rankOfName;
    }

    public String getName(int year, int rank, String gender){
        int place = 0;
        FileResource fr = new FileResource("us_babynames_test\\yob"+year+"short.csv");
        for (CSVRecord currRow : fr.getCSVParser(false)){
            String currGender = currRow.get(1);
            if (currGender.matches(gender)){
                place++;
                if (place == rank){
                    return currRow.get(0);
                }
            }
        }
        return "NO NAME";
    }

    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        String gen = "he";
        if (gender.matches("F")){
            gen = "she";
        }
        int rank = getRank(year, name, gender);
        String newName =  getName(newYear, rank, gender);
        if (rank != -1 && !newName.equals("NO NAME")){
            System.out.println(name + " born in "+year+" would be "+newName+" if "+gen+" was born in "+newYear+".");
        }else{
            System.out.println("NOT FOUND");
        }
    }

    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int yearNameHighRank = -1;
        int rankInYear = -1;
        for (File f : dr.selectedFiles()){
            String fileName = f.getName().replaceAll("\\D", "");
            int year = Integer.parseInt(fileName);
            int currRankInYear = getRank(year, name, gender);
            if (rankInYear == -1 && currRankInYear != -1){
                rankInYear = currRankInYear;
                yearNameHighRank = year;
            }else{
                if (currRankInYear < rankInYear && currRankInYear != -1){
                    rankInYear = currRankInYear;
                    yearNameHighRank = year;
                }
            }
        }
        return yearNameHighRank;
    }

    public double getAverageRank(String name, String gender){
        int numOfFile = 0;
        int totalRank = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            String fileName = f.getName().replaceAll("\\D", "");
            int year = Integer.parseInt(fileName);
            int currRankInYear = getRank(year, name, gender);
            if (currRankInYear != -1){
                totalRank += currRankInYear;
                numOfFile++;
            }
        }
        if (numOfFile == 0){
            return -1.0;
        }else{
            return (double) totalRank/numOfFile;
        }
    }

    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int totalBirths = 0;
        FileResource fr = new FileResource("us_babynames_test\\yob"+year+"short.csv");
        for (CSVRecord currRow : fr.getCSVParser(false)){
            String currGender = currRow.get(1);
            if (currGender.matches(gender)){
                int currBirth = Integer.parseInt(currRow.get(2));
                String currName = currRow.get(0);
                if (currName.matches(name)){
                    return totalBirths;
                }else{
                    totalBirths += currBirth;
                }
            }
        }
        return -1;
    }
}
