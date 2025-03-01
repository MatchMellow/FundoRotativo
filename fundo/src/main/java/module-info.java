module com.fundorotativo.fundo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.fundorotativo.fundo to javafx.fxml;
    exports com.fundorotativo.fundo;
    exports com.fundorotativo.fundo.db;
    opens com.fundorotativo.fundo.db to javafx.fxml;
}