package ru.cs.vsu.shul;

import java.util.Arrays;

public class CubicSpline {
    private double[] a, b, c, d;
    private double[] x, y;
    private int nx;

    public CubicSpline(double[] x, double[] y) {
        this.b = new double[x.length];
        this.c = new double[x.length];
        this.d = new double[x.length];
        this.x = x;
        this.y = y;

        double[] h = new double[x.length - 1];
        for (int i = 0; i < x.length - 1; i++) {
            h[i] = x[i + 1] - x[i];
        }
        this.nx = x.length;

        this.a = new double[y.length];

        for (int i = 0; i < y.length; i++) {
            this.a[i] = y[i];
        }

        double[][] aMatrix = calculateA(h);
        double[] bVector = calculateB(h);

        c = TDMA.solveTDMA(aMatrix, bVector);

        for (int i = 0; i < nx - 1; i++) {
            d[i] = (c[i + 1] - c[i]) / (3.0 * h[i]);
            double tb = (a[i + 1] - a[i]) / h[i] - h[i] * (c[i+1] + 2.0 * c[i]) / 3.0;
            b[i] = tb;
        }
    }

    public double point(double param) {
        int i = searchIndex(param);
        double dx = param - x[i];
        return a[i] + b[i] * dx + c[i] * dx * dx + d[i] * dx * dx * dx;
    }

    private int searchIndex(double param) {
        int lo = 0;
        int hi = x.length;

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (param < x[mid]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo - 1;
    }

    private double[][] calculateA(double[] h) {
        double[][] result = new double[nx][nx];
        for (int i = 0; i < nx; i++) {
            Arrays.fill(result[i], 0.0);
        }
        result[0][0] = 1.0;
        for (int i = 0; i < nx - 1; i++) {
            if (i != nx - 2) {
                result[i+1][i+1] = 2.0 * (h[i] + h[i + 1]);
            }
            result[i + 1][i] = h[i];
            result[i][i + 1] = h[i];
        }
        result[0][1] = 0.0;
        result[nx - 1][nx - 2] = 0.0;
        result[nx - 1][nx - 1] = 1.0;
        return result;
    }


    private double[] calculateB(double[] h) {
        double[] result = new double[nx];

        for (int i = 0; i < nx; i++) {
            result[i] = 0;
        }

        for (int i = 0; i < nx - 2; i++) {
            result[i+1] = 3.0 * (((a[i + 2] - a[i + 1]) / h[i + 1]) - ((a[i + 1] - a[i]) / h[i]));
        }
        return result;
    }
}
