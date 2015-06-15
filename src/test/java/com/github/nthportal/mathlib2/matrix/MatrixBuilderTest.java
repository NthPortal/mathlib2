package com.github.nthportal.mathlib2.matrix;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class MatrixBuilderTest {

    private static Field builderArray;
    private static Field builderExpired;

    private static Field matrixArray;

    private static Field underlyingBuilder;

    @BeforeClass
    public static void setUp() throws Exception {
        Class matrixBuilderClass = MatrixBuilder.class;
        builderArray = matrixBuilderClass.getDeclaredField("array");
        builderArray.setAccessible(true);
        builderExpired = matrixBuilderClass.getDeclaredField("expired");
        builderExpired.setAccessible(true);

        Class matrixClass = DefaultMatrix.class;
        matrixArray = matrixClass.getDeclaredField("array");
        matrixArray.setAccessible(true);

        Class vectorBuilderClass = MatrixBuilder.VectorBuilder.class;
        underlyingBuilder = vectorBuilderClass.getDeclaredField("underlyingBuilder");
        underlyingBuilder.setAccessible(true);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        builderArray.setAccessible(false);
        builderExpired.setAccessible(false);

        matrixArray.setAccessible(false);

        underlyingBuilder.setAccessible(false);
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

    @Test
    public void testNewVectorBuilder() throws Exception {
        try {
            MatrixBuilder.newVectorBuilder(0);
            fail("Vector cannot have size of 0");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            MatrixBuilder.newVectorBuilder(-2);
            fail("Vector cannot have negative size");
        } catch (IllegalArgumentException ignored) {
        }

        MatrixBuilder.VectorBuilder builder = MatrixBuilder.newVectorBuilder(4);
        MatrixBuilder underlying = (MatrixBuilder) underlyingBuilder.get(builder);

        int rows = underlying.rows;
        int cols = underlying.cols;
        assertEquals(4, rows);
        assertEquals(1, cols);

        int[][] array = (int[][]) builderArray.get(underlying);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                assertEquals(0, array[i][j]);
            }
        }
    }

    @Test
    public void vectorTestWithValue() throws Exception {
        try {
            MatrixBuilder.newVectorBuilder(4)
                    .withValue(-1, 0);
            fail("Cannot have an index < 0");
        } catch (MatrixIndexOutOfBoundsException ignored) {
        }

        try {
            MatrixBuilder.newVectorBuilder(4)
                    .withValue(4, 0);
            fail("Cannot have an index >= vector size");
        } catch (MatrixIndexOutOfBoundsException ignored) {
        }

        int index = 1;
        MatrixBuilder.VectorBuilder builder = MatrixBuilder.newVectorBuilder(4)
                .withValue(index, 2);
        MatrixBuilder underlying = (MatrixBuilder) underlyingBuilder.get(builder);

        assertEquals(1, underlying.cols);

        boolean expired = (boolean) builderExpired.get(underlying);
        assertEquals(false, expired);

        int[][] array = (int[][]) builderArray.get(underlying);
        for (int i = 0; i < underlying.rows; i++) {
            for (int j = 0; j < underlying.cols; j++) {
                if (index == i) {
                    assertEquals(2, array[i][j]);
                } else {
                    assertEquals(0, array[i][j]);
                }
            }
        }

        builder.create();
        builder.withValue(index, 2);
        array = (int[][]) builderArray.get(underlying);
        assertNotNull(array);
        expired = (boolean) builderExpired.get(underlying);
        assertEquals(false, expired);
    }
}