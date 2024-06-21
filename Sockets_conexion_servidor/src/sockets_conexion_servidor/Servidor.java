package sockets_conexion_servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Servidor {

    private ServerSocket serverSocket;

    public Servidor(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Servidor escuchando en el puerto " + serverSocket.getLocalPort() + "...");

        // Bucle infinito para mantener el servidor en ejecución
        while (true) {
            try {
                // Esperar a que un cliente se conecte
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado!");

                // Manejar la conexión del cliente en un hilo separado
                new ClientHandler(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
                break; // Si ocurre un error, romper el bucle
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {

        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                // Leer la acción solicitada por el cliente
                String action = input.readUTF();

                if (action.equals("login")) {
                    // Leer el usuario y la contraseña enviados por el cliente
                    String username = input.readUTF();
                    String password = input.readUTF();
                    System.out.println("Usuario: " + username + ", Contraseña: " + password);

                    // Validar las credenciales contra la base de datos
                    if (DatabaseConnection.validateCredentials(username, password)) {
                        output.writeUTF("Credenciales válidas!");
                    } else {
                        output.writeUTF("Credenciales inválidas!");
                    }
                } else if (action.equals("addUser")) {
                    // Leer los datos del nuevo usuario
                    int id = 0;
                    String nombre = input.readUTF();
                    String apellido = input.readUTF();
                    String cedula = input.readUTF();
                    String user = input.readUTF();
                    String password = input.readUTF();

                    Usuario usuario = new Usuario(id, nombre, apellido, cedula, user, password);

                    // Agregar el nuevo usuario a la base de datos
                    if (DatabaseConnection.addUser(usuario)) {
                        output.writeUTF("Usuario agregado correctamente!");
                    } else {
                        output.writeUTF("Error al agregar el usuario!");
                    }
                } else if (action.equals("addTipoCuenta")) {
                    //Leer los datos
                    //int idTipoCuenta = input.readInt();
                    int idTipoCuenta = 0;
                    String nombreTipoCuenta = input.readUTF();
                    TipoCuenta tipoCuenta = new TipoCuenta(idTipoCuenta, nombreTipoCuenta);

                    //Agregar el tipo de cuenta a la base de datos
                    if (DatabaseConnection.addTipoCuenta(tipoCuenta)) {
                        output.writeUTF("Tipo de cuenta agregado correctamente!");
                    } else {
                        output.writeUTF("Error al agregar el tipo de cuenta!");
                    }

                } else if (action.equals("deleteUser")) {
                    // Leer el ID del usuario a eliminar
                    int idUsuario = input.readInt();

                    // Eliminar el usuario de la base de datos
                    if (DatabaseConnection.deleteUser(idUsuario)) {
                        output.writeUTF("Usuario eliminado correctamente!");
                    } else {
                        output.writeUTF("Error al eliminar el usuario!");
                    }
                } else if (action.equals("getAllUsers")) {
                    // Obtener todos los usuarios de la base de datos
                    List<Usuario> usuarios = DatabaseConnection.getAllUsers();
                    output.writeInt(usuarios.size());
                    for (Usuario usuario : usuarios) {
                        output.writeInt(usuario.getIdUsuario());
                        output.writeUTF(usuario.getNombre());
                        output.writeUTF(usuario.getApellido());
                        output.writeUTF(usuario.getCedula());
                        output.writeUTF(usuario.getUser());
                        output.writeUTF(usuario.getPassword());
                    }
                }
                else if (action.equals("updateUser")) {
                    // Leer los datos del usuario a actualizar
                    int idUsuario = input.readInt();
                    String nombre = input.readUTF();
                    String apellido = input.readUTF();
                    String cedula = input.readUTF();
                    String user = input.readUTF();
                    String password = input.readUTF();
                     Usuario usuarios = new Usuario(idUsuario, nombre, apellido, cedula, user, password);
                    // Actualizar el usuario en la base de datos
                    if (DatabaseConnection.updateUser(usuarios)) {
                        output.writeUTF("Usuario actualizado correctamente!");
                    } else {
                        output.writeUTF("Error al actualizar el usuario!");
                    }
                }

                // Cerrar las conexiones
                input.close();
                output.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
