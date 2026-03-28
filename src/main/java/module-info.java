module org.fxstudy.oraclefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.fxstudy.oraclefx to javafx.fxml;
    exports org.fxstudy.oraclefx;
}