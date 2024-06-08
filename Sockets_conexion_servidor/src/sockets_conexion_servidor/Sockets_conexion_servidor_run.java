package sockets_conexion_servidor;

import java.io.IOException;

public class Sockets_conexion_servidor_run {

    public static void main(String[] args) {
     try {
            Servidor servidor = new Servidor(5000);
            servidor.start();
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
    
}
