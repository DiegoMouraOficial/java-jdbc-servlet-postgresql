package com.br.diegomoura.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Classe utilitária para obter conexão com o banco de dados
 * */
public class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
    private static Connection connection = null;

    /**
     * Obtém um conexão com o banco de dados.
     *
     * @return A conexao com o banco de dados.
     * */
    public static Connection getConnection() {
        //Verifica se ja existe uma conexao estabelecia
        if (connection != null) {
            return connection;
        } else {
            try {
                // Carrega as info de confi do banco de dados do arquivo db.properties
                Properties properties = new Properties();
                InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
                properties.load(inputStream);

                //Extrai as info de cong do arquivo
                String url = properties.getProperty("url");
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");

                // Estabele uma conexao com o banco de dados
                connection = DriverManager.getConnection(url, user, password);

                // Verifica se a conexao foi estabelecida com sucesso
                if (connection.isValid(5)) {
                    System.out.println("Conexão estabelecida com o banco de dados.");
                } else {
                    System.out.println("Falha ao estabelecer conexão com o banco de dados.");
                }

            } catch (SQLException | IOException e) {
               log.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
            }
            return connection;
        }
    }
}
