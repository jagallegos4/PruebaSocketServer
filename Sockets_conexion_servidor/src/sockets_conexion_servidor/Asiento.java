package sockets_conexion_servidor;
public class Asiento {
    private int idAsiento;
    private int idCabecera;
    private int idCuenta;
    private String cuenta;
    private String fecha;
    private String detalle;
    private float debe;
    private float haber;

    public Asiento() {
        this.idAsiento = 0;
        this.idCabecera = 0;
        this.idCuenta = 0;
        this.cuenta = "";
        this.fecha = "";
        this.detalle = "";
        this.debe = 0;
        this.haber = 0;
    }

    public Asiento(int idAsiento, int idCabecera, int idCuenta, String cuenta, String fecha, String detalle, float debe, float haber) {
        this.idAsiento = idAsiento;
        this.idCabecera = idCabecera;
        this.idCuenta = idCuenta;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.detalle = detalle;
        this.debe = debe;
        this.haber = haber;
    }

    @Override
    public String toString() {
        return "Asiento{" + "idAsiento=" + idAsiento + ", idCabecera=" + idCabecera + ", idCuenta=" + idCuenta + ", cuenta=" + cuenta + ", fecha=" + fecha + ", detalle=" + detalle + ", debe=" + debe + ", haber=" + haber + '}';
    }
    
    
}
