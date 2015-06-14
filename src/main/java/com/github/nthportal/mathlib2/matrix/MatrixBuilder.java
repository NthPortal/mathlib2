package com.github.nthportal.mathlib2.matrix;

public class MatrixBuilder {
    private final int rows;
    private final int cols;
    private final int[][] array;

    public MatrixBuilder(int rows, int cols) throws IllegalArgumentException {
        if ((rows <= 0) || cols <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive");
        }

        this.rows = rows;
        this.cols = cols;
        array = new int[rows][cols];
    }

    public MatrixBuilder withValue(int row, int col, int value) throws MatrixBoundsException {
        if ((row < 0) || (row >= rows) || (col < 0) || (col >= cols)) {
            throw new MatrixBoundsException("Value must be within bounds of matrix");
        }
        array[row][col] = value;
        return this;
    }

    public Matrix create() {
        return new Matrix(array);
    }
}
