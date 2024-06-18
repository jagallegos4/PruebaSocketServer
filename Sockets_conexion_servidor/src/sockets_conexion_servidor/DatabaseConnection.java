
package sockets_conexion_servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String query = "INSERT INTO usuario (nombre, apellido, cedula, user, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, cedula);
            preparedStatement.setString(4, user);
            preparedStatement.setString(5, password);
            
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Usuario"+nombre+" "+apellido+" ingresado exitosamente.");
            return rowsAffected > 0; // Devuelve true si se insertó el usuario correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean addTipoCuenta(TipoCuenta tipoCuenta){
        String query = "INSERT INTO tipo_cuentas (ID_TIPO_CUENTA, NOMBRE_TIPO) VALUES (?,?)";
        
        int idTipoCuenta = tipoCuenta.getIdTipo();
        String nombreTipoCuenta = tipoCuenta.getNombreTipo();
        
        try (Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setInt(1, idTipoCuenta);
            ps.setString(2, nombreTipoCuenta);
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("El tipo de cuenta"+" "+nombreTipoCuenta+" ingresado exitosamente.");
            return rowsAffected > 0; // Devuelve true si se insertó el usuario correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return false;
    }
}
