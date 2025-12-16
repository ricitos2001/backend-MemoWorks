package com.example.catalog.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
class DatabaseConfig {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void verificarConexion() {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Conexión exitosa a: " +
                    connection.getMetaData().getURL());
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
