package com.fundorotativo.fundo.db;

import com.fundorotativo.fundo.Solicitante;
import com.fundorotativo.fundo.Voluntario;
import com.fundorotativo.fundo.Emprestimo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private static Connection conn;

    // Método para conectar ao banco de dados (uso do SQLiteConnection)
    public static Connection connect() {
        if (conn == null) {
            conn = SQLiteConnection.connect(); // Obtém a conexão
        }
        return conn;
    }

    // Método para criar as tabelas no banco de dados
    public static void criarTabelas() {
        String sqlSolicitantes = "CREATE TABLE IF NOT EXISTS solicitantes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cpf TEXT UNIQUE NOT NULL, " +
                "rg TEXT NOT NULL, " +
                "endereco TEXT NOT NULL, " +
                "bairro TEXT NOT NULL);";

        String sqlVoluntarios = "CREATE TABLE IF NOT EXISTS voluntarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cpf TEXT UNIQUE NOT NULL, " +
                "rg TEXT NOT NULL, " +
                "endereco TEXT NOT NULL, " +
                "bairro TEXT NOT NULL);";

        String sqlEmprestimos = "CREATE TABLE IF NOT EXISTS emprestimos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cpf TEXT NOT NULL, " +
                "valor DOUBLE NOT NULL, " +
                "parcelas INTEGER NOT NULL);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sqlSolicitantes);
            stmt.executeUpdate(sqlVoluntarios);
            stmt.executeUpdate(sqlEmprestimos);
            System.out.println("Tabelas criadas com sucesso.");

            // Criar índices para acelerar a busca por CPF
            String indexSolicitantes = "CREATE INDEX IF NOT EXISTS idx_solicitantes_cpf ON solicitantes(cpf);";
            String indexVoluntarios = "CREATE INDEX IF NOT EXISTS idx_voluntarios_cpf ON voluntarios(cpf);";
            stmt.executeUpdate(indexSolicitantes);
            stmt.executeUpdate(indexVoluntarios);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    // Método para verificar a existência do CPF no banco
    public static boolean verificarCPFExistente(String cpf, String tipo) {
        String sql = "SELECT COUNT(*) FROM " + tipo + " WHERE cpf = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            return rs.getInt(1) > 0;  // Retorna true se o CPF já existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // MÉTODOS DE SOLICITANTE

    // Método para inserir um novo solicitante no banco de dados
    public static boolean inserirSolicitante(String nome, String cpf, String rg, String endereco, String bairro) {
        if (verificarCPFExistente(cpf, "solicitantes")) {
            System.out.println("CPF já existe no banco!");
            return false;
        }

        String sql = "INSERT INTO solicitantes (nome, cpf, rg, endereco, bairro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, cpf);
            pstmt.setString(3, rg);
            pstmt.setString(4, endereco);
            pstmt.setString(5, bairro);

            pstmt.executeUpdate();  // Executa a inserção no banco de dados
            System.out.println("Solicitante cadastrado com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar solicitante: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todos os solicitantes
    public static List<Solicitante> getAllSolicitantes() {
        List<Solicitante> solicitantes = new ArrayList<>();
        String sql = "SELECT * FROM solicitantes";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Solicitante solicitante = new Solicitante(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("rg"),
                        rs.getString("endereco"),
                        rs.getString("bairro")
                );
                solicitantes.add(solicitante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return solicitantes;
    }

    // Método para excluir um solicitante do banco de dados
    public static boolean excluirSolicitante(String cpf) {
        // Verificar se o CPF existe antes de excluir
        if (!verificarCPFExistente(cpf, "solicitantes")) {
            return false;  // Retorna falso se o CPF não existir
        }

        String sql = "DELETE FROM solicitantes WHERE cpf = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;  // Retorna true se a exclusão for bem-sucedida
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // MÉTODOS DE VOLUNTÁRIO

    // Método para inserir um novo voluntário no banco de dados
    public static boolean inserirVoluntario(String nome, String cpf, String rg, String endereco, String bairro) {
        if (verificarCPFExistente(cpf, "voluntarios")) {
            System.out.println("CPF já existe no banco!");
            return false;
        }

        String sql = "INSERT INTO voluntarios (nome, cpf, rg, endereco, bairro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, cpf);
            pstmt.setString(3, rg);
            pstmt.setString(4, endereco);
            pstmt.setString(5, bairro);

            pstmt.executeUpdate();  // Executa a inserção no banco de dados
            System.out.println("Voluntário cadastrado com sucesso!");
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar voluntário: " + e.getMessage());
            return false;
        }
    }

    // Método para excluir um voluntário do banco de dados
    public static boolean excluirVoluntario(String cpf) {
        // Verificar se o CPF existe antes de excluir
        if (!verificarCPFExistente(cpf, "voluntarios")) {
            return false;  // Retorna falso se o CPF não existir
        }

        String sql = "DELETE FROM voluntarios WHERE cpf = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;  // Retorna true se a exclusão for bem-sucedida
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos os voluntários
    public static List<Voluntario> getAllVoluntarios() {
        List<Voluntario> voluntarios = new ArrayList<>();
        String sql = "SELECT * FROM voluntarios";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Voluntario voluntario = new Voluntario(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("rg"),
                        rs.getString("endereco"),
                        rs.getString("bairro")
                );
                voluntarios.add(voluntario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voluntarios;
    }

    // MÉTODOS DE EMPRÉSTIMO

    // Método para inserir um novo empréstimo
    public static boolean inserirEmprestimo(String cpf, double valor, int parcelas) {
        String sql = "INSERT INTO emprestimos (cpf, valor, parcelas) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            pstmt.setDouble(2, valor);
            pstmt.setInt(3, parcelas);

            pstmt.executeUpdate();  // Executa a inserção no banco de dados
            System.out.println("Empréstimo inserido com sucesso!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para recuperar todos os empréstimos do banco de dados
    public static List<Emprestimo> getAllEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";  // Consulta para obter todos os empréstimos

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Itera sobre o resultado da consulta e cria objetos Emprestimo
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        new Solicitante(rs.getString("cpf"), "", "", "", ""),  // Criar solicitante simplificado
                        new Voluntario("", "", "", "", ""),                   // Criar voluntário simplificado
                        rs.getDouble("valor"),       // Recupera o valor do empréstimo
                        rs.getInt("parcelas"),       // Recupera o número de parcelas
                        LocalDate.now(),             // Data inicial (exemplo: data atual)
                        LocalDate.now().plusMonths(rs.getInt("parcelas")),  // Data final calculada
                        1                             // Dia de pagamento (exemplo: 1)
                );
                emprestimos.add(emprestimo);  // Adiciona o empréstimo à lista
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Caso haja algum erro, exibe no log
        }

        return emprestimos;  // Retorna a lista de empréstimos
    }
}
