/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets_conexion_servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Andres Gallegos
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/tu_base_de_datos";
    private static final String USER = "tu_usuario";
    private static final String PASSWORD = "tu_contraseña";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validateCredentials(String user, String password) {
        String query = "SELECT * FROM usuarios WHERE user = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return resultSet.next(); // Devuelve true si encuentra el usuario y la contraseña
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
