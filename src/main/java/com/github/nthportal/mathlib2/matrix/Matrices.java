package com.github.nthportal.mathlib2.matrix;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Matrices {
    public static boolean equals(Matrix m1, Matrix m2) {
        if ((m1.rows() != m2.rows()) || (m1.columns() != m2.columns())) {
            return false;
        }
        EqualsBuilder builder = new EqualsBuilder();
        for (int i = 0; i < m1.rows(); i++) {
            for (int j = 0; j < m1.columns(); j++) {
                builder.append(m1.get(i, j), m2.get(i, j));
            }
        }
        return builder.isEquals();
    }

}
