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

    public int consultarTiempo(String hora) {
        String sql;
        sql = "SELECT id_tiempo FROM tiempo WHERE hora_origen = '" + hora + "'";
        return dbmanager.selectQuery(sql);   
    }

    public int consultarParada(String parada) {
        String sql;
        sql = "SELECT id_parada FROM parada WHERE nombre_parada = '" + parada + "'";
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

    public void cargarParada(Parada parada) {
        String sql;
        sql = "INSERT INTO paradas VALUES ('"
                + parada.getIdParada() + "', '" + parada.getNombre() + "', '"
                + parada.getTipo() + ")";

        dbmanager.insertQuery(sql);
    }

    void cargarDemanda(Demanda demanda) {

        String sql;
        sql = "INSERT INTO demanda VALUES ('"
                + demanda.getFechaKey() + "', '" + demanda.getTiempoKey() + "', '"
                + "', '" + demanda.getOrigenKey() + "', '"
                + "', '" + demanda.getDestinoKey() + "', '"
                + demanda.getCantPasajeros() + ")";

        dbmanager.insertQuery(sql);

    }

}
