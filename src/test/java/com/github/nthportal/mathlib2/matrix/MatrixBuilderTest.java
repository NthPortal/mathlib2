package com.github.nthportal.mathlib2.matrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class MatrixBuilderTest {

    private Field builderArray;
    private Field builderRows;
    private Field builderCols;

    private Field matrixArray;
    private Field matrixRows;
    private Field matrixCols;

    @Before
    public void setUp() throws Exception {
        Class matrixBuilderClass = MatrixBuilder.class;
        builderArray = matrixBuilderClass.getDeclaredField("array");
        builderArray.setAccessible(true);
        builderRows = matrixBuilderClass.getDeclaredField("rows");
        builderRows.setAccessible(true);
        builderCols = matrixBuilderClass.getDeclaredField("cols");
        builderCols.setAccessible(true);

        Class matrixClass = Matrix.class;
        matrixArray = matrixClass.getDeclaredField("array");
        matrixArray.setAccessible(true);
        matrixRows = matrixClass.getDeclaredField("rows");
        matrixRows.setAccessible(true);
        matrixCols = matrixClass.getDeclaredField("cols");
        matrixCols.setAccessible(true);
    }

    @After
    public void tearDown() throws Exception {
        builderArray.setAccessible(false);
        builderRows.setAccessible(false);
        builderCols.setAccessible(false);

        matrixArray.setAccessible(false);
        matrixRows.setAccessible(false);
        matrixCols.setAccessible(false);
    }

    @Test
    public void testBean() throws Exception {
        MatrixBuilder builder;
        try {
            builder = new MatrixBuilder(0, 4);
            fail("Cannot have 0 rows");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            builder = new MatrixBuilder(3, 0);
            fail("Cannot have 0 columns");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            builder = new MatrixBuilder(0, 0);
            fail("Cannot have 0 rows or columns");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            builder = new MatrixBuilder(-2, -3);
            fail("Cannot have negative rows or columns");
        } catch (IllegalArgumentException ignored) {
        }

        builder = new MatrixBuilder(4, 3);

        int rows = (int) builderRows.get(builder);
        int cols = (int) builderCols.get(builder);
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
        // TODO check illegal args

        int row = 0;
        int col = 2;
        MatrixBuilder builder = new MatrixBuilder(4, 3)
                .withValue(row, col, 2);

        int rows = (int) builderRows.get(builder);
        int cols = (int) builderCols.get(builder);
        int[][] array = (int[][]) builderArray.get(builder);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((row == i) && (col == j)) {
                    assertEquals(2, array[i][j]);
                } else {
                    assertEquals(0, array[i][j]);
                }
            }
        }
    }

    @Test
    public void testCreate() throws Exception {

    }
}