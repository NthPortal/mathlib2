package com.github.nthportal.mathlib2.matrix;

public interface SquareMatrix extends Matrix {
    default int size() {
        return rows();
    }

    @Override
    SquareMatrix add(Matrix m) throws MatrixSizeException;

    @Override
    SquareMatrix subtract(Matrix m) throws MatrixSizeException;

    @Override
    SquareMatrix multiply(int scalar);

    @Override
    SquareMatrix transpose();

    @Override
    default boolean isSquare() {
        return true;
    }
}
