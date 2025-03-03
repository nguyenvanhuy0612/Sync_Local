
/**
 * Write a description of test1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class test1 {
    public CSVParser tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        return parser;
    }

    public String countryInfo (CSVParser parser, String country){
        String countryReturn ="";
        for (CSVRecord record : parser){
            String currCountry = record.get("Country");
            if (currCountry.contains(country)){
                countryReturn += currCountry + ": " +record.get("Exports") + ": " +record.get("Value (dollars)") + "\n";
            }
        }
        if (countryReturn.isEmpty()){
            return "NOT FOUND";
        }   
        return countryReturn;
    }
    
    public void testcountryInfo(){
        //CSVParser parser = tester();
        //System.out.println(countryInfo(parser, "Germany"));
        //System.out.println(countryInfo(parser, "Namibia"));
        
        String a = "$132,155,000";
        
        String b = a.substring(1);
        String c = b.replaceAll(",", "");
        double aa = Double.parseDouble(c);

    }
    
    
    public void testReplace(){
        String abc = "Toi la Huy, ban la ai Huy";
        String def = abc.replace('H', 'K');
        System.out.println("abc "+abc);
        System.out.println("def "+def);
        
        String ghi = abc.replaceFirst("l", "M");

        System.out.println("ghi "+ghi);
        
        
        
    }
}
