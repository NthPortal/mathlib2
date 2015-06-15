package com.github.nthportal.mathlib2.matrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class MatrixBuilderTest {

    private Field builderArray;
    private Field builderExpired;

    private Field matrixArray;

    @Before
    public void setUp() throws Exception {
        Class matrixBuilderClass = MatrixBuilder.class;
        builderArray = matrixBuilderClass.getDeclaredField("array");
        builderArray.setAccessible(true);
        builderExpired = matrixBuilderClass.getDeclaredField("expired");
        builderExpired.setAccessible(true);

        Class matrixClass = DefaultMatrix.class;
        matrixArray = matrixClass.getDeclaredField("array");
        matrixArray.setAccessible(true);
    }

    @After
    public void tearDown() throws Exception {
        builderArray.setAccessible(false);
        builderExpired.setAccessible(false);

        matrixArray.setAccessible(false);
    }

    @Test
    public void testBean() throws Exception {
        try {
            new MatrixBuilder(0, 4);
            fail("Cannot have 0 rows");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new MatrixBuilder(3, 0);
            fail("Cannot have 0 columns");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new MatrixBuilder(0, 0);
            fail("Cannot have 0 rows or columns");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new MatrixBuilder(-2, -3);
            fail("Cannot have negative rows or columns");
        } catch (IllegalArgumentException ignored) {
        }

        MatrixBuilder builder = new MatrixBuilder(4, 3);

        int rows = builder.rows;
        int cols = builder.cols;
        assertEquals(4, rows);
        assertEquals(3, cols);

        int[][] array = (int[][]) builderArray.get(builder);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                assertEquals(0, array[i][j]);
            }
        }
    }

    @Test
    public void testWithValue() throws Exception {
        try {
            new MatrixBuilder(4, 3)
                    .withValue(-1, 0, 0);
            fail("Cannot have a row index < 0");
        } catch (MatrixIndexOutOfBoundsException ignored) {
        }

        try {
            new MatrixBuilder(4, 3)
                    .withValue(4, 0, 0);
            fail("Cannot have a row index >= number of rows");
        } catch (MatrixIndexOutOfBoundsException ignored) {
        }

        try {
            new MatrixBuilder(4, 3)
                    .withValue(0, -1, 0);
            fail("Cannot have a column index < 0");
        } catch (MatrixIndexOutOfBoundsException ignored) {
        }

        try {
            new MatrixBuilder(4, 3)
                    .withValue(0, 3, 0);
            fail("Cannot have a column index >= number of columns");
        } catch (MatrixIndexOutOfBoundsException ignored) {
        }

        int row = 0;
        int col = 2;
        MatrixBuilder builder = new MatrixBuilder(4, 3)
                .withValue(row, col, 2);

        boolean expired = (boolean) builderExpired.get(builder);
        assertEquals(false, expired);

        int[][] array = (int[][]) builderArray.get(builder);
        for (int i = 0; i < builder.rows; i++) {
            for (int j = 0; j < builder.cols; j++) {
                if ((row == i) && (col == j)) {
                    assertEquals(2, array[i][j]);
                } else {
                    assertEquals(0, array[i][j]);
                }
            }
        }

        builder.create();
        builder.withValue(row, col, 2);
        array = (int[][]) builderArray.get(builder);
        assertNotNull(array);
        expired = (boolean) builderExpired.get(builder);
        assertEquals(false, expired);
    }

    @Test
    public void testCreate() throws Exception {
        MatrixBuilder builder = new MatrixBuilder(4, 3);

        boolean expired = (boolean) builderExpired.get(builder);
        assertEquals(false, expired);
        int[][] array = (int[][]) builderArray.get(builder);
        assertNotNull(array);

        DefaultMatrix matrix = builder.create();
        assertArrayEquals(array, (int[][]) matrixArray.get(matrix));
        assertEquals(builder.rows, matrix.rows());
        assertEquals(builder.cols, matrix.columns());

        expired = (boolean) builderExpired.get(builder);
        assertEquals(true, expired);
        array = (int[][]) builderArray.get(builder);
        assertNull(array);
    }
}