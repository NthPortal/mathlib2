package com.github.nthportal.mathlib2.matrix;

public interface Matrix {
    /**
     * Returns the number of rows in this <tt>Matrix</tt>.
     *
     * @return the number of rows in this Matrix
     */
    int rows();

    /**
     * Returns the number of columns in this <tt>Matrix</tt>.
     *
     * @return the number of columns in this Matrix
     */
    int columns();

    /**
     * Retrieves a value from this <tt>Matrix</tt>.
     *
     * @param row the row of this Matrix from which to retrieve the value
     * @param col the column of this Matrix from which to retrieve the value
     * @return the value in the specified row and column of this Matrix
     * @throws MatrixIndexOutOfBoundsException if the row and/or column are
     *          outside the bounds of this Matrix
     */
    int get(int row, int col) throws MatrixIndexOutOfBoundsException;

    /**
     * Adds a <tt>Matrix</tt> to this <tt>Matrix</tt>.
     *
     * @param m the Matrix to add
     * @return a Matrix which is a sum of the two matrices
     * @throws MatrixSizeException if the matrices do not have the same
     *          dimensions
     */
    Matrix add(Matrix m) throws MatrixSizeException;

    /**
     * Subtracts a <tt>Matrix</tt> from this <tt>Matrix</tt>.
     *
     * @param m the Matrix to subtract
     * @return a Matrix which is the difference of the two matrices
     * @throws MatrixSizeException if the matrices do not have the same
     *          dimensions
     */
    Matrix subtract(Matrix m) throws MatrixSizeException;

    /**
     * Multiplies this <tt>Matrix</tt> by a scalar value.
     *
     * @param scalar the factor by which to scale the entries of this Matrix
     * @return a Matrix scaled by the specified value
     */
    Matrix multiply(int scalar);

    /**
     * Right-multiplies this <tt>Matrix</tt> by another Matrix.
     *
     * @param m the Matrix to multiply by
     * @return a Matrix which is the product of the two matrices
     * @throws MatrixSizeException if the number of columns in this Matrix
     *          and the number of rows in the other Matrix are different
     */
    Matrix multiply(Matrix m) throws MatrixSizeException;

    /**
     * Returns the transpose of this <tt>Matrix</tt>.
     *
     * @return a Matrix which is the transpose of this Matrix
     */
    Matrix transpose();

    /**
     * Returns <tt>true</tt> if this <tt>Matrix</tt> is a vector.
     *
     * @return true if this Matrix is a vector (has exactly 1 column)
     */
    default boolean isVector() {
        return (columns() == 1);
    }

    /**
     * Returns <tt>true</tt> if this <tt>Matrix</tt> is square.
     *
     * @return true if this Matrix is square (has the same
     *          number of rows and columns)
     */
    default boolean isSquare() {
        return (rows() == columns());
    }
}
