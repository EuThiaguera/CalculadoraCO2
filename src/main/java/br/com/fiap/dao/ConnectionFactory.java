package br.com.fiap.dao;

import br.com.fiap.exception.ErroBancoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL_PADRAO = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

    private ConnectionFactory() {
    }

    public static Connection abrirConexao() {
        String url = System.getenv().getOrDefault("DB_URL", URL_PADRAO);
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (user == null || password == null) {
            throw new ErroBancoException("Variáveis de ambiente DB_USER e DB_PASSWORD não configuradas.");
        }

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new ErroBancoException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}