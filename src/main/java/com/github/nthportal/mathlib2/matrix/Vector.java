package com.github.nthportal.mathlib2.matrix;

public class Vector implements Matrix {
    private final Matrix underlyingMatrix;

    private Vector(Matrix matrix) {
        underlyingMatrix = matrix;
    }

    public static Vector fromMatrix(Matrix matrix) throws MatrixSizeException {
        if (!matrix.isVector()) {
            throw new MatrixSizeException("A matrix is only a vector if it has exactly 1 column");
        }
        return new Vector(matrix);
    }

    public static Vector create(int[][] array) throws IllegalArgumentException, MatrixSizeException {
        return new Vector(DefaultMatrix.create(array));
    }

    public static Vector create(int[] array) throws IllegalArgumentException {
        MatrixBuilder.VectorBuilder builder = MatrixBuilder.newVectorBuilder(array.length);
        for (int i = 0; i < array.length; i++) {
            builder.withValue(i, array[i]);
        }
        return builder.create();
    }

    public int size() {
        return underlyingMatrix.rows();
    }

    public int get(int index) throws MatrixBoundsException {
        return underlyingMatrix.get(index, 1);
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
        return 1;
    }

    @Override
    public int get(int row, int col) throws MatrixBoundsException {
        return underlyingMatrix.get(row, col);
    }

    @Override
    public Vector add(Matrix m) throws MatrixSizeException {
        return new Vector(underlyingMatrix.add(m));
    }

    @Override
    public Vector subtract(Matrix m) throws MatrixSizeException {
        return new Vector(underlyingMatrix.subtract(m));
    }

    @Override
    public Vector multiply(int scalar) {
        return new Vector(underlyingMatrix.multiply(scalar));
    }

    @Override
    public Matrix multiply(Matrix m) throws MatrixSizeException {
        return underlyingMatrix.multiply(m);
    }

    @Override
    public Matrix transpose() {
        return underlyingMatrix.transpose();
    }

    @Override
    public boolean isVector() {
        return true;
    }

    @Override
    public boolean isSquare() {
        return underlyingMatrix.isSquare();
    }
}
