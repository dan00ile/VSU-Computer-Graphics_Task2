package ru.cs.vsu.shul;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ProtoCurveController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<Point2D> points = new ArrayList<>();

    private static final List<Double> xPoints = new ArrayList<>();
    private static final List<Double> yPoints = new ArrayList<>();

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        canvas.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(canvas.getGraphicsContext2D(), event);
            }
        });
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());
        graphicsContext.clearRect(0, 0, 800,800);

        if (!checkFault(clickPoint)) {
            points.add(clickPoint);
            xPoints.add(clickPoint.getX());
            yPoints.add(clickPoint.getY());
        }

        for (Point2D point : points) {
            int POINT_RADIUS = 3;
            graphicsContext.fillOval(
                    point.getX() - POINT_RADIUS, point.getY() - POINT_RADIUS,
                    2 * POINT_RADIUS, 2 * POINT_RADIUS);
        }

        if (xPoints.size() > 1) {
            List<Point2D> splinePoints = CubicSplineInterpolation.calculateCubicSplineInterpolation(xPoints, yPoints);

            graphicsContext.setStroke(Color.DARKRED);

            for (int i = 1; i < splinePoints.size(); i++) {
                Point2D p1 = splinePoints.get(i - 1);
                Point2D p2 = splinePoints.get(i);
                graphicsContext.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }
    }

    private boolean checkFault(Point2D clickPoint) {
        double x = clickPoint.getX();
        double y = clickPoint.getY();

        return xPoints.contains(x) && yPoints.contains(y);
    }


}
