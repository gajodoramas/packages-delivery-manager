package crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controllers.ConnectionController;
import classes.Package;


public class CrudPackages {
    
    public static boolean crear(Package paquete) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getINSERTAR_PAQUETE());

        st.setInt(1, paquete.getCodigopaquete());
        st.setString(2, paquete.getDescripcion());
        st.setString(3, paquete.getDestino());
        st.setString(4, paquete.getDirecciondestinatario());
        st.setInt(5, paquete.getApartadopostalciudad());
        st.setString(6, paquete.getRfccamionero());

        if (st.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static Package leerUno(String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getSELECCIONAR_PAQUETE());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        Package paquete = new Package();

        if (rs.next()) {
            paquete.setCodigopaquete(rs.getInt(1));
            paquete.setDescripcion(rs.getString(2));
            paquete.setDestino(rs.getString(3));
            paquete.setDirecciondestinatario(rs.getString(4));
            paquete.setApartadopostalciudad(rs.getInt(5));
            paquete.setRfccamionero(rs.getString(6));
        }

        return paquete;

    }
    
        public static ArrayList<Package> leerTodos() throws SQLException {
        
            PreparedStatement st = ConnectionController.getConexion()
                    .prepareStatement(crud.SqlSentences.getSELECCIONAR_PAQUETES());
            ResultSet rs = st.executeQuery();
            ArrayList<Package> listaPaquetes = new ArrayList();
            
            while (rs.next()) {
                Package paquete = new Package();
                paquete.setCodigopaquete(rs.getInt(1));
                paquete.setDescripcion(rs.getString(2));
                paquete.setDestino(rs.getString(3));
                paquete.setDirecciondestinatario(rs.getString(4));
                paquete.setApartadopostalciudad(rs.getInt(5));
                paquete.setRfccamionero(rs.getString(6));
                listaPaquetes.add(paquete);
            }
            
            return listaPaquetes;

    }
        
        public static boolean actualizar(Package paquete, String pk) throws SQLException {

        PreparedStatement st = ConnectionController.getConexion()
                .prepareStatement(crud.SqlSentences.getACTUALIZAR_PAQUETE());
        st.setInt(1, paquete.getCodigopaquete());
        st.setString(2, paquete.getDescripcion());
        st.setString(3, paquete.getDestino());
        st.setString(4, paquete.getDirecciondestinatario());
        st.setInt(5, paquete.getApartadopostalciudad());
        st.setString(6, paquete.getRfccamionero());
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
                .prepareStatement(crud.SqlSentences.getBORRAR_PAQUETE());
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
                .prepareStatement(crud.SqlSentences.getBUSCAR_PK_REPETIDA_PAQUETE());
        st.setString(1, pk);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }

}
