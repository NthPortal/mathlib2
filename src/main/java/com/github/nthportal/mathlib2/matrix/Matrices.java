package com.github.nthportal.mathlib2.matrix;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Matrices {
    public static boolean equals(Matrix m1, Matrix m2) {
        if ((m1.rows() != m2.rows()) || (m1.columns() != m2.columns())) {
            return false;
        }
        EqualsBuilder builder = new EqualsBuilder();
        for (int i = 0; i < m1.rows(); i++) {
            for (int j = 0; j < m1.columns(); j++) {
                builder.append(m1.get(i, j), m2.get(i, j));
            }
        }
        return builder.isEquals();
    }

    public static SquareMatrix identity(int size) {
        return new IdentityMatrix(size);
    }

    private static class IdentityMatrix implements SquareMatrix {
        private final int size;

        private IdentityMatrix(int size) throws IllegalArgumentException {
            if (size <= 0) {
                throw new IllegalArgumentException("Matrix dimensions must be positive");
            }
            this.size = size;
        }

        private DefaultSquareMatrix toSquareMatrix() {
            int[][] array = new int[size][size];
            for (int i = 0; i < size; i++) {
                array[i][i] = 1;
            }
            return new DefaultMatrix(array).asSquareMatrix();
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public Matrix asMatrix() {
            return this;
        }

        @Override
        public int rows() {
            return size;
        }

        @Override
        public int columns() {
            return size;
        }

        @Override
        public int get(int row, int col) throws MatrixBoundsException {
            return (row == col) ? 1 : 0;
        }

        @Override
        public Matrix add(Matrix m) throws MatrixSizeException {
            return m.add(this);
        }

        @Override
        public DefaultSquareMatrix subtract(Matrix m) throws MatrixSizeException {
            return toSquareMatrix().subtract(m);
        }

        @Override
        public Matrix multiply(int scalar) {
            return (scalar == 1) ? this : toSquareMatrix().multiply(scalar);
        }

        @Override
        public Matrix multiply(Matrix m) throws MatrixSizeException {
            return m;
        }

        @Override
        public IdentityMatrix transpose() {
            return this;
        }

        @Override
        public boolean isVector() {
            return (size == 1);
        }

        @Override
        public boolean isSquare() {
            return true;
        }
    }
}
