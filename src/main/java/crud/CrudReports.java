package crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controllers.ConnectionController;
import classes.Report;


public class CrudReports {
    
    public static ArrayList<classes.Report> leerTodos() throws SQLException {
        
            PreparedStatement st = ConnectionController.getConexion().prepareStatement(
                    crud.SqlSentences.getLEER_INFORMES());
            ResultSet rs = st.executeQuery();
            ArrayList<Report> listaInformes = new ArrayList();
            
            while (rs.next()) {
                Report informe = new Report();
                informe.setCamionero(rs.getString(1));
                informe.setCamion(rs.getString(2));
                informe.setPlaca(rs.getString(3));
                informe.setDestino(rs.getString(4));
                informe.setFecha(rs.getString(5));
                listaInformes.add(informe);
            }
            
            return listaInformes;

    }

}
