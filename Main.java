import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
//        if (args.length != 6) {
//            System.out.println("Number of passed arguments is not valid");
//            return;
//        }

        // getting all parameters
//        String inputImageFilename = args[1];
//        int cols = Integer.parseInt(args[2]);
//        int rows = Integer.parseInt(args[3]);
//        int energyType = Integer.parseInt(args[4]);
//        String outputImageFilename = args[5];

        String inputImageFilename = "halong_bay.jpg";
        String outputImageFilename = "halong_bay_out.jpg";

        // get output file extension
        String fileSuffix = "";
        int i = outputImageFilename.lastIndexOf('.');
        if (i > 0) {
            fileSuffix = outputImageFilename.substring(i+1);
        }

        try
        {
            // read the image file
            BufferedImage inImage = ImageIO.read(new File(inputImageFilename));
//            Matrix energyMatrix = getEnergyMatrix(inImage, energyType);
//

            BufferedImage outImage = convertMatrixToBufferedImage(Matrix.getRGBMatrix(inImage));
            ImageIO.write(outImage, fileSuffix, new File(outputImageFilename));
            System.out.println("end");

        }
        catch (IOException e)
        {
            // log the exception
            // re-throw if desired
        }

    }



    private static void makeCheck(){

        ArrayList<String> list=new ArrayList<String>();//Creating arraylist
        list.add("Roni");//Adding object in arraylist
        list.add("Vivi");
        list.add("didi");
        list.add("AiAi");

        System.out.print(list.size());
        //Traversing list through Iterator
        Iterator itr=list.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }

        list.remove(2);
        list.remove(1);
        itr=list.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }


    private static BufferedImage convertMatrixToBufferedImage(Matrix rgbMatrix) {
        int n = rgbMatrix.getN();  // #rows, height of image
        int m = rgbMatrix.getM();   // #cols, width of image
        BufferedImage img = new BufferedImage(m, n, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int rgb = (int)rgbMatrix.get(i,j);
                img.setRGB(j, i, rgb);
            }
        }

        return img;
    }
}




