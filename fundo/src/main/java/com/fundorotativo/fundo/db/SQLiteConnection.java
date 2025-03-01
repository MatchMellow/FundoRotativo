package com.fundorotativo.fundo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URL;
import java.io.File;
import java.net.URISyntaxException;

public class SQLiteConnection {

    // Método para conectar ao banco de dados SQLite
    public static Connection connect() {
        Connection conn = null;
        try {
            // Tenta buscar o banco de dados dentro da pasta resources
            URL resource = SQLiteConnection.class.getClassLoader().getResource("com/fundorotativo/fundo/fundo_rotativo.db");

            // Se o arquivo não for encontrado
            if (resource == null) {
                throw new SQLException("Arquivo de banco de dados não encontrado.");
            }

            // No caso de ser um arquivo dentro de um JAR, convertemos para caminho absoluto
            File dbFile = new File(resource.toURI());
            if (!dbFile.exists()) {
                throw new SQLException("Arquivo de banco de dados não encontrado no caminho especificado.");
            }

            // Cria a URL de conexão com o banco de dados
            String dbUrl = "jdbc:sqlite:" + dbFile.getAbsolutePath();  // Use o caminho absoluto do arquivo

            // Conecta ao banco
            conn = DriverManager.getConnection(dbUrl);
            System.out.println("Conectado ao banco de dados SQLite.");

        } catch (SQLException | URISyntaxException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }
}
