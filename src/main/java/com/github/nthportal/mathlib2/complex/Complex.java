package com.github.nthportal.mathlib2.complex;

import java.util.Random;

public class Complex {
    private static final Random random = new Random();

    public final int real;
    public final int imaginary;

    public Complex(int real, int imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex add(Complex c) {
        return new Complex((real + c.real), (imaginary + c.imaginary));
    }

    public Complex subtract(Complex c) {
        return new Complex((real - c.real), (imaginary - c.imaginary));
    }

    public Complex multiply(Complex c) {
        return new Complex((real * c.real) - (imaginary * c.imaginary), (real * c.imaginary) + (imaginary * c.real));
    }

    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    public static Complex random() {
        return new Complex(random.nextInt(), random.nextInt());
    }
}
