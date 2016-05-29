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
        } catch (SQLException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void crearDemanda(String fecha, String hora, String origen, String destino, double cantidadPasajeros) {

        //Consultar surrogate key de la fecha
        String ano, mes, dia, fecha_str;
        ano = "20" +fecha.substring(0, 2);
        mes = fecha.substring(2,4);
        dia = fecha.substring(4,6);
        fecha_str = ano + "-" + mes + "-" + dia;
        
        int id_fecha = loader.consultarFecha(fecha_str);
        
        //Consultar surrogate key de la franja horaria
        System.out.println("hora "+hora);
        String franja = hora.split(" ")[1];
        int id_franja = loader.consultarTiempo(franja);
        
        //Consultar surrogate key de la parada para el origen y destino
        int id_origen = loader.consultarParada(origen);
        int id_destino = loader.consultarParada(destino);
        
        //Redondear con funcion techo el valor leido de la cantidad de pasajeros
        int cantidadPasajerosInt = (int) Math.ceil(cantidadPasajeros);

        Demanda nuevaDemanda = new Demanda(id_fecha, id_franja, id_origen, id_destino, cantidadPasajerosInt);

        int cargado = loader.cargarDemanda(nuevaDemanda);
        System.out.println("cargar demanda" + cargado);
 
    }

    void crearParada(String nombreParada) {
        
        int existeParada = loader.consultarParada(nombreParada);
        
        //Si no existe la parada en la bodega de datos entonces se crea una nueva parada
        if (existeParada==-1) {
            String tipoParada = "";
            if (nombreParada.matches("^.*\\d.*$")) {
                tipoParada = "RUTA";
            } else {
                tipoParada = "ESTACION";
            }
            Parada nuevaparada = new Parada(nombreParada, nombreParada, tipoParada);
            loader.cargarParada(nuevaparada);
        }
    }

}
