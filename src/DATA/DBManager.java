/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author John A. Munoz
 */
public class DBManager {

    Connection conexion;
    String url, usuario, password;

    public DBManager() {
        url = "jdbc:postgresql://localhost:5432/kdd_etl_2016";
        usuario = "postgres";
        password = "admin";
        conectar();
    }

    public void conectar() {
        try {
            // Se carga el driver
            Class.forName("org.postgresql.Driver");
            //System.out.println( "Driver Cargado" );
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver.");
        }

        try {
            //Crear el objeto de conexion a la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion establecida con la bd.");
            //Crear objeto Statement para realizar queries a la base de datos
        } catch (Exception e) {
            System.out.println("No se pudo conectar con la bd.");
        }
    }

    public Connection getConexion() {
        return this.conexion;
    }

    public void desconectar() {
        try {
            conexion.close();
        } catch (Exception e) {
            System.out.println("No se pudo cerrar la conexion.");
        }
    }

    public boolean emptyDB() throws SQLException {
        boolean empty;
        DatabaseMetaData meta = conexion.getMetaData();
        ResultSet res = meta.getTables(null, null, null,
                new String[]{"TABLE"});
        if (res.next()) {
            empty = false;
            System.out.print("Base de Datos con tablas.\n");
        } else {
            empty = true;
            System.out.print("Base de Datos vacia.\n");
        }
        return empty;
    }

    public int insertQuery(String sql) {
        try {
            int resultado;
            Statement sentencia = conexion.createStatement();
            resultado = sentencia.executeUpdate(sql);
            return resultado;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public ResultSet selectQuery(String sql) {
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            return resultado;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
