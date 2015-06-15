package com.github.nthportal.mathlib2.matrix;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

public final class DefaultMatrix implements Matrix {
    private final int rows;
    private final int cols;
    private final int[][] array;

    DefaultMatrix(int[][] array) {
        this.array = array;
        rows = array.length;
        cols = array[0].length;
    }

    public static DefaultMatrix create(int[][] array) throws IllegalArgumentException {
        if ((array == null) || (array.length == 0)) {
            throw new IllegalArgumentException("Array from which to construct matrix cannot be null or of 0 length");
        }
        for (int[] row : array) {
            if ((row == null) || row.length == 0) {
                throw new IllegalArgumentException("Array from which to construct matrix cannot contain null row or row of length 0");
            }
        }

        return new DefaultMatrix(getCopy(array));
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return cols;
    }

    @Override
    public int get(int row, int col) throws MatrixBoundsException {
        if ((row < 0) || (row >= rows) || (col < 0) || (col >= cols)) {
            throw new MatrixBoundsException("Index is out of bounds of the matrix");
        }
        return array[row][col];
    }

    @Override
    public DefaultMatrix add(Matrix m) throws MatrixSizeException {
        if (m == null) {
            throw new NullPointerException("Cannot add a null matrix");
        } else if ((rows != m.rows()) || (cols != m.columns())) {
            throw new MatrixSizeException("Cannot add matrices of different dimensions");
        }
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] + m.get(i, j);
            }
        }
        return new DefaultMatrix(result);
    }

    @Override
    public DefaultMatrix subtract(Matrix m) throws MatrixSizeException {
        if (m == null) {
            throw new NullPointerException("Cannot add a null matrix");
        } else if ((rows != m.rows()) || (cols != m.columns())) {
            throw new MatrixSizeException("Cannot subtract matrices of different dimensions");
        }
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] - m.get(i, j);
            }
        }
        return new DefaultMatrix(result);
    }

    @Override
    public DefaultMatrix multiply(int scalar) {
        int[][] result = getCopy(array);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] * scalar;
            }
        }
        return new DefaultMatrix(result);
    }

    @Override
    public DefaultMatrix multiply(Matrix m) throws MatrixSizeException {
        if (m == null) {
            throw new NullPointerException("Cannot add a null matrix");
        } else if (cols != m.rows()) {
            throw new MatrixSizeException("Cannot multiply by a matrix with a different number of rows than this has columns");
        }
        int[][] result = new int[rows][m.columns()];
        for (int rowL = 0; rowL < rows; rowL++) {
            for (int colR = 0; colR < m.columns(); colR++) {
                for (int colLRowR = 0; colLRowR < cols; colLRowR++) {
                    result[rowL][colR] += array[rowL][colLRowR] * m.get(colLRowR, colR);
                }
            }
        }
        return new DefaultMatrix(result);
    }

    @Override
    public DefaultMatrix transpose() {
        int[][] result = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = array[i][j];
            }
        }
        return new DefaultMatrix(result);
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

    // TODO maybe change?
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultMatrix)) {
            return false;
        }
        DefaultMatrix rhs = (DefaultMatrix) obj;
        EqualsBuilder builder = new EqualsBuilder()
                .append(rows, rhs.rows)
                .append(cols, rhs.cols);
        for (int i = 0; i < rows; i++) {
            builder.append(array[i], rhs.array[i]);
        }
        return builder.isEquals();
    }

    @Override
    public boolean isVector() {
        return (cols == 1);
    }

    public Vector asVector() throws MatrixSizeException {
        return new Vector(this);
    }

    @Override
    public boolean isSquare() {
        return (rows == cols);
    }

    public DefaultSquareMatrix asSquareMatrix() throws MatrixSizeException {
        return new DefaultSquareMatrix(this);
    }

    private static int[][] getCopy(int[][] array) {
        int rows = array.length;
        int[][] copy = new int[rows][array[0].length];
        for (int i = 0; i < rows; i++) {
            copy[i] = Arrays.copyOf(array[i], rows);
        }
        return copy;
    }

}
