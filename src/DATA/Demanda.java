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

    private int fechaKey;
    private int tiempoKey;
    private int origenKey;
    private int destinoKey;
    private int cantidadPasajerosKey;

    public Demanda(int fechaKey, int tiempoKey, int origenKey, int destinoKey, int cantidadPasajeros) {
        this.fechaKey = fechaKey;
        this.tiempoKey = tiempoKey;
        this.origenKey = origenKey;
        this.destinoKey = destinoKey;
        this.cantidadPasajerosKey = cantidadPasajeros;
    }

    public void setFechaKey(int fechaKey) {
        this.fechaKey = fechaKey;
    }

    public int getFechaKey() {
        return this.fechaKey;
    }

    public void setTiempoKey(int tiempoKey) {
        this.tiempoKey = tiempoKey;
    }

    public int getTiempoKey() {
        return this.tiempoKey;
    }

    public void setOrigenKey(int origenKey) {
        this.origenKey = origenKey;
    }

    public int getOrigenKey() {
        return this.origenKey;
    }

    public void setDestinoKey(int destinoKey) {
        this.destinoKey = destinoKey;
    }

    public int getDestinoKey() {
        return this.destinoKey;
    }

    public void setCantPasajerosKey(int cantidadPasajerosKey) {
        this.cantidadPasajerosKey = cantidadPasajerosKey;
    }

    public int getCantPasajeros() {
        return this.cantidadPasajerosKey;
    }

}
