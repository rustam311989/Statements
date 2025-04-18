module hhh.irp2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;


    opens hhh.irp2 to javafx.fxml;
    exports hhh.irp2;
}