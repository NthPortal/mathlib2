package com.github.nthportal.mathlib2.matrix;

public interface SquareMatrix extends Matrix {
    /**
     * Returns the size of this <tt>SquareMatrix</tt>.
     * The same as {@link #rows()} and {@link #columns()}.
     *
     * @return the size of this SquareMatrix
     */
    default int size() {
        return rows();
    }

    /**
     * Adds a <tt>Matrix</tt> to this <tt>SquareMatrix</tt>.
     *
     * @param m the Matrix to add
     * @return a SquareMatrix which is a sum of the two matrices
     * @throws MatrixSizeException if the matrices do not have the same
     *          dimensions
     */
    @Override
    SquareMatrix add(Matrix m) throws MatrixSizeException;

    /**
     * Subtracts a <tt>Matrix</tt> from this <tt>SquareMatrix</tt>.
     *
     * @param m the Matrix to subtract
     * @return a SquareMatrix which is the difference of the two matrices
     * @throws MatrixSizeException if the matrices do not have the same
     *          dimensions
     */
    @Override
    SquareMatrix subtract(Matrix m) throws MatrixSizeException;

    /**
     * Multiplies this <tt>SquareMatrix</tt> by a scalar value.
     *
     * @param scalar the factor by which to scale the entries of
     *               this SquareMatrix
     * @return a SquareMatrix scaled by the specified value
     */
    @Override
    SquareMatrix multiply(int scalar);

    /**
     * Returns the transpose of this <tt>SquareMatrix</tt>.
     *
     * @return a SquareMatrix which is the transpose of this SquareMatrix
     */
    @Override
    SquareMatrix transpose();

    /**
     * Returns <tt>true</tt>; a <tt>SquareMatrix</tt> is always square.
     *
     * @return true
     */
    @Override
    default boolean isSquare() {
        return true;
    }
}
