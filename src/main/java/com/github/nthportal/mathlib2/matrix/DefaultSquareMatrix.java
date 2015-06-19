package com.github.nthportal.mathlib2.matrix;

class DefaultSquareMatrix implements SquareMatrix {
    private final Matrix underlyingMatrix;

    DefaultSquareMatrix(Matrix matrix) {
        underlyingMatrix = matrix;
    }

    @Override
    public int rows() {
        return underlyingMatrix.rows();
    }

    @Override
    public int columns() {
        return underlyingMatrix.columns();
    }

    @Override
    public int get(int row, int col) throws MatrixIndexOutOfBoundsException {
        return underlyingMatrix.get(row, col);
    }

    @Override
    public DefaultSquareMatrix add(Matrix m) throws MatrixSizeException {
        return new DefaultSquareMatrix(underlyingMatrix.add(m));
    }

    @Override
    public DefaultSquareMatrix subtract(Matrix m) throws MatrixSizeException {
        return new DefaultSquareMatrix(underlyingMatrix.subtract(m));
    }

    @Override
    public DefaultSquareMatrix multiply(int scalar) {
        return new DefaultSquareMatrix(underlyingMatrix.multiply(scalar));
    }

    @Override
    public Matrix multiply(Matrix m) throws MatrixSizeException {
        return underlyingMatrix.multiply(m);
    }

    @Override
    public DefaultSquareMatrix transpose() {
        return new DefaultSquareMatrix(underlyingMatrix.transpose());
    }
}
