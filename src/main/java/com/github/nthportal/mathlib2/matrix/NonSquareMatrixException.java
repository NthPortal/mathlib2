package com.github.nthportal.mathlib2.matrix;

public class NonSquareMatrixException extends MatrixSizeException {
    public NonSquareMatrixException() {
        super();
    }

    public NonSquareMatrixException(String message) {
        super(message);
    }
}
