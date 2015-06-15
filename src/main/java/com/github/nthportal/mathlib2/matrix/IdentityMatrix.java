package com.github.nthportal.mathlib2.matrix;

public class IdentityMatrix implements SquareMatrix {
    private final int size;

    public IdentityMatrix(int size) throws IllegalArgumentException {
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
        return new DefaultMatrix(array).asSquareMatrix();
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
    public int get(int row, int col) throws MatrixBoundsException {
        return (row == col) ? 1 : 0;
    }

    @Override
    public Matrix add(Matrix m) throws MatrixSizeException {
        return m.add(this);
    }

    @Override
    public DefaultSquareMatrix subtract(Matrix m) throws MatrixSizeException {
        return toSquareMatrix().subtract(m);
    }

    @Override
    public Matrix multiply(int scalar) {
        return (scalar == 1) ? this : toSquareMatrix().multiply(scalar);
    }

    @Override
    public Matrix multiply(Matrix m) throws MatrixSizeException {
        return m;
    }

    @Override
    public IdentityMatrix transpose() {
        return this;
    }

    @Override
    public boolean isVector() {
        return (size == 1);
    }

    @Override
    public boolean isSquare() {
        return true;
    }
}
