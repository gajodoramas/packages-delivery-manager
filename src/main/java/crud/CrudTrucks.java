package crud;

import classes.Truck;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controllers.ConnectionController;


public class CrudTrucks {

    public static boolean crear(Truck camion) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getINSERTAR_CAMION());

        st.setString(1, camion.getPlaca());
        st.setString(2, camion.getModelo());
        st.setString(3, camion.getTipo());
        st.setString(4, camion.getPotencia());

        if (st.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static Truck leerUno(String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getSELECCIONAR_CAMION());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        Truck camion = new Truck();

        if (rs.next()) {
            camion.setPlaca(rs.getString(1));
            camion.setModelo(rs.getString(2));
            camion.setTipo(rs.getString(3));
            camion.setPotencia(rs.getString(4));
        }

        return camion;
        
    }

    public static ArrayList<Truck> leerTodos() throws SQLException {
        
        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getSELECCIONAR_CAMIONES());
        ResultSet rs = st.executeQuery();
        ArrayList<Truck> listaCamiones = new ArrayList();

        while (rs.next()) {
            Truck camion = new Truck();
            camion.setPlaca(rs.getString(1));
            camion.setModelo(rs.getString(2));
            camion.setTipo(rs.getString(3));
            camion.setPotencia(rs.getString(4));
            listaCamiones.add(camion);
        }

        return listaCamiones;

    }

    public static boolean actualizar(Truck camion, String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getACTUALIZAR_CAMION());
        st.setString(1, camion.getPlaca());
        st.setString(2, camion.getModelo());
        st.setString(3, camion.getTipo());
        st.setString(4, camion.getPotencia());
        st.setString(5, pk);

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
                .prepareStatement(crud.SqlSentences.getBORRAR_CAMION());
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
                .prepareStatement(crud.SqlSentences.getBUSCAR_PK_REPETIDA_CAMION());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return true;
        } else {
            return false;
        }
        
    }

}
