package com.github.nthportal.mathlib2.matrix;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

class DefaultMatrix implements Matrix {
    private final int rows;
    private final int cols;
    private final int[][] array;

    DefaultMatrix(int[][] array) {
        this.array = array;
        rows = array.length;
        cols = array[0].length;
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
    public int get(int row, int col) throws MatrixIndexOutOfBoundsException {
        Matrices.Checks.getCheck(this, row, col);
        return array[row][col];
    }

    @Override
    public DefaultMatrix add(Matrix m) throws MatrixSizeException {
        Matrices.Checks.addSubtractCheck(this, m, true);
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
        Matrices.Checks.addSubtractCheck(this, m, false);
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] - m.get(i, j);
            }
        }
        return new DefaultMatrix(result);
    }

    @Override
    public Matrix multiply(int scalar) {
        if (scalar == 1) {
            return this;
        } else  if (scalar == 0) {
            return Matrices.zeroMatrix(rows, cols);
        }

        int[][] result = Matrices.Util.getCopy(array);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = array[i][j] * scalar;
            }
        }
        return new DefaultMatrix(result);
    }

    @Override
    public DefaultMatrix multiply(Matrix m) throws MatrixSizeException {
        Matrices.Checks.multiplyCheck(this, m);
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
}
