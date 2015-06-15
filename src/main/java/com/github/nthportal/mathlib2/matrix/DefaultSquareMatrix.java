package com.github.nthportal.mathlib2.matrix;

public class DefaultSquareMatrix implements SquareMatrix {
    private final Matrix underlyingMatrix;

    private DefaultSquareMatrix(Matrix matrix) {
        underlyingMatrix = matrix;
    }

    public static DefaultSquareMatrix fromMatrix(Matrix matrix) throws MatrixSizeException {
        if (!matrix.isSquare()) {
            throw new MatrixSizeException("A square matrix must have the same number of rows and columns");
        }
        return new DefaultSquareMatrix(matrix);
    }

    public static DefaultSquareMatrix create(int[][] array) throws IllegalArgumentException, MatrixSizeException {
        return DefaultMatrix.create(array).asSquareMatrix();
    }

    public int size() {
        return underlyingMatrix.rows();
    }

    public Matrix asMatrix() {
        return underlyingMatrix;
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
    public int get(int row, int col) throws MatrixBoundsException {
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

    @Override
    public boolean isVector() {
        return underlyingMatrix.isVector();
    }

    @Override
    public boolean isSquare() {
        return true;
    }
}
