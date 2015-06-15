package com.github.nthportal.mathlib2.matrix;

public class MatrixBuilder {
    public final int rows;
    public final int cols;
    private int[][] array;
    private boolean expired = false;

    public MatrixBuilder(int rows, int cols) throws IllegalArgumentException {
        if ((rows <= 0) || cols <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive");
        }

        this.rows = rows;
        this.cols = cols;
        array = new int[rows][cols];
    }

    public MatrixBuilder withValue(int row, int col, int value) throws MatrixBoundsException {
        if (expired) {
            array = new int[rows][cols];
            expired = false;
        }
        if ((row < 0) || (row >= rows) || (col < 0) || (col >= cols)) {
            throw new MatrixBoundsException("Value must be within bounds of matrix");
        }
        array[row][col] = value;
        return this;
    }

    public Matrix create() {
        Matrix matrix = new Matrix(array);
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

        public VectorBuilder withValue(int index, int value) throws MatrixBoundsException {
            underlyingBuilder.withValue(index, 1, value);
            return this;
        }

        public Matrix.Vector create() {
            return underlyingBuilder.create().asVector();
        }
    }
}
