package crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controllers.ConnectionController;
import classes.Trucker;


public class CrudTruckers {

    public static boolean crear(Trucker camionero) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getINSERTAR_CAMIONERO());

        st.setString(1, camionero.getRfc());
        st.setString(2, camionero.getNombre());
        st.setInt(3, camionero.getTelefono());
        st.setString(4, camionero.getDireccion());
        st.setFloat(5, camionero.getSalario());
        st.setString(6, camionero.getPoblacion());

        if (st.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static Trucker leerUno(String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getSELECCIONAR_CAMIONERO());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        Trucker camionero = new Trucker();

        if (rs.next()) {
            camionero.setRfc(rs.getString(1));
            camionero.setNombre(rs.getString(2));
            camionero.setTelefono(rs.getInt(3));
            camionero.setDireccion(rs.getString(4));
            camionero.setSalario(rs.getFloat(5));
            camionero.setPoblacion(rs.getString(6));
        }

        return camionero;

    }

    public static ArrayList<Trucker> leerTodos() throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getSELECCIONAR_CAMIONEROS());
        ResultSet rs = st.executeQuery();
        ArrayList<Trucker> listaCamioneros = new ArrayList();

        while (rs.next()) {
            Trucker camionero = new Trucker();
            camionero.setRfc(rs.getString(1));
            camionero.setNombre(rs.getString(2));
            camionero.setTelefono(rs.getInt(3));
            camionero.setDireccion(rs.getString(4));
            camionero.setSalario(rs.getFloat(5));
            camionero.setPoblacion(rs.getString(6));
            listaCamioneros.add(camionero);
        }

        return listaCamioneros;

    }

    public static boolean actualizar(Trucker camionero, String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getACTUALIZAR_CAMIONERO());
        st.setString(1, camionero.getRfc());
        st.setString(2, camionero.getNombre());
        st.setInt(3, camionero.getTelefono());
        st.setString(4, camionero.getDireccion());
        st.setFloat(5, camionero.getSalario());
        st.setString(6, camionero.getPoblacion());
        st.setString(7, pk);
        try {
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return false;

    }

    public static boolean eliminar(String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getBORRAR_CAMIONERO());
        st.setString(1, pk);

        try {
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return false;

    }

    public static boolean comprobarExistencia(String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getBUSCAR_PK_REPETIDA_CAMIONERO());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }

}
