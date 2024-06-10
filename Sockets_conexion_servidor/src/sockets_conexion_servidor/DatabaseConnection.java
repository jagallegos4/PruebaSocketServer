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
    private static final String URL = "jdbc:mysql://localhost:3306/bd_appdistpro1";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validateCredentials(String user, String password) {
        String query = "SELECT * FROM usuario WHERE user = ? AND password = ?";
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
    
    public static boolean addUser(String nombre, String apellido, String cedula, String user, String password) {
        String query = "INSERT INTO usuarios (nombre, apellido, cedula, user, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, cedula);
            preparedStatement.setString(4, user);
            preparedStatement.setString(5, password);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se insertó el usuario correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
