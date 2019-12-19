
/**
 * Write a description of GrayScaleConverter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class GrayScaleConverter {
    public ImageResource makeGray(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel : outImage.pixels()){
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            int average = (inPixel.getRed() + inPixel.getGreen() + inPixel.getBlue()) / 3;
            pixel.setRed(average);
            pixel.setGreen(average);
            pixel.setBlue(average);
        }
        return outImage;
    }
    public void testMakeGray(){
        ImageResource ir = new ImageResource();
        ImageResource gray = makeGray(ir);
        ir.draw();
        gray.draw();
    }
}
