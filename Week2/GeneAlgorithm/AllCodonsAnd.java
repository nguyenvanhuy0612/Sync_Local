
/**
 * Write a description of AllCodonsAnd here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class AllCodonsAnd {
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1){
            if ((currIndex - startIndex) % 3 == 0){
                return currIndex;
            }
            currIndex = dna.indexOf(stopCodon, currIndex + 1);
        }
        return -1;
    }

    public String findGene(String dna, int posStart){

        int startIndex = dna.indexOf("ATG",posStart); //vi tri cua ATG
        System.out.println("startIndex "+startIndex);
        if (startIndex == -1){
            return "";
        }
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");

        System.out.println("taaIndex "+taaIndex);
        System.out.println("tagIndex "+tagIndex);
        System.out.println("tgaIndex "+tgaIndex);

        int minIndex = 0;
        if (taaIndex == -1 || (tagIndex < taaIndex) && (tagIndex != -1)){ minIndex = tagIndex;}
        else {minIndex = taaIndex;}
        System.out.println("minIndex (tagIndex vs taaIndex) "+minIndex);
        if (minIndex == -1 || (tgaIndex < minIndex) && (tgaIndex != -1)){ minIndex = tgaIndex;}
        System.out.println("minIndex (minIndex vs tgaIndex) "+minIndex);
        if (minIndex == -1){
            return "";
        }
        return dna.substring(startIndex, minIndex + 3);
    }

    public void testfindGene(){
        //                      1         2         3         4         5  
        //            0123456789012345678901234567890123456789012345678901234
        String dna = "ATGATGCGCTACGTAAAATGCTAGTCAGCATCATCGGATCGATCATCGACTGTAC";

        System.out.println("dna "+dna);
        System.out.println("findGene "+findGene(dna,0));
    }

    public void printAllGene(String dna){
        int startIndex = 0;
        while (true){
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty()){
                break;
            }
            System.out.println(currGene);
            startIndex = dna.indexOf(currGene, startIndex) +currGene.length();
        }
    }

    public void testprintAllGene(){
        //                      1         2         3         4         5  
        //            0123456789012345678901234567890123456789012345678901234
        String dna = "AATGTAATGAATGGTCCGTCAGTGACCCGATGCAGTCATGCGATGCATGTAG";
        System.out.println("dna "+dna);
        printAllGene(dna);
    }    
}
