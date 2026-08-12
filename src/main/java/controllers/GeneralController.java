package controllers;

import crud.CrudAssignment;
import crud.CrudCities;
import crud.CrudTrucks;
import crud.CrudPackages;
import crud.CrudTruckers;
import crud.CrudReports;
import classes.Truck;
import classes.City;
import classes.Package;
import classes.Trucker;
import classes.Report;
import classes.Assignment;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;


public class GeneralController {

    public static boolean actualizarTablas(ArrayList<DefaultTableModel> listaDTM,
            ArrayList<JLabel> listaLabels) throws SQLException {

        try {
            
            for (DefaultTableModel dtm : listaDTM) {
                dtm.setRowCount(0);
            }
            
            for (int i = 0; i < listaLabels.size(); i++) {
                listaLabels.get(i).setText("0 registros");
            }
            
            //TABLA INFORMES:
            int contador = 0;
            for (Report informe : CrudReports.leerTodos()) {
                listaDTM.get(0).addRow(informe.toArrayStrings());
                contador++;
            }
            listaLabels.get(0).setText(contador + " registros");
            
            //TABLA CAMIONES:
            
            contador = 0;
            for (Truck camion : CrudTrucks.leerTodos()) {
                listaDTM.get(1).addRow(camion.toArrayStrings());
                contador++;
            }
            listaLabels.get(1).setText(contador + " registros");
            
            //TABLA CAMIONEROS:
            contador = 0;
            for (Trucker camionero : CrudTruckers.leerTodos()) {
                listaDTM.get(2).addRow(camionero.toArrayStrings());
                contador++;
            }
            listaLabels.get(2).setText(contador + " registros");
            
            //TABLA PAQUETES:
            contador = 0;
            for (Package paquete : CrudPackages.leerTodos()) {
                listaDTM.get(3).addRow(paquete.toArrayStrings());
                contador++;
            }
            listaLabels.get(3).setText(contador + " registros");
            
            //TABLA CIUDADES:
            contador = 0;
            for (City ciudad : CrudCities.leerTodos()) {
                listaDTM.get(4).addRow(ciudad.toArrayStrings());
                contador++;
            }
            listaLabels.get(4).setText(contador + " registros");
            
            //TABLA ASIGNACIONES:
            contador = 0;
            for (Assignment asignacion : CrudAssignment.leerTodos()) {
                listaDTM.get(5).addRow(asignacion.toArrayStrings());
                contador++;
            }
            listaLabels.get(5).setText(contador + " registros");
            
            return true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
    }

}
