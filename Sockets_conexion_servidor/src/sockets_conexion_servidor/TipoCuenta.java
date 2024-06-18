package sockets_conexion_servidor;

public class TipoCuenta {
    int idTipo;
    String NombreTipo;

    public TipoCuenta(int idTipo, String NombreTipo) {
        this.idTipo = idTipo;
        this.NombreTipo = NombreTipo;
    }
    
    
    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return NombreTipo;
    }

    public void setNombreTipo(String NombreTipo) {
        this.NombreTipo = NombreTipo;
    }
    
    

}
