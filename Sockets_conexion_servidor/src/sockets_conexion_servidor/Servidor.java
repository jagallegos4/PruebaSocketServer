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
                    
                    String nombre = input.readUTF();
                    String apellido = input.readUTF();
                    String cedula = input.readUTF();
                    String user = input.readUTF();
                    String password = input.readUTF();
                    
                    Usuario usuario =new Usuario(nombre, apellido, cedula, user, password);

                    // Agregar el nuevo usuario a la base de datos
                    if (DatabaseConnection.addUser(usuario)) {
                        output.writeUTF("Usuario agregado correctamente!");
                    } else {
                        output.writeUTF("Error al agregar el usuario!");
                    }
                }
                
                else if (action.equals("getAllUsers")) {
                    // Obtener todos los usuarios de la base de datos
                    List<Usuario> usuarios = DatabaseConnection.getAllUsers();
                    output.writeInt(usuarios.size());
                    for (Usuario usuario : usuarios) {
                        //output.writeInt(usuario.getIdUsuario());
                        output.writeUTF(usuario.getNombre());
                        output.writeUTF(usuario.getApellido());
                        output.writeUTF(usuario.getCedula());
                        output.writeUTF(usuario.getUser());
                        output.writeUTF(usuario.getPassword());
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
