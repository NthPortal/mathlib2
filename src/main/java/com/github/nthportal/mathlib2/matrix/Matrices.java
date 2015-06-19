package com.github.nthportal.mathlib2.matrix;

import java.util.Arrays;

public class Matrices {
    static boolean equals(Matrix m1, Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == m1) {
            return true;
        }
        if (!(obj instanceof Matrix)) {
            return false;
        }
        Matrix m2 = (Matrix) obj;
        return equalsInternal(m1, m2);
    }

    public static boolean equals(Matrix m1, Matrix m2) {
        // Separate statements for clarity
        if (m1 == m2) {
            return true;
        }
        if ((m1 == null) || (m2 == null)) {
            return false;
        }
        return equalsInternal(m1, m2);
    }

    private static boolean equalsInternal(Matrix m1, Matrix m2) {
        if ((m1.rows() != m2.rows()) || (m1.columns() != m2.columns())) {
            return false;
        }
        for (int i = 0; i < m1.rows(); i++) {
            for (int j = 0; j < m1.columns(); j++) {
                if (m1.get(i, j) != m2.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Matrix fromArray(int[][] array) throws IllegalArgumentException {
        if ((array == null) || (array.length == 0)) {
            throw new IllegalArgumentException("Array from which to construct matrix cannot be null or of 0 length");
        }
        for (int[] row : array) {
            if ((row == null) || row.length == 0) {
                throw new IllegalArgumentException("Array from which to construct matrix cannot contain null row or row of length 0");
            }
        }

        return new DefaultMatrix(Matrices.Util.getCopy(array));
    }

    public static SquareMatrix identity(int size) {
        return new IdentityMatrix(size);
    }

    public static Matrix zeroMatrix(int rows, int cols) {
        return new ZeroMatrix(rows, cols);
    }

    public static class Square {
        public static SquareMatrix fromMatrix(Matrix matrix) throws MatrixSizeException {
            if (!matrix.isSquare()) {
                throw new MatrixSizeException("A square matrix must have the same number of rows and columns");
            } else if (matrix instanceof SquareMatrix) {
                return (SquareMatrix) matrix;
            }
            return new DefaultSquareMatrix(matrix);
        }
    }

    public static class Vectors {
        public static Vector fromArray2d(int[][] array) throws IllegalArgumentException, MatrixSizeException {
            return fromMatrix(fromArray(array));
        }

        public static Vector fromArray1d(int[] array) throws IllegalArgumentException {
            MatrixBuilder.VectorBuilder builder = MatrixBuilder.vectorBuilder(array.length);
            for (int i = 0; i < array.length; i++) {
                builder.withValue(i, array[i]);
            }
            return builder.create();
        }

        public static Vector fromMatrix(Matrix matrix) throws MatrixSizeException {
            if (!matrix.isVector()) {
                throw new MatrixSizeException("A matrix is only a vector if it has exactly 1 column");
            } else if (matrix instanceof  Vector) {
                return (Vector) matrix;
            }
            return new DefaultVector(matrix);
        }
    }

    private static class IdentityMatrix implements SquareMatrix {
        private final int size;

        private IdentityMatrix(int size) throws IllegalArgumentException {
            Checks.constructCheck(size, size);
            this.size = size;
        }

        private SquareMatrix toSquareMatrix() {
            int[][] array = new int[size][size];
            for (int i = 0; i < size; i++) {
                array[i][i] = 1;
            }
            return Square.fromMatrix(new DefaultMatrix(array));
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public int rows() {
            return size;
        }

        @Override
        public int columns() {
            return size;
        }

        @Override
        public int get(int row, int col) throws MatrixIndexOutOfBoundsException {
            Checks.getCheck(this, row, col);
            return (row == col) ? 1 : 0;
        }

        @Override
        public SquareMatrix add(Matrix m) throws MatrixSizeException {
            Checks.addSubtractCheck(this, m, true);
            return Square.fromMatrix(m.add(this));
        }

        @Override
        public SquareMatrix subtract(Matrix m) throws MatrixSizeException {
            Checks.addSubtractCheck(this, m, false);
            return toSquareMatrix().subtract(m);
        }

        @Override
        public SquareMatrix multiply(int scalar) {
            return    (scalar == 1) ? this
                    : (scalar == 0) ? Square.fromMatrix(zeroMatrix(size, size))
                    : toSquareMatrix().multiply(scalar);
        }

        @Override
        public Matrix multiply(Matrix m) throws MatrixSizeException {
            Checks.multiplyCheck(this, m);
            return m;
        }

        @Override
        public IdentityMatrix transpose() {
            return this;
        }
    }

    private static class ZeroMatrix implements Matrix {
        private final int rows;
        private final int cols;

        private ZeroMatrix(int rows, int cols) throws IllegalArgumentException {
            Checks.constructCheck(rows, cols);
            this.rows = rows;
            this.cols = cols;
        }

        @Override
        public int rows() {
            return rows;
        }

        @Override
        public int columns() {
            return cols;
        }

        @Override
        public int get(int row, int col) throws MatrixIndexOutOfBoundsException {
            Checks.getCheck(this, row, col);
            return 0;
        }

        @Override
        public Matrix add(Matrix m) throws MatrixSizeException {
            Checks.addSubtractCheck(this, m, true);
            return m;
        }

        @Override
        public Matrix subtract(Matrix m) throws MatrixSizeException {
            Checks.addSubtractCheck(this, m, false);
            return m.multiply(-1);
        }

        @Override
        public Matrix multiply(int scalar) {
            return this;
        }

        @Override
        public Matrix multiply(Matrix m) throws MatrixSizeException {
            Checks.multiplyCheck(this, m);
            return new ZeroMatrix(rows, m.columns());
        }

        @Override
        public Matrix transpose() {
            return new ZeroMatrix(cols, rows);
        }
    }

    static class Checks {
        static void constructCheck(int rows, int cols) throws IllegalArgumentException {
            if ((rows <= 0) || cols <= 0) {
                throw new IllegalArgumentException("Matrix dimensions must be positive");
            }
        }

        static void addSubtractCheck(Matrix m1, Matrix m2, boolean add) throws MatrixSizeException {
            String op = add ? "add" : "subtract";
            if (m2 == null) {
                throw new NullPointerException("Cannot " + op + " a null matrix");
            } else if ((m1.rows() != m2.rows()) || (m1.columns() != m2.columns())) {
                throw new MatrixSizeException("Cannot " + op + " matrices of different dimensions");
            }
        }

        static void multiplyCheck(Matrix m1, Matrix m2) throws MatrixSizeException {
            if (m2 == null) {
                throw new NullPointerException("Cannot multiply by a null matrix");
            } else if (m1.columns() != m2.rows()) {
                throw new MatrixSizeException("Cannot multiply by a matrix with a different number of rows than this has columns");
            }
        }

        static void getCheck(Matrix m, int row, int col) throws MatrixIndexOutOfBoundsException {
            if ((row < 0) || (row >= m.rows()) || (col < 0) || (col >= m.columns())) {
                throw new MatrixIndexOutOfBoundsException("Index is out of bounds of the matrix");
            }
        }
    }

    static class Util {
        static int[][] getCopy(int[][] array) {
            int rows = array.length;
            int[][] copy = new int[rows][array[0].length];
            for (int i = 0; i < rows; i++) {
                copy[i] = Arrays.copyOf(array[i], rows);
            }
            return copy;
        }
    }
}
