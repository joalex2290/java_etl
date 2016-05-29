/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import DATA.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author John A. Munoz
 */
public class Transformer {

    private Loader loader;
    private JTextArea log;

    public Transformer(JTextArea log) {
        loader = new Loader(log);
        this.log = log;
        try {
            loader.cargarTiempoFecha();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void crearDemanda(String fecha, String hora, String origen, String destino, double cantidadPasajeros) {
        //SELECT key FROM fecha WHERE dia=fecha.substring(0, 2) and mes=fecha.substring(2,4) ano=fecha.substring(4,6)

        int fechaKey = loader.consultarFecha("140114");
        int tiempoKey = loader.consultarTiempo(hora);
        int origenKey = loader.consultarParada(origen);
        int destinoKey = loader.consultarParada(destino);
        int cantidadPasajerosInt = (int) Math.ceil(cantidadPasajeros);

        Demanda nuevaDemanda = new Demanda(fechaKey, tiempoKey, origenKey, destinoKey, cantidadPasajerosInt);

        loader.cargarDemanda(nuevaDemanda);

    }

    void crearParada(String nombreParada) {

        String tipoParada = "";

        if (nombreParada.matches("^.*\\d.*$")) {
            tipoParada = "RUTA";
        } else {
            tipoParada = "ESTACION";
        }

        Parada nuevaParada = new Parada(nombreParada, nombreParada, tipoParada);

        loader.cargarParada(nuevaParada);

    }

}
