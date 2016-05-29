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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        int id = 0;
        sql = "SELECT id_fecha FROM fecha WHERE fecha_formateada = '" + fecha + "'";
        id = dbmanager.selectIdQuery("id_fecha",sql);
        return id;
    }

    public int consultarTiempo(String hora) {
        String sql;
        int id = 0;
        sql = "SELECT id_tiempo FROM tiempo WHERE franja = '" + hora + "'";
        id = dbmanager.selectIdQuery("id_tiempo",sql);
        return id;
    }

    public int consultarParada(String parada) {
        String sql;
        int id = 0;
        sql = "SELECT id_parada FROM parada WHERE nombre = '" + parada + "'";
        id = dbmanager.selectIdQuery("id_parada",sql);
        return id;
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
        sql = "INSERT INTO parada (id,nombre,tipo) VALUES ('" + parada.getIdParada()
                + "','" + parada.getNombre() + "','" + parada.getTipo() + "');";

        dbmanager.insertQuery(sql);
    }

    void cargarDemanda(Demanda demanda) {

        String sql;
        sql = "INSERT INTO demanda (id_fecha,id_tiempo,id_origen,id_destino,cantidad_pasajeros) VALUES ("
                + demanda.getFechaKey() + "," + demanda.getTiempoKey() + "," + demanda.getOrigenKey()
                + "," + demanda.getDestinoKey() + "," + demanda.getCantPasajeros() + ");";
        dbmanager.insertQuery(sql);
    }
}
