module org.fxstudy.oraclefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.fxstudy.oraclefx to javafx.fxml;
    exports org.fxstudy.oraclefx;
}