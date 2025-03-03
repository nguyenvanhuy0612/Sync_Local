
/**
 * Write a description of test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test {
    Part1 p1 = new Part1();

    public String findGene(String dna, int beginIndex){
        int startIndex = dna.indexOf("ATG", beginIndex);
        if (startIndex == -1){
            return "";
        }
        int taaIndex = p1.findStopIndex(dna, startIndex, "TAA");
        int tagIndex = p1.findStopIndex(dna, startIndex, "TAG"); 
        int tgaIndex = p1.findStopIndex(dna, startIndex, "TGA");
        int minIndex = 0;

        if (taaIndex*tagIndex == 1){ minIndex = -1;}
        else if (taaIndex*tagIndex == -1){ minIndex = -1*taaIndex*tagIndex;}
        else{ minIndex = Math.min(taaIndex,tagIndex);}
        
        
        return dna.substring(startIndex, minIndex + 3);
    }

    public double cgRatio(String dna){
        int i = 0;
        int countcg = 0;
        while (i < dna.length()){
            if ( 'C' == dna.charAt(i) || 'G' == dna.charAt(i) ){
                countcg++;
            }
            i++;
        }
        return (double) countcg/ dna.length();

    }

    public void testUpperCase() {
        String dna = "atgctagctacgactacg";
        dna = dna.toUpperCase();
        System.out.println("DNA: "+dna+" lengh "+dna.length());
        dna = dna.toUpperCase();
        System.out.println("DNA: "+dna+" lengh "+dna.length());
    }

}

