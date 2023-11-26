module ru.cs.vsu.shul {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;


    opens ru.cs.vsu.shul to javafx.fxml;
    exports ru.cs.vsu.shul;
}