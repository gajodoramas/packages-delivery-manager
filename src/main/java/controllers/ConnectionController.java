package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionController {

    private static String conector;
    private static String ruta;
    private static int puerto;
    private static String usuario;
    private static String contrasena;
    private static Connection conexion;

    public static boolean conectar() throws SQLException {
        
        //Problemas con el puerto, lo quitamos por ahora

            conexion = DriverManager.getConnection("jdbc:"
                    + conector + ":" + ruta, usuario, contrasena);

        return !conexion.isClosed();

    }

    public static String aString() {
        return "jdbc:" + conector + ":" + ruta + "\n"  + usuario + "\n" + contrasena;
    }
    

    public static boolean cargarDatosDesdeFichero() {

        boolean exito = false;

        try {

            BufferedReader br = new BufferedReader(new FileReader("db.config"));

            ConnectionController.conector = br.readLine();
            ConnectionController.ruta = br.readLine();
            ConnectionController.puerto = Integer.parseInt(br.readLine());
            ConnectionController.usuario = br.readLine();
            ConnectionController.contrasena = br.readLine();

            br.close();

            exito = true;

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return exito;
    }

    public static boolean probarConexion(String nconector, String nruta,
            int npuerto, String nusuario,
            String ncontrasena) {

        Connection pruebaConexion;

        try {
            pruebaConexion = DriverManager.getConnection("jdbc:"
                    + nconector + ":" + nruta, nusuario, ncontrasena);

            if (!pruebaConexion.isClosed() && pruebaConexion != null) {
                pruebaConexion.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }

        return false;
    }

    public static void guardarDatosEnFichero() throws SQLException, IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter("basededatos.txt"));

        bw.write(conector + "\n");
        bw.write(ruta + "\n");
        bw.write(puerto + "\n");
        bw.write(usuario + "\n");
        bw.write(contrasena);

        bw.close();

    }

    public static String getConector() {
        return conector;
    }

    public static void setConector(String conector) {
        ConnectionController.conector = conector;
    }

    public static String getRuta() {
        return ruta;
    }

    public static void setRuta(String ruta) {
        ConnectionController.ruta = ruta;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        ConnectionController.usuario = usuario;
    }

    public static String getContrasena() {
        return contrasena;
    }

    public static void setContrasena(String contrasena) {
        ConnectionController.contrasena = contrasena;
    }

    public static Connection getConexion() throws SQLException {
        return conexion;

    }

    public static void setConexion(Connection conexion) {
        ConnectionController.conexion = conexion;
    }

    public static int getPuerto() {
        return puerto;
    }

    public static void setPuerto(int puerto) {
        ConnectionController.puerto = puerto;
    }
    
    

}
