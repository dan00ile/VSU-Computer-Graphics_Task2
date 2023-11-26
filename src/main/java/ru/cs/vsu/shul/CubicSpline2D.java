package ru.cs.vsu.shul;

import javafx.geometry.Point2D;

public class CubicSpline2D {
    private CubicSpline sx;
    private CubicSpline sy;
    public double[] params;

    public CubicSpline2D(double[] x, double[] y) {
        this.params = calculateParams(x, y);
        this.sx = new CubicSpline(params, x);
        this.sy = new CubicSpline(params, y);
    }

    public Point2D point(double param) {
        double x = sx.point(param);
        double y = sy.point(param);
        return new Point2D(x, y);
    }

    private double[] calculateParams(double[] x, double[] y) {
        double[] dx = new double[x.length - 1];
        double[] dy = new double[y.length - 1];

        for (int i = 0; i < x.length - 1; i++) {
            dx[i] = x[i + 1] - x[i];
            dy[i] = y[i + 1] - y[i];
        }

        double[] ds = new double[dx.length];
        for (int i = 0; i < dx.length; i++) {
            ds[i] = Math.sqrt(dx[i] * dx[i] + dy[i] * dy[i]);
        }

        double[] s = new double[ds.length + 1];
        s[0] = 0.0;
        for (int i = 0; i < ds.length; i++) {
            s[i + 1] = s[i] + ds[i];
        }

        return s;
    }
}
