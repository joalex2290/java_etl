/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

/**
 *
 * @author John A. Munoz
 */
public class Demanda {
    
    private int id_fecha;
    private int id_franja;
    private int id_origen;
    private int id_destino;
    private int cantidad_pasajeros;

    public Demanda(int id_fecha, int id_franja, int id_origen, int id_destino, int cantidad_pasajeros) {
        this.id_fecha = id_fecha;
        this.id_franja = id_franja;
        this.id_origen = id_origen;
        this.id_destino = id_destino;
        this.cantidad_pasajeros = cantidad_pasajeros;
    }

    public int getId_fecha() {
        return id_fecha;
    }

    public int getId_franja() {
        return id_franja;
    }

    public int getId_origen() {
        return id_origen;
    }

    public int getId_destino() {
        return id_destino;
    }

    public int getCantidad_pasajeros() {
        return cantidad_pasajeros;
    }

    public void setId_fecha(int id_fecha) {
        this.id_fecha = id_fecha;
    }

    public void setId_franja(int id_franja) {
        this.id_franja = id_franja;
    }

    public void setId_origen(int id_origen) {
        this.id_origen = id_origen;
    }

    public void setId_destino(int id_destino) {
        this.id_destino = id_destino;
    }

    public void setCantidad_pasajeros(int cantidad_pasajeros) {
        this.cantidad_pasajeros = cantidad_pasajeros;
    }
    
    
    
}
