import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private  int N;             // number of rows
    private  int M;             // number of columns
    private  double[][] data;   // M-by-N array

    //private EnergyData[][] data;

    // create N-by-M matrix of 0's
    public Matrix(int N, int M) {
        this.N = N;
        this.M = M;
        this.data = new double[N][M];
    }

    public Matrix(){}
    // create matrix based on 2d array

    public Matrix(double[][] _data, boolean useExisting) {

        N = data.length;
        M = data[0].length;

        if (useExisting) {
            this.data = _data;
        } else {
            this.data = new double[N][M];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < M; j++)
                    this.data[i][j] = data[i][j];
        }
    }

    // copy constructor
    private Matrix(Matrix A) {
        this(A.data, false);
    }

    public double get(int i, int j) {
        return this.data[i][j];
    }

    public void set(int i, int j, double value) {
        this.data[i][j] = value;
    }

    public int getN() {
        return this.N;
    }

    public int getM() {
        return this.M;
    }

    public double[] getRow(int n) {
        return this.data[n];
    }

    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.N != A.N || B.M != A.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(N, M);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }

    // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.N != A.N || B.M != A.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(N, M);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    // print matrix to standard output
    public void show() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
        }
    }


    public static Matrix getRGBMatrix(BufferedImage image) {
        int n = image.getHeight();  // #rows
        int m = image.getWidth();   // #cols

        double[][] data = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                data[i][j] = image.getRGB(j,i);

        return new Matrix(data,true);
    }

    // returns int RGB array of size 3 = {red, blue, green}
    private int[] getRGBArray(int pixel) {
        int[] rgb = new int[3];
        rgb[0] = (pixel >> 16) & 0xff;
        rgb[0] = (pixel >> 8) & 0xff;
        rgb[0] = (pixel) & 0xff;

        return rgb;
    }

    public Matrix getEnergyMatrix(BufferedImage image, int type) {
        Matrix rgbMatrix = getRGBMatrix(image);
        Matrix energyMatrix = getRegularEnergyMatrix(rgbMatrix);

        if (type == 0) // regular energy without entropy term
            return energyMatrix;
        if (type == 1) { // regular energy with entropy term
            return energyMatrix.plus(getEntropyMatrix(rgbMatrix));
        }
        if (type == 2) {
            // TODO
        }
        return energyMatrix;
    }


    // returns indexes list of (i,j) neighbors
    private List<int[]> getNeighbors(Matrix mat, int i, int j) {
        List<int[]> neighbors = new ArrayList<>();
        int neighborRowLimit = Math.min(mat.getN(), i + 2);
        int neighborColumnLimit = Math.min(mat.getM(), j + 2);

        for (int neighborRow = Math.max(0, i - 1); neighborRow < neighborRowLimit; neighborRow++) {
            for (int neighborColumn = Math.max(0, j - 1); neighborColumn < neighborColumnLimit; neighborColumn++) {
                if (neighborRow != i || neighborColumn != j) {
                    neighbors.add(new int[]{neighborRow, neighborColumn});
                }
            }
        }
        return neighbors;
    }

    // regular energy matrix without entropy
    private Matrix getRegularEnergyMatrix(Matrix rgbMatrix) {
        int n = rgbMatrix.getN();  // #rows
        int m = rgbMatrix.getM();   // #cols
        double neighborVal, pixelVal, sum;
        int[] neighborRGB;
        int[] pixelRGB;
        List<int[]> neighbors;

        double[][] data = new double[n][m];

        // getting the energy values of each pixel

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sum = 0;
                neighbors = getNeighbors(rgbMatrix, i, j); // getting list of (i,j) neighbors values
                pixelRGB = getRGBArray((int)rgbMatrix.get(i, j)); // get the rgb vals from the pixel
                for (int[] neighbor : neighbors) {    // iterating over all (i,j) neighbors to calc his value
                    pixelVal = rgbMatrix.get(neighbor[0], neighbor[1]);   // get the neighbor pixel value
                    neighborRGB = getRGBArray((int) pixelVal); // get the rgb vals from the neighbor pixel
                    neighborVal = (Math.abs(pixelRGB[0] - neighborRGB[0]) + Math.abs(pixelRGB[1] - neighborRGB[1]) + Math.abs(pixelRGB[2] - neighborRGB[2])) / 3;
                    sum += neighborVal;
                }
                data[i][j] = sum / neighbors.size();    // setting (i,j) value
            }
        }

        return new Matrix(data, true);
    }

    private Matrix getGreyscaleMatrix(Matrix rgbMatrix) {
        int n = rgbMatrix.getN();  // #rows
        int m = rgbMatrix.getM();   // #cols

        double[][] data = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                data[i][j] = getGreyscale(getRGBArray((int)rgbMatrix.get(i,j)));

        return new Matrix(data, true);
    }

    // returns greyscale value of pixel
    private double getGreyscale(int[] rgb) {
        double grey;
        grey = (rgb[0] + rgb[1] + rgb[2]) / 3;

        return grey;
    }

    // entropy matrix
    private Matrix getEntropyMatrix(Matrix rgbMatrix) {
        int n = rgbMatrix.getN();  // #rows
        int m = rgbMatrix.getM();   // #cols
        Matrix greyscaleMatrix = getGreyscaleMatrix(rgbMatrix);

        double[][] data = new double[n][m];

        // getting the entropy values of each pixel
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                data[i][j] = getEntropyValue(rgbMatrix, greyscaleMatrix, i, j);    // setting (i,j) value

        return new Matrix(data, true);
    }

    // returns the entropy pixel value of (i,j)
    private double getEntropyValue(Matrix rgbMatrix, Matrix greyscaleMatrix, int i, int j) {
        List<int[]> pixelNeighbors, pNeighbors;
        double sum=0, p, grey, greySum=0;
        int p_m, p_n;

        pixelNeighbors = getNeighbors(greyscaleMatrix, i, j);

        for (int k = 0; k < pixelNeighbors.size(); k++) {
            p_m = pixelNeighbors.get(k)[0];
            p_n = pixelNeighbors.get(k)[1];
            pNeighbors = getNeighbors(rgbMatrix, p_m, p_n); // get the neighbors of P(mn)
            grey = getGreyscale(getRGBArray((int)rgbMatrix.get(p_m, p_n))); // grey value of P(mn)
            for (int l = 0; l < pNeighbors.size(); l++) {
                greySum += getGreyscale(getRGBArray((int)rgbMatrix.get(pNeighbors.get(k)[0], pNeighbors.get(k)[1])));   // sum P(mn) neighbors grey vals
            }
            p = grey / greySum; // calc P(mn) value
            sum += p*Math.log(p); // sum all p values
        }

        return sum*(-1);
    }

    public interface matListener{
        Matrix getEnergyMatrix(BufferedImage image, int type);
    }

}
