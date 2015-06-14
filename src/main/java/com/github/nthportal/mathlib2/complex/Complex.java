package com.github.nthportal.mathlib2.complex;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Complex {
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

    public int asInt() throws ArithmeticException {
        if (imaginary != 0) {
            throw new ArithmeticException("Cannot convert complex number to integer: imaginary part is non-zero");
        }
        return real;
    }

    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(real)
                .append(imaginary)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Complex rhs = (Complex) obj;
        return new EqualsBuilder()
                .append(real, rhs.real)
                .append(imaginary, rhs.imaginary)
                .isEquals();
    }
}
