
/**
 * Write a description of test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test {
        
    public void test1(int taaIndex, int tagIndex, int tgaIndex){
        //int taaIndex = 0;   
        System.out.println("taaIndex "+taaIndex);
        //int tagIndex = 0;   
        System.out.println("tagIndex "+tagIndex);
        //int tgaIndex = 0;   
        System.out.println("tgaIndex "+tgaIndex);
        int minIndex = 0;   
        System.out.println("minIndex "+minIndex);
        
        if (taaIndex == -1 || ((tagIndex != -1) && (tagIndex < taaIndex))){
            minIndex = tagIndex;
        }else{ minIndex = tagIndex; }
        System.out.println("minIndex lan 1 "+minIndex);
        if (minIndex == -1 || ((tgaIndex != -1) && (tgaIndex < minIndex))){
            minIndex = tgaIndex;
        }
        System.out.println("minIndex lan 2 "+minIndex);
    }
}
