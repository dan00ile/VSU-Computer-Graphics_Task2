package ru.cs.vsu.shul;

public class TDMA {
    public static double[] solveTDMA(double[][] matrix, double[] d) {
        int n = d.length;

        double[] a = new double[n]; // нижняя диагональ
        double[] b = new double[n]; // середина
        double[] c = new double[n]; // верхняя диагональ

        for (int i = 0; i < n; i++) {
            b[i] = matrix[i][i];
            if (i < n - 1) {
                a[i] = matrix[i + 1][i];
                c[i] = matrix[i][i + 1];
            }
        }

        double temp;
        c[0] /= b[0];
        d[0] /= b[0];
        for (int i = 1; i < n; i++) {
            temp = 1.0 / (b[i] - c[i - 1] * a[i]);
            c[i] *= temp;
            d[i] = (d[i] - d[i - 1] * a[i]) * temp;
        }

        double[] x = new double[n];
        x[n - 1] = d[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            x[i] = d[i] - c[i] * x[i + 1];
        }

        return x;
    }
}
