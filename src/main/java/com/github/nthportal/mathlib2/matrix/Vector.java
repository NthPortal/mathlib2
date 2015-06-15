package com.github.nthportal.mathlib2.matrix;

public interface Vector extends Matrix {
    default int size() {
        return rows();
    }

    default int get(int index) throws MatrixIndexOutOfBoundsException {
        return get(index, 0);
    }

    @Override
    Vector add(Matrix m) throws MatrixSizeException;

    @Override
    Vector subtract(Matrix m) throws MatrixSizeException;

    @Override
    Vector multiply(int scalar);

    @Override
    default boolean isVector() {
        return true;
    }
}
