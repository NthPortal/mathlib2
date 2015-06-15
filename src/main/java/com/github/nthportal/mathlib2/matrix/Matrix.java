package com.github.nthportal.mathlib2.matrix;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

public final class Matrix {
    public final int rows;
    public final int cols;
    private final int[][] array;

    Matrix(int[][] array) {
        this.array = array;
        rows = array.length;
        cols = array[0].length;
    }

    public static Matrix create(int[][] array) throws IllegalArgumentException {
        if ((array == null) || (array.length == 0)) {
            throw new IllegalArgumentException("Array from which to construct matrix cannot be null or of 0 length");
        }
        for (int[] row : array) {
            if ((row == null) || row.length == 0) {
                throw new IllegalArgumentException("Array from which to construct matrix cannot contain null row or row of length 0");
            }
        }

        return new Matrix(getCopy(array));
    }

    public int get(int row, int col) throws MatrixBoundsException {
        if ((row < 0) || (row >= rows) || (col < 0) || (col >= cols)) {
            throw new MatrixBoundsException("Index is out of bounds of the matrix");
        }
        return array[row][col];
    }

    public Matrix add(Matrix m) throws MatrixSizeException {
        if (m == null) {
            throw new NullPointerException("Cannot add a null matrix");
        } else if ((rows != m.rows) || (cols != m.cols)) {
            throw new MatrixSizeException("Cannot add matrices of different dimensions");
        }
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] + m.array[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix subtract(Matrix m) throws MatrixSizeException {
        if (m == null) {
            throw new NullPointerException("Cannot add a null matrix");
        } else if ((rows != m.rows) || (cols != m.cols)) {
            throw new MatrixSizeException("Cannot subtract matrices of different dimensions");
        }
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] - m.array[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix multiply(int scalar) {
        int[][] result = getCopy(array);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] * scalar;
            }
        }
        return new Matrix(result);
    }

    public Matrix multiply(Matrix m) throws MatrixSizeException {
        if (m == null) {
            throw new NullPointerException("Cannot add a null matrix");
        } else if (cols != m.rows) {
            throw new MatrixSizeException("Cannot multiply by a matrix with a different number of rows than this has columns");
        }
        int[][] result = new int[rows][m.cols];
        for (int rowL = 0; rowL < rows; rowL++) {
            for (int colR = 0; colR < m.cols; colR++) {
                for (int colLRowR = 0; colLRowR < cols; colLRowR++) {
                    result[rowL][colR] += array[rowL][colLRowR] * m.array[colLRowR][colR];
                }
            }
        }
        return new Matrix(result);
    }

    public Matrix transpose() {
        int[][] result = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = array[i][j];
            }
        }
        return new Matrix(result);
    }

    public static Matrix identity(int size) throws IllegalArgumentException {
        if (size <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive");
        }
        int[][] array = new int[size][size];
        for (int i = 0; i < size; i++) {
            array[i][i] = 1;
        }
        return new Matrix(array);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder()
                .append(rows)
                .append(cols);
        for (int i = 0; i < rows; i++) {
            builder.append(array[i]);
        }
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Matrix)) {
            return false;
        }
        Matrix rhs = (Matrix) obj;
        EqualsBuilder builder = new EqualsBuilder()
                .append(rows, rhs.rows)
                .append(cols, rhs.cols);
        for (int i = 0; i < rows; i++) {
            builder.append(array[i], rhs.array[i]);
        }
        return builder.isEquals();
    }

    public boolean isVector() {
        return (cols == 1);
    }

    public Vector asVector() throws MatrixSizeException {
        return new Vector(this);
    }

    private static int[][] getCopy(int[][] array) {
        int rows = array.length;
        int[][] copy = new int[rows][array[0].length];
        for (int i = 0; i < rows; i++) {
            copy[i] = Arrays.copyOf(array[i], rows);
        }
        return copy;
    }

    public static class Vector {
        private final Matrix underlyingMatrix;

        private Vector(Matrix matrix) throws MatrixSizeException {
            if (!matrix.isVector()) {
                throw new MatrixSizeException("A matrix is only a vector if it has exactly 1 column");
            }
            underlyingMatrix = matrix;
        }

        public static Vector create(int[][] array) throws IllegalArgumentException, MatrixSizeException {
            return Matrix.create(array).asVector();
        }

        public int size() {
            return underlyingMatrix.rows;
        }

        public Matrix asMatrix() {
            return underlyingMatrix;
        }
    }
}
