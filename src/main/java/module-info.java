module com.fundorotativo.fundo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fundorotativo.fundo to javafx.fxml;
    exports com.fundorotativo.fundo;
}