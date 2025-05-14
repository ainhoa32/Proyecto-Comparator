package com.proyecto.comparadorProyecto.database;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class DatabaseInitializer {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "kevin";

    @PostConstruct
    public void init() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS comparador_de_precios");
            statement.execute("USE comparador_de_precios");

            String scriptFilePath = "src/main/resources/data.sql";
            BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath));
            StringBuilder sqlScript = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlScript.append(line).append("\n");
            }
            reader.close();

            String[] queries = sqlScript.toString().split(";");
            for (String query : queries) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
