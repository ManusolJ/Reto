module com.example.reto2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires waffle.jna;

    opens com.example.reto2 to javafx.fxml;
    exports com.example.reto2;
}