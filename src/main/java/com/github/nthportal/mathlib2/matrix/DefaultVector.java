package com.github.nthportal.mathlib2.matrix;

public class DefaultVector implements Vector {
    private final Matrix underlyingMatrix;

    private DefaultVector(Matrix matrix) {
        underlyingMatrix = matrix;
    }

    public static DefaultVector fromMatrix(Matrix matrix) throws MatrixSizeException {
        if (!matrix.isVector()) {
            throw new MatrixSizeException("A matrix is only a vector if it has exactly 1 column");
        } else if (matrix instanceof  DefaultVector) {
            return (DefaultVector) matrix;
        }
        return new DefaultVector(matrix);
    }

    public static DefaultVector create(int[][] array) throws IllegalArgumentException, MatrixSizeException {
        return new DefaultVector(DefaultMatrix.create(array));
    }

    public static DefaultVector create(int[] array) throws IllegalArgumentException {
        MatrixBuilder.VectorBuilder builder = MatrixBuilder.newVectorBuilder(array.length);
        for (int i = 0; i < array.length; i++) {
            builder.withValue(i, array[i]);
        }
        return builder.create();
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

    @Override
    public boolean isSquare() {
        return underlyingMatrix.isSquare();
    }
}
