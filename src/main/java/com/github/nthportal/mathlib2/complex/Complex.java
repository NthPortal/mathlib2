package com.github.nthportal.mathlib2.complex;

public class Complex {
    public final int real;
    public final int complex;

    public Complex(int real, int complex) {
        this.real = real;
        this.complex = complex;
    }

    public Complex add(Complex c) {
        return new Complex((real + c.real), (complex + c.complex));
    }

    public Complex subtract(Complex c) {
        return new Complex((real - c.real), (complex - c.complex));
    }

    public Complex multiply(Complex c) {
        return new Complex((real * c.real) - (complex * c.complex), (real * c.complex) + (complex * c.real));
    }

    public Complex conjugate() {
        return new Complex(real, -complex);
    }
}
