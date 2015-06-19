package com.github.nthportal.mathlib2.matrix;

public interface Vector extends Matrix {
    /**
     * Returns the size of this <tt>Vector</tt>.
     * The same as {@link #rows()}.
     *
     * @return the size of this Vector
     */
    default int size() {
        return rows();
    }

    /**
     * Retrieves a value from this <tt>Vector</tt>.
     *
     * @param index the index of this Vector from which to retrieve the value
     * @return the value at the specified index of this Vector
     * @throws MatrixIndexOutOfBoundsException if the index is outside the
     *          bounds of this Vector
     */
    default int get(int index) throws MatrixIndexOutOfBoundsException {
        return get(index, 0);
    }

    /**
     * Adds a <tt>Matrix</tt> to this <tt>Vector</tt>.
     *
     * @param m the Matrix to add
     * @return a Vector which is a sum of the two matrices
     * @throws MatrixSizeException if the matrices do not have the same
     *          dimensions
     */
    @Override
    Vector add(Matrix m) throws MatrixSizeException;

    /**
     * Subtracts a <tt>Matrix</tt> from this <tt>Vector</tt>.
     *
     * @param m the Matrix to subtract
     * @return a Vector which is the difference of the two matrices
     * @throws MatrixSizeException if the matrices do not have the same
     *          dimensions
     */
    @Override
    Vector subtract(Matrix m) throws MatrixSizeException;

    /**
     * Multiplies this <tt>Vector</tt> by a scalar value.
     *
     * @param scalar the factor by which to scale the entries of
     *               this Vector
     * @return a Vector scaled by the specified value
     */
    @Override
    Vector multiply(int scalar);

    /**
     * Returns <tt>true</tt>; a <tt>Vector</tt> is always a vector.
     *
     * @return true
     */
    @Override
    default boolean isVector() {
        return true;
    }
}
