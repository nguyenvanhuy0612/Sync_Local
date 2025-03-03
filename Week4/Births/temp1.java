
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import org.apache.commons.csv.*;
import edu.duke.*;
public class temp1 {

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
        int rank = 0;
        FileResource fr = new FileResource("H:\\github\\Data_week4\\us_babynames_test\\yob"+year+"short.csv");
        for (CSVRecord currRow : fr.getCSVParser(false)){
            String currGender = currRow.get(1);
            if (currGender.matches(gender)){
                rank++;
                String currName = currRow.get(0);
                if (currName.matches(name)){
                    return rank;
                }
            }
        }
        return -1;
    }

    public String getName(int year, int rank, String gender){
        int place = 0;
        FileResource fr = new FileResource("H:\\github\\Data_week4\\us_babynames_test\\yob"+year+"short.csv");
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
        if (rank != -1){
            String newName =  getName(newYear, rank, gender);
            System.out.println(name + " born in "+year+" would be "+newName+" if "+gen+" was born in "+newYear+".");
        }
    }
    
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            String fileName = f.getName().replaceAll("\\D", "");
            int year = Integer.parseInt(fileName);
            
            System.out.println(year);
        }
        
        return 1;
    }
}
