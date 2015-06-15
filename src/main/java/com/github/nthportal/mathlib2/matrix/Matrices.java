package com.github.nthportal.mathlib2.matrix;

public class Matrices {
    public static boolean equals(Matrix m1, Matrix m2) {
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

    public static SquareMatrix identity(int size) {
        return new IdentityMatrix(size);
    }

    public static Matrix zeroMatrix(int rows, int cols) {
        return new ZeroMatrix(rows, cols);
    }

    private static class IdentityMatrix implements SquareMatrix {
        private final int size;

        private IdentityMatrix(int size) throws IllegalArgumentException {
            if (size <= 0) {
                throw new IllegalArgumentException("Matrix dimensions must be positive");
            }
            this.size = size;
        }

        private DefaultSquareMatrix toSquareMatrix() {
            int[][] array = new int[size][size];
            for (int i = 0; i < size; i++) {
                array[i][i] = 1;
            }
            return DefaultSquareMatrix.fromMatrix(new DefaultMatrix(array));
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
        public Matrix add(Matrix m) throws MatrixSizeException {
            Checks.addSubtractCheck(this, m, true);
            return m.add(this);
        }

        @Override
        public DefaultSquareMatrix subtract(Matrix m) throws MatrixSizeException {
            Checks.addSubtractCheck(this, m, false);
            return toSquareMatrix().subtract(m);
        }

        @Override
        public Matrix multiply(int scalar) {
            return (scalar == 1) ? this : toSquareMatrix().multiply(scalar);
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

        private ZeroMatrix(int rows, int cols) {
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
}
