package graphics.hw1;

final public class Matrix {
    private final int M;             // number of rows
    private final int N;             // number of columns
    private final double[][] data;   // M-by-N array

    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    // create matrix based on 2d array
    public Matrix(double[][] data, boolean useExisting) {
        M = data.length;
        N = data[0].length;
        if (useExisting) {
            this.data = data;
        } else {
            this.data = new double[M][N];
            for (int i = 0; i < M; i++)
                System.arraycopy(data[i], 0, this.data[i], 0, N);
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
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }

    // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    // print matrix to standard output
    public void show() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++)
                System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
        }
    }
}
