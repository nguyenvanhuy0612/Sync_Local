
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class Part3 {
    int currStopCodonIndex = 0;
    public String collectDNA(){
        FileResource f = new FileResource();
        String dna = "";
        for (String s : f.words()){
            dna += s;
        }
        //System.out.println("dna "+dna);
        return dna;
    }

    public int findStopIndex(String dna, int startIndex, String stopCodon){ //System.out.println("findStopIndex");
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1){
            int distance = currIndex - startIndex;
            if (distance %3 == 0){
                return currIndex;
            }
            currIndex = dna.indexOf(stopCodon, currIndex + 1);
        }
        return dna.length();
    }

    public void testFindStopCodon(){

        String dna = "ATGCGTGATTAA";
        System.out.println("findStopIndex "+findStopIndex(dna, 0, "TAA"));

    }

    public String findGene(String dna, int beginIndex){
        int startIndex = dna.indexOf("ATG", beginIndex);
        if (startIndex == -1){
            return "";
        }

        int taaIndex = findStopIndex(dna, startIndex, "TAA");   //System.out.println("taaIndex "+taaIndex);
        int tagIndex = findStopIndex(dna, startIndex, "TAG");   //System.out.println("tagIndex "+tagIndex);
        int tgaIndex = findStopIndex(dna, startIndex, "TGA");   //System.out.println("tgaIndex "+tgaIndex);

        int minIndex = Math.min(taaIndex, tagIndex);
        minIndex = Math.min(minIndex, tgaIndex);                //System.out.println("minIndex "+minIndex);

        if (minIndex == dna.length()){
            return "";
        }
        currStopCodonIndex = minIndex + 3;
        
        //System.out.println("currStopCodonIndex = minIndex + 3; "+currStopCodonIndex);
        return dna.substring(startIndex, minIndex + 3);
    }

    public void testFindGene(){
        String dna = collectDNA();
        dna = dna.toUpperCase();
        System.out.println(dna);
        System.out.println("dna.length() "+dna.length());
        System.out.println(findGene(dna, 0));
    }

    public void printAllGenes(String dna){
        int startIndex = dna.indexOf("ATG");
        while (startIndex != -1){
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty()){
                break;
            }
            System.out.println("currGene "+currGene);
            //startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
            startIndex = currStopCodonIndex;
            //System.out.println("startIndex "+startIndex);
            //System.out.println("currStopCodonIndex "+currStopCodonIndex);
        }
    }

    public int countGenes(String dna){
        int startIndex = dna.indexOf("ATG");
        int totalGenes = 0;
        while (startIndex != -1){
            String currGene = findGene(dna, startIndex);
            if (currGene.isEmpty()){
                break;
            }
            //System.out.println("currGene "+currGene);
            totalGenes += 1;
            startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
        }
        return totalGenes;
    }

    public void testPrintAllGenes(){
        String dna = collectDNA();
        dna = dna.toUpperCase();
        System.out.println(dna);
        System.out.println("dna.length() "+dna.length());
        printAllGenes(dna);
    }

    public void testCountGenes(){
        System.out.println("number of Gene in DNA "+countGenes("ATGTAAGATGCCCTAGT"));
    }
}
