package com.github.nthportal.mathlib2.matrix;

public class MatrixBuilder {
    public final int rows;
    public final int cols;
    private int[][] array;
    private boolean expired = false;

    private MatrixBuilder(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        array = new int[rows][cols];
    }

    public static MatrixBuilder newBuilder(int rows, int cols) throws IllegalArgumentException {
        Matrices.Checks.constructCheck(rows, cols);
        return new MatrixBuilder(rows, cols);
    }


    public static SquareMatrixBuilder squareBuilder(int size) throws IllegalArgumentException {
        Matrices.Checks.constructCheck(size, size);
        return new SquareMatrixBuilder(size);
    }

    public static VectorBuilder vectorBuilder(int size) throws IllegalArgumentException {
        Matrices.Checks.constructCheck(size, size);
        return new VectorBuilder(new MatrixBuilder(size, size));
    }

    public MatrixBuilder withValue(int row, int col, int value) throws MatrixIndexOutOfBoundsException {
        if (expired) {
            array = new int[rows][cols];
            expired = false;
        }
        if ((row < 0) || (row >= rows) || (col < 0) || (col >= cols)) {
            throw new MatrixIndexOutOfBoundsException("Value must be within bounds of matrix");
        }
        array[row][col] = value;
        return this;
    }

    public Matrix create() {
        DefaultMatrix matrix = new DefaultMatrix(array);
        expired = true;
        array = null;
        return matrix;
    }

    public static class SquareMatrixBuilder extends MatrixBuilder {
        private SquareMatrixBuilder(int size) {
            super(size, size);
        }

        @Override
        public SquareMatrix create() {
            return Matrices.Square.fromMatrix(super.create());
        }
    }

    public static class VectorBuilder {
        private final MatrixBuilder underlyingBuilder;

        private VectorBuilder(MatrixBuilder matrixBuilder) {
            underlyingBuilder = matrixBuilder;
        }

        public VectorBuilder withValue(int index, int value) throws MatrixIndexOutOfBoundsException {
            underlyingBuilder.withValue(index, 0, value);
            return this;
        }

        public Vector create() {
            return Matrices.Vectors.fromMatrix(underlyingBuilder.create());
        }
    }
}
