/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import DATA.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTextArea;

/**
 *
 * @author John A. Munoz
 */
public class Loader {

    Connection conexion;
    DBManager dbmanager;
    ScriptRunner runner;
    private JTextArea log;

    public Loader(JTextArea log) {
        dbmanager = new DBManager();
        this.log = log;
    }

    public int consultarFecha(String fecha) {
        String sql;
        sql = "SELECT id_fecha FROM fecha WHERE fecha = '" + fecha + "'";
        return dbmanager.selectQuery(sql);   
    }

    public int consultarTiempo(String franja) {
        String sql;
        sql = "SELECT id_tiempo FROM tiempo WHERE franja = '" + franja + "'";
        return dbmanager.selectQuery(sql);   
    }

    public int consultarParada(String parada) {
        String sql;
        sql = "SELECT id_parada FROM parada WHERE nombre = '" + parada + "'";
        return dbmanager.selectQuery(sql);   
    }

    public void cargarTiempoFecha() throws SQLException, FileNotFoundException, IOException {
        if (dbmanager.emptyDB()) {
            System.out.print("Cargando dimension fecha y tiempo.\n");
            runner = new ScriptRunner(dbmanager.getConexion(), false, false);
            runner.runScript(new BufferedReader(new FileReader("config.sql")));
        } else {
            System.out.print("Base de Datos con dimension fecha y tiempo cargada.\n");
        }
    }

    public int cargarParada(Parada parada) {
        String sql;
        sql = "INSERT INTO parada (id, nombre, tipo) VALUES ('"
                + parada.getId() + "', '" + parada.getNombre() + "', '"
                + parada.getTipo() + "')";

        return dbmanager.insertQuery(sql);
    }

    public int cargarDemanda(Demanda demanda) {

        String sql;
        sql = "INSERT INTO demanda (id_fecha, id_franja, id_origen, id_destino, cantidad_pasajeros) VALUES ("
                + demanda.getId_fecha() + ", " + demanda.getId_franja() + ", " 
                + demanda.getId_origen() + ", " + demanda.getId_destino() + ", "
                + demanda.getCantidad_pasajeros() + ")";

        return dbmanager.insertQuery(sql);

    }

}
