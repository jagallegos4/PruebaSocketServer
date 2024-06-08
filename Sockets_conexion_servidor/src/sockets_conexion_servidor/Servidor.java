
package sockets_conexion_servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

                // Leer el usuario y la contraseña enviados por el cliente
                String username = input.readUTF();
                String password = input.readUTF();
                System.out.println("Usuario: " + username + ", Contraseña: " + password);

                // Aquí se puede agregar la lógica para validar el usuario y la contraseña
                // Por simplicidad, asumimos que la validación siempre es exitosa
                output.writeUTF("Credenciales recibidas!");

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
