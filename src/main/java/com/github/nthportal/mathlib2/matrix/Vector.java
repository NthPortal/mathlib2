package com.github.nthportal.mathlib2.matrix;

public interface Vector extends Matrix {
    default int size() {
        return rows();
    }

    default int get(int index) throws MatrixBoundsException {
        return get(index, 1);
    }

    @Override
    default boolean isVector() {
        return true;
    }
}
