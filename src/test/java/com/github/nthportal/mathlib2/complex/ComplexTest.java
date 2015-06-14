package com.github.nthportal.mathlib2.complex;

import org.junit.Test;

import static org.junit.Assert.*;

public class ComplexTest {

    @Test
    public void testBean() {
        int real = 8;
        int imaginary = 3;
        Complex c = new Complex(real, imaginary);
        assertEquals(real, c.real);
        assertEquals(imaginary, c.imaginary);
    }

    @Test
    public void testAdd() throws Exception {
        Complex c1 = new Complex(6, 2);
        Complex c2 = new Complex(9, -4);
        Complex result = c1.add(c2);

        assertEquals(15, result.real);
        assertEquals(-2, result.imaginary);
    }

    @Test
    public void testSubtract() throws Exception {
        Complex c1 = new Complex(6, 2);
        Complex c2 = new Complex(9, -4);
        Complex result = c1.subtract(c2);

        assertEquals(-3, result.real);
        assertEquals(6, result.imaginary);
    }

    @Test
    public void testMultiply() throws Exception {
        Complex c1 = new Complex(6, 2);
        Complex c2 = new Complex(9, -4);
        Complex result = c1.multiply(c2);

        assertEquals(62, result.real);
        assertEquals(-6, result.imaginary);
    }


    @Test
    public void testAsInt() throws Exception {
        Complex c = new Complex(4, 0);
        try {
            assertEquals(4, c.asInt());
        } catch (ArithmeticException ignored) {
            fail("Should not produce exception");
        }

        c = new Complex(4, 2);
        try {
            c.asInt();
            fail("Should not be able to evaluate complex number as integer");
        } catch (ArithmeticException ignored) {
        }
    }

    @Test
    public void testConjugate() throws Exception {
        Complex c = new Complex(6, 2);
        Complex result = c.multiply(c.conjugate());
        try {
            int intResult = result.asInt();
            assertEquals(40, intResult);
        } catch (ArithmeticException ignored) {
            fail("Multiplying by conjugate should always yield an integer");
        }
    }

    @Test
    public void testHashCode() throws Exception {
        Complex c1 = new Complex(6, 2);
        Complex c2 = new Complex(9, -4);
        Complex c3 = new Complex(6, 2);

        assertEquals(c1.hashCode(), c1.hashCode());
        assertNotEquals(c1.hashCode(), c2.hashCode());
        assertEquals(c1.hashCode(), c3.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        Complex c1 = new Complex(6, 2);
        Complex c2 = new Complex(9, -4);
        Complex c3 = new Complex(6, 2);

        assertEquals(c1, c1);
        assertNotEquals(c1, null);
        assertNotEquals(c1, c2);
        assertEquals(c1, c3);
    }
}