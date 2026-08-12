package crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controllers.ConnectionController;
import classes.City;


public class CrudCities {
    
    public static boolean crear(City ciudad) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getINSERTAR_CIUDAD());

        st.setInt(1, ciudad.getApartadopostal());
        st.setString(2, ciudad.getNombre());

        if (st.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static City leerUno(String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getSELECCIONAR_CIUDAD());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        City ciudad = new City();

        if (rs.next()) {
            ciudad.setApartadopostal(rs.getInt(1));
            ciudad.setNombre(rs.getString(2));
        }

        return ciudad;

    }
    
    public static ArrayList<City> leerTodos() throws SQLException {
        
            PreparedStatement st = ConnectionController.getConexion()
                    .prepareStatement(crud.SqlSentences.getSELECCIONAR_CIUDADES());
            ResultSet rs = st.executeQuery();
            ArrayList<City> listaCiudades = new ArrayList();
            
            while (rs.next()) {
                City ciudad = new City();
                ciudad.setApartadopostal(rs.getInt(1));
                ciudad.setNombre(rs.getString(2));
                listaCiudades.add(ciudad);
            }
            
            return listaCiudades;

    }
    
    public static boolean actualizar(City ciudad, String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getACTUALIZAR_CIUDAD());
        st.setInt(1, ciudad.getApartadopostal());
        st.setString(2, ciudad.getNombre());
        st.setString(3, pk);

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
                .prepareStatement(crud.SqlSentences.getBORRAR_CIUDAD());
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
                .prepareStatement(crud.SqlSentences.getBUSCAR_PK_REPETIDA_CIUDAD());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }

}
