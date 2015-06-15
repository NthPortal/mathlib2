package com.github.nthportal.mathlib2.matrix;

public interface Matrix {
    int rows();

    int columns();

    int get(int row, int col) throws MatrixBoundsException;

    Matrix add(Matrix m) throws MatrixSizeException;

    Matrix subtract(Matrix m) throws MatrixSizeException;

    Matrix multiply(int scalar);

    Matrix multiply(Matrix m) throws MatrixSizeException;

    Matrix transpose();

    boolean isVector();

    boolean isSquare();
}
