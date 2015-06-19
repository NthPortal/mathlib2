package com.github.nthportal.mathlib2.matrix;

class DefaultVector implements Vector {
    private final Matrix underlyingMatrix;

    DefaultVector(Matrix matrix) {
        underlyingMatrix = matrix;
    }

    @Override
    public int rows() {
        return underlyingMatrix.rows();
    }

    @Override
    public int columns() {
        return 1;
    }

    @Override
    public int get(int row, int col) throws MatrixIndexOutOfBoundsException {
        return underlyingMatrix.get(row, col);
    }

    @Override
    public DefaultVector add(Matrix m) throws MatrixSizeException {
        return new DefaultVector(underlyingMatrix.add(m));
    }

    @Override
    public DefaultVector subtract(Matrix m) throws MatrixSizeException {
        return new DefaultVector(underlyingMatrix.subtract(m));
    }

    @Override
    public DefaultVector multiply(int scalar) {
        return new DefaultVector(underlyingMatrix.multiply(scalar));
    }

    @Override
    public Matrix multiply(Matrix m) throws MatrixSizeException {
        return underlyingMatrix.multiply(m);
    }

    @Override
    public Matrix transpose() {
        return underlyingMatrix.transpose();
    }
}
