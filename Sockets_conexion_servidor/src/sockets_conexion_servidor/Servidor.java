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
                } else if (action.equals("updateUser")) {
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
                } else if (action.equals("deleteUser")) {
                    // Leer el ID del usuario a eliminar
                    int idUsuario = input.readInt();

                    // Eliminar el usuario de la base de datos
                    if (DatabaseConnection.deleteUser(idUsuario)) {
                        output.writeUTF("Usuario eliminado correctamente!");
                    } else {
                        output.writeUTF("Error al eliminar el usuario!");
                    }
                } else if (action.equals("addTipoCuenta")) {
                    //Leer los datos
                    int idTipoCuenta = 0;
                    String nombreTipoCuenta = input.readUTF();
                    TipoCuenta tipoCuenta = new TipoCuenta(idTipoCuenta, nombreTipoCuenta);

                    //Agregar el tipo de cuenta a la base de datos
                    if (DatabaseConnection.addTipoCuenta(tipoCuenta)) {
                        output.writeUTF("Tipo de cuenta agregado correctamente!");
                    } else {
                        output.writeUTF("Error al agregar el tipo de cuenta!");
                    }
                } else if (action.equals("getAllTiposCuentas")) {
                    List<TipoCuenta> tiposCuentas = DatabaseConnection.obtenerTiposCuentas();
                    output.writeInt(tiposCuentas.size());
                    for (TipoCuenta tipoCuenta : tiposCuentas) {
                        output.writeInt(tipoCuenta.getIdTipo());
                        output.writeUTF(tipoCuenta.getNombreTipo());
                    }
                }else if (action.equals("updateTipoCuenta")) {
                    // Leer los datos del usuario a actualizar
                    int idTipoCuenta = input.readInt();
                    String nombreTipo = input.readUTF();
                    TipoCuenta tipoCuenta = new TipoCuenta(idTipoCuenta, nombreTipo);
                 
                    // Actualizar el usuario en la base de datos
                    if (DatabaseConnection.editarTipoCuenta(tipoCuenta)) {
                        output.writeUTF("Tipo de cuenta actualizado correctamente!");
                    } else {
                        output.writeUTF("Error al actualizar el tipo de cuenta!");
                    }
                } else if (action.equals("deleteTipoCuenta")) {
                    int idTipoCuenta = input.readInt();
                    //boolean result = DatabaseConnection.eliminarTipoCuenta(idTipoCuenta);
                    if (DatabaseConnection.eliminarTipoCuenta(idTipoCuenta)) {
                        output.writeUTF("Tipo de cuenta eliminado correctamente!");
                    } else {
                        output.writeUTF("Error al eliminar el tipo de cuenta!");
                    }
                } else if (action.equals("addCuenta")) {
                    String nombreCuenta = input.readUTF();
                    int idTipoCuenta = input.readInt();
                    Cuenta cuenta = new Cuenta(0, nombreCuenta, idTipoCuenta);

                    if (DatabaseConnection.agregarCuenta(cuenta)) {
                        output.writeUTF("Cuenta agregada correctamente!");
                    } else {
                        output.writeUTF("Error al agregar la cuenta!");
                    }
                } else if (action.equals("getAllCuentas")) {
                    List<Cuenta> cuentas = DatabaseConnection.obtenerCuentas();
                    output.writeInt(cuentas.size());
                    for (Cuenta cuenta : cuentas) {
                        output.writeInt(cuenta.getIdCuenta());
                        output.writeUTF(cuenta.getNombreCuenta());
                        output.writeInt(cuenta.getIdTipoCuenta());
                    }
                } else if (action.equals("buscarIdTipo")){
                    String nombreTipo = input.readUTF();
                    
                    int idTipo =DatabaseConnection.obtenerIdTipo(nombreTipo);
                    if(idTipo>0){
                        output.writeInt(idTipo);
                    }else{
                        output.writeInt(-1);
                    }
                }else if (action.equals("updateCuenta")) {
                    // Leer los datos del usuario a actualizar
                    int idCuenta = input.readInt();
                    String nombreCuenta = input.readUTF();
                    int idTipoCuenta = input.readInt();
                    Cuenta cuenta = new Cuenta(idCuenta, nombreCuenta, idTipoCuenta);
                    // Actualizar el usuario en la base de datos
                    if (DatabaseConnection.editarCuenta(cuenta)) {
                        output.writeUTF("Cuenta actualizada correctamente!");
                    } else {
                        output.writeUTF("Error al actualizar la cuenta!");
                    }
                }else if (action.equals("deleteCuenta")) {
                    // Leer los datos del usuario a actualizar
                    int idCuenta = input.readInt();
                    
                    // Actualizar el usuario en la base de datos
                    if (DatabaseConnection.eliminarCuenta(idCuenta)) {
                        output.writeUTF("Cuenta eliminada correctamente!");
                    } else {
                        output.writeUTF("Error al eliminar la cuenta!");
                    }
                }else if (action.equals("buscarIdCuenta")){
                    String nombreCuenta = input.readUTF();
                    
                    int idCuenta =DatabaseConnection.obtenerIdCuenta(nombreCuenta);
                    if(idCuenta>0){
                        output.writeInt(idCuenta);
                    }else{
                        output.writeInt(-1);
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
