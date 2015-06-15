package com.github.nthportal.mathlib2.matrix;

public class MatrixBuilder {
    public final int rows;
    public final int cols;
    private int[][] array;
    private boolean expired = false;

    public MatrixBuilder(int rows, int cols) throws IllegalArgumentException {
        Matrices.Checks.constructCheck(rows, cols);
        this.rows = rows;
        this.cols = cols;
        array = new int[rows][cols];
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

    public DefaultMatrix create() {
        DefaultMatrix matrix = new DefaultMatrix(array);
        expired = true;
        array = null;
        return matrix;
    }

    public static VectorBuilder newVectorBuilder(int size) throws IllegalArgumentException {
        return new VectorBuilder(new MatrixBuilder(size, 1));
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

        public DefaultVector create() {
            return DefaultVector.fromMatrix(underlyingBuilder.create());
        }
    }
}
