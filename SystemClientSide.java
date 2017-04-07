/**
 * Created by oshriamir on 4/7/17.
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class SystemClientSide implements Matrix.matListener {

    static BufferedImage image;

    public SystemClientSide() {

    }


    public static boolean readImage (String inputImageFilename){

        boolean flag = true;
        try
        {
            //open the image file
            File imageFile = new File(inputImageFilename);
            if(imageFile != null){
                // read the image file
                image = ImageIO.read(imageFile);

            }else{
                System.out.println("cannot open this image file, try another one");
                image = null;
                flag = false;

            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            flag = false;
        }

        return flag;

    }

    public static void calculateEnergyMatrix(){
        //Matrix energyMatrix = getEnergyMatrix(image, energyType);
    }

    /*
    Matrix getEnergyMatrix(BufferedImage image, int type){
        return new Matrix();
    }*/

}
