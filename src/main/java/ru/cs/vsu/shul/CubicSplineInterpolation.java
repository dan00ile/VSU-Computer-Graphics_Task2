package ru.cs.vsu.shul;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class CubicSplineInterpolation {


    public static double[] toDoubleArrList(List<Double> list) {
        double[] arr = new double[list.size()];

        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    public static List<Point2D> calculateCubicSplineInterpolation(List<Double> x, List<Double> y) {
        double[] xArr = CubicSplineInterpolation.toDoubleArrList(x);
        double[] yArr = CubicSplineInterpolation.toDoubleArrList(y);

        return calculateCSI(xArr, yArr);
    }


    private static List<Point2D> calculateCSI(double[] x, double[] y) {
        List<Point2D> interpolatedPoints = new ArrayList<>();

        CubicSpline2D cubicSpline2D = new CubicSpline2D(x, y);

        double maxLength = cubicSpline2D.params[cubicSpline2D.params.length-1];

        for (double i = 0; i < maxLength; i ++) {
            interpolatedPoints.add(cubicSpline2D.point(i));
        }

        return interpolatedPoints;
    }
}
