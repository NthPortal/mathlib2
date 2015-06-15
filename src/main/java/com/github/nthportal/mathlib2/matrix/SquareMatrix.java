package com.github.nthportal.mathlib2.matrix;

public interface SquareMatrix extends Matrix {
    default int size() {
        return rows();
    }

    @Override
    default boolean isSquare() {
        return true;
    }
}
