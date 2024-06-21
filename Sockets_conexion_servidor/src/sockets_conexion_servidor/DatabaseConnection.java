package sockets_conexion_servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean addUser(Usuario usuario) {
        String query = "INSERT INTO usuario (nombre, apellido, cedula, user, password) VALUES (?, ?, ?, ?, ?)";

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

    public static boolean addTipoCuenta(TipoCuenta tipoCuenta) {
        String query = "INSERT INTO tipo_cuentas (NOMBRE_TIPO) VALUES (?)";

        //int idTipoCuenta = tipoCuenta.getIdTipo();
        //String nombreTipoCuenta = tipoCuenta.getNombreTipo();

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, tipoCuenta.getNombreTipo());
            //ps.setString(2, nombreTipoCuenta);

            int rowsAffected = ps.executeUpdate();
            System.out.println("El tipo de cuenta" + " " + tipoCuenta.getNombreTipo() + " ingresado exitosamente.");
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
                Usuario usuario = new Usuario(0, "", "", "", "", "");
                usuario.setIdUsuario(resultSet.getInt("id_usuario"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellido(resultSet.getString("apellido"));
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

    public static boolean deleteUser(int idUsuario) {
        String query = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idUsuario);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se eliminó el usuario correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean updateUser(Usuario usuarios) {
        String query = "UPDATE usuario SET nombre = ?, apellido = ?, cedula = ?, user = ?, password = ? WHERE id_usuario = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setString(1, usuarios.getNombre());
            ps.setString(2, usuarios.getApellido());
            ps.setString(3, usuarios.getCedula());
            ps.setString(4, usuarios.getUser());
            ps.setString(5, usuarios.getPassword());
            ps.setInt(6, usuarios.getIdUsuario());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se actualizó el usuario correctamente
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*
    
    
     */

}
