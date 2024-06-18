
package sockets_conexion_servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
             PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setString(1, user);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            
            return resultSet.next(); // Devuelve true si encuentra el usuario y la contraseña
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /*public static boolean addUser(String nombre, String apellido, String cedula, String user, String password) {
        String query = "INSERT INTO usuario (nombreUsuario, apellidoUsuario, cedula, user, password) VALUES (?, ?, ?, ?, ?)";
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
    }*/
    
    public static boolean addUser(Usuario usuario) {
        String query = "INSERT INTO usuario (nombreUsuario, apellidoUsuario, cedula, user, password) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCedula());
            ps.setString(4, usuario.getUser());
            ps.setString(5, usuario.getPassword());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se insertó el usuario correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static List<Usuario> getAllUsers() {
        String query = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario("","","","","");
                //usuario.setIdUsuario(resultSet.getInt("idUsuario"));
                usuario.setNombre(resultSet.getString("nombreUsuario"));
                usuario.setApellido(resultSet.getString("apellidoUsuario"));
                usuario.setCedula(resultSet.getString("cedula"));
                usuario.setUser(resultSet.getString("user"));
                usuario.setPassword(resultSet.getString("password"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    /*
    
    
    */
}
