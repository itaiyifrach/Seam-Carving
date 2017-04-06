import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Number of passed arguments is not valid");
            return;
        }

        // getting all parameters
        String inputImageFilename = args[1];
        int cols = Integer.parseInt(args[2]);
        int rows = Integer.parseInt(args[3]);
        int energyType = Integer.parseInt(args[4]);
        String outputImageFilename = args[5];

        try
        {
            // read the image file
            BufferedImage image = ImageIO.read(new File(inputImageFilename));

        }
        catch (IOException e)
        {
            // log the exception
            // re-throw if desired
        }

    }
}
