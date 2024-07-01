package sockets_conexion_servidor;

public class CabeceraConta {
    private int id_cabecera;
    private String periodo;

    public CabeceraConta() {
        this.id_cabecera = 0;
        this.periodo = "";
    }

    public CabeceraConta(int id_cabecera, String periodo) {
        this.id_cabecera = id_cabecera;
        this.periodo = periodo;
    }

    public int getId_cabecera() {
        return id_cabecera;
    }

    public void setId_cabecera(int id_cabecera) {
        this.id_cabecera = id_cabecera;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
        
}
