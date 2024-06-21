package sockets_conexion_servidor;

/**
 *
 * @author Andres Gallegos
 */
public class Cuenta {

    private int idCuenta;
    private String nombreCuenta;
    private int idTipoCuenta;

    public Cuenta(int idCuenta, String nombreCuenta, int idTipoCuenta) {
        this.idCuenta = idCuenta;
        this.nombreCuenta = nombreCuenta;
        this.idTipoCuenta = idTipoCuenta;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public int getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(int idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }
    
    
}
