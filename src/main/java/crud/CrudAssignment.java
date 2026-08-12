package crud;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controllers.ConnectionController;
import classes.Assignment;


public class CrudAssignment {
    
    public static boolean crear(Assignment asignacion) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getINSERTAR_ASIGNACION());

        st.setString(1, asignacion.getPlacacamion());
        st.setString(2, asignacion.getRfccamionero());
        System.out.println(Date.valueOf(asignacion.getFecha()));
        st.setDate(3, Date.valueOf(asignacion.getFecha()));

        if (st.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static Assignment leerUno(String pk1, String pk2, String pk3) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getSELECCIONAR_ASIGNACION());
        st.setString(1, pk1);
        st.setString(2, pk2);
        st.setString(3, pk3);
        ResultSet rs = st.executeQuery();

        Assignment asignacion = new Assignment();

        if (rs.next()) {
            asignacion.setPlacacamion(rs.getString(1));
            asignacion.setRfccamionero(rs.getString(2));
            asignacion.setFecha(rs.getString(3));
        }

        return asignacion;

    }
    
    public static ArrayList<Assignment> leerTodos() throws SQLException {
        
            PreparedStatement st = ConnectionController.getConexion()
                    .prepareStatement(crud.SqlSentences.getSELECCIONAR_ASIGNACIONES());
            ResultSet rs = st.executeQuery();
            ArrayList<Assignment> listaAsignaciones = new ArrayList();
            
            while (rs.next()) {
                Assignment asignacion = new Assignment();
                asignacion.setPlacacamion(rs.getString(1));
                asignacion.setRfccamionero(rs.getString(2));
                asignacion.setFecha(rs.getString(3));
               
                listaAsignaciones.add(asignacion);
            }
            return listaAsignaciones;

    }
    
    public static boolean actualizar(Assignment asignacion, String placa,
            String rfc, String fecha) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getACTUALIZAR_ASIGNACION());
        st.setString(1, asignacion.getPlacacamion());
        st.setString(2, asignacion.getRfccamionero());
        st.setDate(3, Date.valueOf(asignacion.getFecha()));
        st.setString(4, placa);
        st.setString(5, rfc);
        st.setString(6, fecha);

        if (st.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean eliminar(String placa, String rfc, String fecha) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getBORRAR_ASIGNACION());
        st.setString(1, placa);
        st.setString(2, rfc);
        st.setString(3, fecha);

        if (st.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean comprobarExistencia(String placa, String rfc, String fecha) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getBUSCAR_PK_REPETIDA_ASIGNACION());
        st.setString(1, placa);
        st.setString(2, rfc);
        st.setString(3, fecha);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }

}
