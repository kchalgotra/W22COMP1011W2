module com.example.w22comp1011w2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.w22comp1011w2 to javafx.fxml;
    exports com.example.w22comp1011w2;
}