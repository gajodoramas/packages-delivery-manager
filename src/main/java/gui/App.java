package gui;

import controllers.GeneralController;
import controllers.ConnectionController;
import crud.CrudAssignment;
import crud.CrudCities;
import crud.CrudTrucks;
import crud.CrudPackages;
import crud.CrudTruckers;
import classes.Truck;
import classes.City;
import classes.Package;
import classes.Report;
import classes.Trucker;
import classes.Assignment;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.JLabel;


public class App extends javax.swing.JFrame {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    ArrayList<DefaultTableModel> listaDTM;
    ArrayList<JLabel> listaLabels;

    /**
     * Creates new form App
     *
     * @throws java.sql.SQLException
     */
    public App() throws SQLException {
        initComponents();
        setTitle("Gestión de paquetería y camiones");
        setIconImage(toolkit.getImage("src/main/resources/icono.png"));

        ConnectionController.cargarDatosDesdeFichero();
        ConnectionController.conectar();
        prepararActualizacionesDeTablas();
        actualizarTablas();
    }

    private void prepararActualizacionesDeTablas() {

        listaDTM = new ArrayList();

        listaDTM.add((DefaultTableModel) tablaInforme.getModel());
        listaDTM.add((DefaultTableModel) tablaCamiones.getModel());
        listaDTM.add((DefaultTableModel) tablaCamioneros.getModel());
        listaDTM.add((DefaultTableModel) tablaPaquetes.getModel());
        listaDTM.add((DefaultTableModel) tablaCiudades.getModel());
        listaDTM.add((DefaultTableModel) tablaAsignacionCamiones.getModel());

        listaLabels = new ArrayList();

        listaLabels.add(cuentaInformes);
        listaLabels.add(cuentaCamiones);
        listaLabels.add(cuentaCamioneros);
        listaLabels.add(cuentaPaquetes);
        listaLabels.add(cuentaCiudades);
        listaLabels.add(cuentaAsignaciones);

    }

    public ArrayList<DefaultTableModel> getListaDTM() {
        return listaDTM;
    }

    public ArrayList<JLabel> getListaLabels() {
        return listaLabels;
    }
    
    

    public boolean actualizarTablas() throws SQLException {
        return GeneralController.actualizarTablas(listaDTM, listaLabels);
    }

    private void logicaEditarCamion() throws SQLException {

        cambiarPestanna(1);

        if (tablaCamiones.getSelectedRow() >= 0) {
            EditTruck editarCamion = new EditTruck(this, true);
            Truck camionsito = new CrudTrucks().leerUno(
                    listaDTM.get(1).getValueAt(tablaCamiones.getSelectedRow(),
                            0).toString());
            editarCamion.fijarDatos(camionsito);
            editarCamion.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, elija un camión.");
        }

    }

    private void logicaEditarCamionero() throws SQLException {

        cambiarPestanna(2);

        if (tablaCamioneros.getSelectedRow() >= 0) {
            EditTrucker editarCamionero = new EditTrucker(this, true);
            Trucker camionero = new CrudTruckers().leerUno(listaDTM.get(2).getValueAt(tablaCamioneros.getSelectedRow(), 0).toString());
            editarCamionero.fijarDatos(camionero);
            editarCamionero.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, elija un camionero.");
        }

    }

    private void logicaEditarPaquetes() throws SQLException {

        cambiarPestanna(3);

        if (tablaPaquetes.getSelectedRow() >= 0) {
            EditPackage editarPaquete = new EditPackage(this, true);
            Package paquete = new CrudPackages().leerUno(listaDTM.get(3).getValueAt(tablaPaquetes.getSelectedRow(), 0).toString());
            editarPaquete.fijarDatos(paquete);
            editarPaquete.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, elija un paquete.");
        }

    }

    private void logicaEditarCiudad() throws SQLException {

        cambiarPestanna(4);

        if (tablaCiudades.getSelectedRow() >= 0) {
            EditCity editarCiudad = new EditCity(this, true);
            City ciudad = new CrudCities().leerUno(listaDTM.get(4).getValueAt(tablaCiudades.getSelectedRow(), 0).toString());
            editarCiudad.fijarDatos(ciudad);
            editarCiudad.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, elija una ciudad.");
        }

    }

    private void logicaEditarAsignacion() throws SQLException {

        cambiarPestanna(5);

        if (tablaAsignacionCamiones.getSelectedRow() >= 0) {
            EditAssignment editarAsignacion = new EditAssignment(this, true);
            Assignment asigna = CrudAssignment.leerUno(
                    listaDTM.get(5).getValueAt(tablaAsignacionCamiones.getSelectedRow(), 0).toString(),
                    listaDTM.get(5).getValueAt(tablaAsignacionCamiones.getSelectedRow(), 1).toString(),
                    listaDTM.get(5).getValueAt(tablaAsignacionCamiones.getSelectedRow(), 2).toString()
            );
            editarAsignacion.fijarDatos(asigna);
            editarAsignacion.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, elija una asignación.");
        }

    }

    private void logicaEliminarCamion() throws SQLException {

        cambiarPestanna(1);

        if (tablaCamiones.getSelectedRow() < 0 || tablaCamiones.getSelectedRow() > tablaCamiones.getRowCount() - 1) {
            JOptionPane.showMessageDialog(this, "Para eliminar un camión, selecciónelo primero.");
        } else {

            if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar el camión seleccionado?") == 0) {
                System.out.println(listaDTM.get(1).getValueAt(tablaCamiones.getSelectedRow(), 0));
                String pk = listaDTM.get(1).getValueAt(tablaCamiones.getSelectedRow(), 0).toString();
                if (CrudTrucks.eliminar(pk)) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar. Elimine primero las asignaciones relacionados con este camión.");
                }
                actualizarTablas();
            }
        }
    }

    private void logicaEliminarCamionero() throws SQLException {

        cambiarPestanna(2);

        if (tablaCamioneros.getSelectedRow() < 0 || tablaCamioneros.getSelectedRow() > tablaCamioneros.getRowCount() - 1) {
            JOptionPane.showMessageDialog(this, "Para eliminar un camionero, selecciónelo primero.");
        } else {

            if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar el camionero seleccionado?") == 0) {
                System.out.println(listaDTM.get(2).getValueAt(tablaCamioneros.getSelectedRow(), 0));
                String pk = listaDTM.get(2).getValueAt(tablaCamioneros.getSelectedRow(), 0).toString();
                if (CrudTruckers.eliminar(pk)) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar. Elimine primero los paquetes o asignaciones relacionados con este camionero.");
                }
                actualizarTablas();
            }
        }
    }

    private void logicaEliminarPaquete() throws SQLException {

        cambiarPestanna(3);

        if (tablaPaquetes.getSelectedRow() < 0 || tablaPaquetes.getSelectedRow() > tablaPaquetes.getRowCount() - 1) {
            JOptionPane.showMessageDialog(this, "Para eliminar un paquete, selecciónelo primero.");
        } else {

            if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar el paquete seleccionado?") == 0) {
                System.out.println(listaDTM.get(3).getValueAt(tablaPaquetes.getSelectedRow(), 0));
                String pk = listaDTM.get(3).getValueAt(tablaPaquetes.getSelectedRow(), 0).toString();
                if (CrudPackages.eliminar(pk)) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar.");
                }
                actualizarTablas();
            }
        }
    }

    private void logicaEliminarCiudad() throws SQLException {

        cambiarPestanna(4);

        if (tablaCiudades.getSelectedRow() < 0 || tablaCiudades.getSelectedRow() > tablaCiudades.getRowCount() - 1) {
            JOptionPane.showMessageDialog(this, "Para eliminar una ciudad, selecciónela primero.");
        } else {

            if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar la ciudad seleccionada?") == 0) {
                System.out.println(listaDTM.get(4).getValueAt(tablaCiudades.getSelectedRow(), 0));
                String pk = listaDTM.get(4).getValueAt(tablaCiudades.getSelectedRow(), 0).toString();
                if (CrudCities.eliminar(pk)) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar.");
                }
                actualizarTablas();
            }
        }
    }

    private void logicaEliminarAsignacion() throws SQLException {

        cambiarPestanna(5);

        if (tablaAsignacionCamiones.getSelectedRow() < 0 || tablaAsignacionCamiones.getSelectedRow() > tablaAsignacionCamiones.getRowCount() - 1) {
            JOptionPane.showMessageDialog(this, "Para eliminar una asignación, selecciónela primero.");
        } else {

            if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar la asignación seleccionada?") == 0) {
                System.out.println(listaDTM.get(5).getValueAt(tablaAsignacionCamiones.getSelectedRow(), 0));
                String pk1 = listaDTM.get(5).getValueAt(tablaAsignacionCamiones.getSelectedRow(), 0).toString();
                String pk2 = listaDTM.get(5).getValueAt(tablaAsignacionCamiones.getSelectedRow(), 1).toString();
                String pk3 = listaDTM.get(5).getValueAt(tablaAsignacionCamiones.getSelectedRow(), 2).toString();
                
                if (CrudAssignment.eliminar(pk1, pk2, pk3)) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar.");
                }
                
                actualizarTablas();
            }
        }
    }

    public void cambiarPestanna(int pestana) {
        panelPestannas.setSelectedIndex(pestana);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPestannas = new javax.swing.JTabbedPane();
        tabInforme = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInforme = new javax.swing.JTable();
        panelBotonesInforme = new javax.swing.JPanel();
        botonActualizarTablas1 = new javax.swing.JButton();
        panelSuperiorInforme = new javax.swing.JPanel();
        panelCuentaRegistrosInforme = new javax.swing.JPanel();
        cuentaInformes = new javax.swing.JLabel();
        panelBusquedaInforme = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        barraBusquedaInforme = new javax.swing.JTextField();
        tabCamiones = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCamiones = new javax.swing.JTable();
        panelBotonesCamiones = new javax.swing.JPanel();
        botonAnadirCamion = new javax.swing.JButton();
        botonEditarCamion = new javax.swing.JButton();
        botonEliminarCamion = new javax.swing.JButton();
        botonActualizarTablas2 = new javax.swing.JButton();
        panelSuperiorCamiones = new javax.swing.JPanel();
        panelCuentaRegistrosCamiones = new javax.swing.JPanel();
        cuentaCamiones = new javax.swing.JLabel();
        panelBusquedaInforme1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        barraBusquedaCamiones = new javax.swing.JTextField();
        tabCamioneros = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaCamioneros = new javax.swing.JTable();
        panelBotonesCamionero = new javax.swing.JPanel();
        botonAnadirCamionero = new javax.swing.JButton();
        botonEditarCamionero = new javax.swing.JButton();
        botonEliminarCamionero = new javax.swing.JButton();
        botonActualizarTablas3 = new javax.swing.JButton();
        panelSuperiorCamiones1 = new javax.swing.JPanel();
        panelCuentaRegistrosCamiones1 = new javax.swing.JPanel();
        cuentaCamioneros = new javax.swing.JLabel();
        panelBusquedaInforme2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        barraBusquedaCamioneros = new javax.swing.JTextField();
        tabPaquetes = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaPaquetes = new javax.swing.JTable();
        panelBotonesPaquetes = new javax.swing.JPanel();
        botonAnadirPaquete = new javax.swing.JButton();
        botonEditarPaquete = new javax.swing.JButton();
        botonEliminarPaquete = new javax.swing.JButton();
        botonActualizarTablas4 = new javax.swing.JButton();
        panelSuperiorCamiones2 = new javax.swing.JPanel();
        panelCuentaRegistrosCamiones2 = new javax.swing.JPanel();
        cuentaPaquetes = new javax.swing.JLabel();
        panelBusquedaInforme3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        barraBusquedaPaquetes = new javax.swing.JTextField();
        tabCiudad = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaCiudades = new javax.swing.JTable();
        panelBotonesCiudades = new javax.swing.JPanel();
        botonAnadirCiudad = new javax.swing.JButton();
        botonEditarCiudad = new javax.swing.JButton();
        botonEliminarCiudad = new javax.swing.JButton();
        botonActualizartablas5 = new javax.swing.JButton();
        panelSuperiorCamiones3 = new javax.swing.JPanel();
        panelCuentaRegistrosCamiones3 = new javax.swing.JPanel();
        cuentaCiudades = new javax.swing.JLabel();
        panelBusquedaInforme4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        barraBusquedaCiudades = new javax.swing.JTextField();
        tabAsignacionCamiones = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaAsignacionCamiones = new javax.swing.JTable();
        panelBotonesAsignacionCamiones = new javax.swing.JPanel();
        botonAnadirAsignacionCamion = new javax.swing.JButton();
        botonEditarAsignacionCamion = new javax.swing.JButton();
        botonEliminarAsignacionCamion = new javax.swing.JButton();
        botonActualizartablas6 = new javax.swing.JButton();
        panelSuperiorCamiones4 = new javax.swing.JPanel();
        panelCuentaRegistrosCamiones4 = new javax.swing.JPanel();
        cuentaAsignaciones = new javax.swing.JLabel();
        panelBusquedaInforme5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        barraBusquedaAsignaciones = new javax.swing.JTextField();
        menuSuperior = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuExportarBD = new javax.swing.JMenuItem();
        menuEdicion = new javax.swing.JMenu();
        menuInformes = new javax.swing.JMenu();
        menuMostrarInformes = new javax.swing.JMenuItem();
        menuCamiones = new javax.swing.JMenu();
        menuAnadirCamion = new javax.swing.JMenuItem();
        menuMostrarCamiones = new javax.swing.JMenuItem();
        menuEditarCamion = new javax.swing.JMenuItem();
        menuEliminarCamion = new javax.swing.JMenuItem();
        menuCamioneros = new javax.swing.JMenu();
        menuCrearCamionero = new javax.swing.JMenuItem();
        menuMostrarCamioneros = new javax.swing.JMenuItem();
        menuEditarCamionero = new javax.swing.JMenuItem();
        menuEliminarCamionero = new javax.swing.JMenuItem();
        menuPaquetes = new javax.swing.JMenu();
        menuAnadirPaquete = new javax.swing.JMenuItem();
        menuMostrarPaquetes = new javax.swing.JMenuItem();
        menuEditarPaquete = new javax.swing.JMenuItem();
        menuEliminarPaquete = new javax.swing.JMenuItem();
        menuCiudades = new javax.swing.JMenu();
        menuAnadirCiudad = new javax.swing.JMenuItem();
        menuMostrarCiudad = new javax.swing.JMenuItem();
        menuEditarCiudad = new javax.swing.JMenuItem();
        menuEliminarCiudad = new javax.swing.JMenuItem();
        menuAsignaciones = new javax.swing.JMenu();
        menuAnadirAsignacion = new javax.swing.JMenuItem();
        menuMostrarAsignacion = new javax.swing.JMenuItem();
        menuEditarAsignacion = new javax.swing.JMenuItem();
        menuEliminarAsignacion = new javax.swing.JMenuItem();
        menuConfiguracion = new javax.swing.JMenu();
        menuConfigurarConexion = new javax.swing.JMenuItem();
        menuActualizarTablas = new javax.swing.JMenuItem();
        checkModoOscuro = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        tabInforme.setLayout(new java.awt.BorderLayout());

        tablaInforme.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Camionero", "Camión", "Placa (Camión)", "Destino", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaInforme);

        tabInforme.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        botonActualizarTablas1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonActualizarTablas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_refresh.png"))); // NOI18N
        botonActualizarTablas1.setText("Actualizar tablas");
        botonActualizarTablas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarTablas1ActionPerformed(evt);
            }
        });
        panelBotonesInforme.add(botonActualizarTablas1);

        tabInforme.add(panelBotonesInforme, java.awt.BorderLayout.PAGE_END);

        panelSuperiorInforme.setLayout(new java.awt.GridLayout(1, 0));

        panelCuentaRegistrosInforme.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cuentaInformes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cuentaInformes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cuentaInformes.setText("0 registros");
        cuentaInformes.setPreferredSize(new java.awt.Dimension(200, 25));
        panelCuentaRegistrosInforme.add(cuentaInformes);

        panelSuperiorInforme.add(panelCuentaRegistrosInforme);

        panelBusquedaInforme.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_search.png"))); // NOI18N
        panelBusquedaInforme.add(jLabel9);

        barraBusquedaInforme.setPreferredSize(new java.awt.Dimension(300, 25));
        barraBusquedaInforme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barraBusquedaInformeKeyReleased(evt);
            }
        });
        panelBusquedaInforme.add(barraBusquedaInforme);

        panelSuperiorInforme.add(panelBusquedaInforme);

        tabInforme.add(panelSuperiorInforme, java.awt.BorderLayout.PAGE_START);

        panelPestannas.addTab("Informe", tabInforme);

        tabCamiones.setLayout(new java.awt.BorderLayout());

        tablaCamiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Placa", "Modelo", "Tipo", "Potencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaCamiones);

        tabCamiones.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        botonAnadirCamion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAnadirCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        botonAnadirCamion.setText("Añadir camión...");
        botonAnadirCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirCamionActionPerformed(evt);
            }
        });
        panelBotonesCamiones.add(botonAnadirCamion);

        botonEditarCamion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEditarCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        botonEditarCamion.setText("Editar camión...");
        botonEditarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarCamionActionPerformed(evt);
            }
        });
        panelBotonesCamiones.add(botonEditarCamion);

        botonEliminarCamion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEliminarCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        botonEliminarCamion.setText("Eliminar camión");
        botonEliminarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarCamionActionPerformed(evt);
            }
        });
        panelBotonesCamiones.add(botonEliminarCamion);

        botonActualizarTablas2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonActualizarTablas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_refresh.png"))); // NOI18N
        botonActualizarTablas2.setText("Actualizar tablas");
        botonActualizarTablas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarTablas2ActionPerformed(evt);
            }
        });
        panelBotonesCamiones.add(botonActualizarTablas2);

        tabCamiones.add(panelBotonesCamiones, java.awt.BorderLayout.PAGE_END);

        panelSuperiorCamiones.setLayout(new java.awt.GridLayout(1, 0));

        panelCuentaRegistrosCamiones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cuentaCamiones.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cuentaCamiones.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cuentaCamiones.setText("0 registros");
        cuentaCamiones.setPreferredSize(new java.awt.Dimension(200, 25));
        panelCuentaRegistrosCamiones.add(cuentaCamiones);

        panelSuperiorCamiones.add(panelCuentaRegistrosCamiones);

        panelBusquedaInforme1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_search.png"))); // NOI18N
        panelBusquedaInforme1.add(jLabel11);

        barraBusquedaCamiones.setPreferredSize(new java.awt.Dimension(300, 25));
        barraBusquedaCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barraBusquedaCamionesActionPerformed(evt);
            }
        });
        barraBusquedaCamiones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barraBusquedaCamionesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barraBusquedaCamionesKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                barraBusquedaCamionesKeyTyped(evt);
            }
        });
        panelBusquedaInforme1.add(barraBusquedaCamiones);

        panelSuperiorCamiones.add(panelBusquedaInforme1);

        tabCamiones.add(panelSuperiorCamiones, java.awt.BorderLayout.PAGE_START);

        panelPestannas.addTab("Camiones", tabCamiones);

        tabCamioneros.setLayout(new java.awt.BorderLayout());

        tablaCamioneros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RFC", "Nombre", "Teléfono", "Dirección", "Salario", "Población"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaCamioneros);

        tabCamioneros.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        botonAnadirCamionero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAnadirCamionero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        botonAnadirCamionero.setText("Añadir camionero...");
        botonAnadirCamionero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirCamioneroActionPerformed(evt);
            }
        });
        panelBotonesCamionero.add(botonAnadirCamionero);

        botonEditarCamionero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEditarCamionero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        botonEditarCamionero.setText("Editar camionero...");
        botonEditarCamionero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarCamioneroActionPerformed(evt);
            }
        });
        panelBotonesCamionero.add(botonEditarCamionero);

        botonEliminarCamionero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEliminarCamionero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        botonEliminarCamionero.setText("Eliminar camionero");
        botonEliminarCamionero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarCamioneroActionPerformed(evt);
            }
        });
        panelBotonesCamionero.add(botonEliminarCamionero);

        botonActualizarTablas3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonActualizarTablas3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_refresh.png"))); // NOI18N
        botonActualizarTablas3.setText("Actualizar tablas");
        botonActualizarTablas3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarTablas3ActionPerformed(evt);
            }
        });
        panelBotonesCamionero.add(botonActualizarTablas3);

        tabCamioneros.add(panelBotonesCamionero, java.awt.BorderLayout.PAGE_END);

        panelSuperiorCamiones1.setLayout(new java.awt.GridLayout(1, 0));

        panelCuentaRegistrosCamiones1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cuentaCamioneros.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cuentaCamioneros.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cuentaCamioneros.setText("0 registros");
        cuentaCamioneros.setPreferredSize(new java.awt.Dimension(200, 25));
        panelCuentaRegistrosCamiones1.add(cuentaCamioneros);

        panelSuperiorCamiones1.add(panelCuentaRegistrosCamiones1);

        panelBusquedaInforme2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_search.png"))); // NOI18N
        panelBusquedaInforme2.add(jLabel12);

        barraBusquedaCamioneros.setPreferredSize(new java.awt.Dimension(300, 25));
        barraBusquedaCamioneros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barraBusquedaCamionerosKeyReleased(evt);
            }
        });
        panelBusquedaInforme2.add(barraBusquedaCamioneros);

        panelSuperiorCamiones1.add(panelBusquedaInforme2);

        tabCamioneros.add(panelSuperiorCamiones1, java.awt.BorderLayout.PAGE_START);

        panelPestannas.addTab("Camioneros", tabCamioneros);

        tabPaquetes.setLayout(new java.awt.BorderLayout());

        tablaPaquetes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de paquete", "Descripción", "Destino", "Dirección del destinatario", "Apartado postal (Ciudad)", "RFC (Camionero)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablaPaquetes);

        tabPaquetes.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        botonAnadirPaquete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAnadirPaquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        botonAnadirPaquete.setText("Añadir paquete...");
        botonAnadirPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirPaqueteActionPerformed(evt);
            }
        });
        panelBotonesPaquetes.add(botonAnadirPaquete);

        botonEditarPaquete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEditarPaquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        botonEditarPaquete.setText("Editar paquete...");
        botonEditarPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarPaqueteActionPerformed(evt);
            }
        });
        panelBotonesPaquetes.add(botonEditarPaquete);

        botonEliminarPaquete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEliminarPaquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        botonEliminarPaquete.setText("Eliminar paquete");
        botonEliminarPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarPaqueteActionPerformed(evt);
            }
        });
        panelBotonesPaquetes.add(botonEliminarPaquete);

        botonActualizarTablas4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonActualizarTablas4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_refresh.png"))); // NOI18N
        botonActualizarTablas4.setText("Actualizar tablas");
        botonActualizarTablas4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarTablas4ActionPerformed(evt);
            }
        });
        panelBotonesPaquetes.add(botonActualizarTablas4);

        tabPaquetes.add(panelBotonesPaquetes, java.awt.BorderLayout.PAGE_END);

        panelSuperiorCamiones2.setLayout(new java.awt.GridLayout(1, 0));

        panelCuentaRegistrosCamiones2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cuentaPaquetes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cuentaPaquetes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cuentaPaquetes.setText("0 registros");
        cuentaPaquetes.setPreferredSize(new java.awt.Dimension(200, 25));
        panelCuentaRegistrosCamiones2.add(cuentaPaquetes);

        panelSuperiorCamiones2.add(panelCuentaRegistrosCamiones2);

        panelBusquedaInforme3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_search.png"))); // NOI18N
        panelBusquedaInforme3.add(jLabel13);

        barraBusquedaPaquetes.setPreferredSize(new java.awt.Dimension(300, 25));
        barraBusquedaPaquetes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barraBusquedaPaquetesKeyReleased(evt);
            }
        });
        panelBusquedaInforme3.add(barraBusquedaPaquetes);

        panelSuperiorCamiones2.add(panelBusquedaInforme3);

        tabPaquetes.add(panelSuperiorCamiones2, java.awt.BorderLayout.PAGE_START);

        panelPestannas.addTab("Paquetes", tabPaquetes);

        tabCiudad.setLayout(new java.awt.BorderLayout());

        tablaCiudades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Apartado postal", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tablaCiudades);

        tabCiudad.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        botonAnadirCiudad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAnadirCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        botonAnadirCiudad.setText("Añadir ciudad...");
        botonAnadirCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirCiudadActionPerformed(evt);
            }
        });
        panelBotonesCiudades.add(botonAnadirCiudad);

        botonEditarCiudad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEditarCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        botonEditarCiudad.setText("Editar ciudad...");
        botonEditarCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarCiudadActionPerformed(evt);
            }
        });
        panelBotonesCiudades.add(botonEditarCiudad);

        botonEliminarCiudad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEliminarCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        botonEliminarCiudad.setText("Eliminar ciudad");
        botonEliminarCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarCiudadActionPerformed(evt);
            }
        });
        panelBotonesCiudades.add(botonEliminarCiudad);

        botonActualizartablas5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonActualizartablas5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_refresh.png"))); // NOI18N
        botonActualizartablas5.setText("Actualizar tablas");
        botonActualizartablas5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizartablas5ActionPerformed(evt);
            }
        });
        panelBotonesCiudades.add(botonActualizartablas5);

        tabCiudad.add(panelBotonesCiudades, java.awt.BorderLayout.PAGE_END);

        panelSuperiorCamiones3.setLayout(new java.awt.GridLayout(1, 0));

        panelCuentaRegistrosCamiones3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cuentaCiudades.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cuentaCiudades.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cuentaCiudades.setText("0 registros");
        cuentaCiudades.setPreferredSize(new java.awt.Dimension(200, 25));
        panelCuentaRegistrosCamiones3.add(cuentaCiudades);

        panelSuperiorCamiones3.add(panelCuentaRegistrosCamiones3);

        panelBusquedaInforme4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_search.png"))); // NOI18N
        panelBusquedaInforme4.add(jLabel14);

        barraBusquedaCiudades.setPreferredSize(new java.awt.Dimension(300, 25));
        barraBusquedaCiudades.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barraBusquedaCiudadesKeyReleased(evt);
            }
        });
        panelBusquedaInforme4.add(barraBusquedaCiudades);

        panelSuperiorCamiones3.add(panelBusquedaInforme4);

        tabCiudad.add(panelSuperiorCamiones3, java.awt.BorderLayout.PAGE_START);

        panelPestannas.addTab("Ciudades", tabCiudad);

        tabAsignacionCamiones.setLayout(new java.awt.BorderLayout());

        tablaAsignacionCamiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Placa (Camión)", "RFC (Camionero)", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tablaAsignacionCamiones);

        tabAsignacionCamiones.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        botonAnadirAsignacionCamion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonAnadirAsignacionCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        botonAnadirAsignacionCamion.setText("Añadir asignación...");
        botonAnadirAsignacionCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirAsignacionCamionActionPerformed(evt);
            }
        });
        panelBotonesAsignacionCamiones.add(botonAnadirAsignacionCamion);

        botonEditarAsignacionCamion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEditarAsignacionCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        botonEditarAsignacionCamion.setText("Editar asignación...");
        botonEditarAsignacionCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarAsignacionCamionActionPerformed(evt);
            }
        });
        panelBotonesAsignacionCamiones.add(botonEditarAsignacionCamion);

        botonEliminarAsignacionCamion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonEliminarAsignacionCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        botonEliminarAsignacionCamion.setText("Eliminar asignación");
        botonEliminarAsignacionCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarAsignacionCamionActionPerformed(evt);
            }
        });
        panelBotonesAsignacionCamiones.add(botonEliminarAsignacionCamion);

        botonActualizartablas6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        botonActualizartablas6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_refresh.png"))); // NOI18N
        botonActualizartablas6.setText("Actualizar tablas");
        botonActualizartablas6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizartablas6ActionPerformed(evt);
            }
        });
        panelBotonesAsignacionCamiones.add(botonActualizartablas6);

        tabAsignacionCamiones.add(panelBotonesAsignacionCamiones, java.awt.BorderLayout.PAGE_END);

        panelSuperiorCamiones4.setLayout(new java.awt.GridLayout(1, 0));

        panelCuentaRegistrosCamiones4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cuentaAsignaciones.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cuentaAsignaciones.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cuentaAsignaciones.setText("0 registros");
        cuentaAsignaciones.setPreferredSize(new java.awt.Dimension(200, 25));
        panelCuentaRegistrosCamiones4.add(cuentaAsignaciones);

        panelSuperiorCamiones4.add(panelCuentaRegistrosCamiones4);

        panelBusquedaInforme5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_search.png"))); // NOI18N
        panelBusquedaInforme5.add(jLabel15);

        barraBusquedaAsignaciones.setPreferredSize(new java.awt.Dimension(300, 25));
        barraBusquedaAsignaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barraBusquedaAsignacionesKeyReleased(evt);
            }
        });
        panelBusquedaInforme5.add(barraBusquedaAsignaciones);

        panelSuperiorCamiones4.add(panelBusquedaInforme5);

        tabAsignacionCamiones.add(panelSuperiorCamiones4, java.awt.BorderLayout.PAGE_START);

        panelPestannas.addTab("Asignación de camiones", tabAsignacionCamiones);

        getContentPane().add(panelPestannas);

        menuArchivo.setText("Archivo");

        menuExportarBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_export.png"))); // NOI18N
        menuExportarBD.setText("Exportar base de datos...");
        menuExportarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportarBDActionPerformed(evt);
            }
        });
        menuArchivo.add(menuExportarBD);

        menuSuperior.add(menuArchivo);

        menuEdicion.setText("Edición");

        menuInformes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_report.png"))); // NOI18N
        menuInformes.setText("Informes");

        menuMostrarInformes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_show.png"))); // NOI18N
        menuMostrarInformes.setText("Mostrar");
        menuMostrarInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMostrarInformesActionPerformed(evt);
            }
        });
        menuInformes.add(menuMostrarInformes);

        menuEdicion.add(menuInformes);

        menuCamiones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_truck.png"))); // NOI18N
        menuCamiones.setText("Camiones");

        menuAnadirCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        menuAnadirCamion.setText("Añadir...");
        menuAnadirCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAnadirCamionActionPerformed(evt);
            }
        });
        menuCamiones.add(menuAnadirCamion);

        menuMostrarCamiones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_show.png"))); // NOI18N
        menuMostrarCamiones.setText("Mostrar");
        menuMostrarCamiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMostrarCamionesActionPerformed(evt);
            }
        });
        menuCamiones.add(menuMostrarCamiones);

        menuEditarCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        menuEditarCamion.setText("Editar...");
        menuEditarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarCamionActionPerformed(evt);
            }
        });
        menuCamiones.add(menuEditarCamion);

        menuEliminarCamion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        menuEliminarCamion.setText("Eliminar");
        menuEliminarCamion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEliminarCamionActionPerformed(evt);
            }
        });
        menuCamiones.add(menuEliminarCamion);

        menuEdicion.add(menuCamiones);

        menuCamioneros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_person.png"))); // NOI18N
        menuCamioneros.setText("Camioneros");

        menuCrearCamionero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        menuCrearCamionero.setText("Añadir...");
        menuCrearCamionero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCrearCamioneroActionPerformed(evt);
            }
        });
        menuCamioneros.add(menuCrearCamionero);

        menuMostrarCamioneros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_show.png"))); // NOI18N
        menuMostrarCamioneros.setText("Mostrar");
        menuMostrarCamioneros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMostrarCamionerosActionPerformed(evt);
            }
        });
        menuCamioneros.add(menuMostrarCamioneros);

        menuEditarCamionero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        menuEditarCamionero.setText("Editar...");
        menuEditarCamionero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarCamioneroActionPerformed(evt);
            }
        });
        menuCamioneros.add(menuEditarCamionero);

        menuEliminarCamionero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        menuEliminarCamionero.setText("Eliminar");
        menuEliminarCamionero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEliminarCamioneroActionPerformed(evt);
            }
        });
        menuCamioneros.add(menuEliminarCamionero);

        menuEdicion.add(menuCamioneros);

        menuPaquetes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_packet.png"))); // NOI18N
        menuPaquetes.setText("Paquetes");

        menuAnadirPaquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        menuAnadirPaquete.setText("Añadir...");
        menuAnadirPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAnadirPaqueteActionPerformed(evt);
            }
        });
        menuPaquetes.add(menuAnadirPaquete);

        menuMostrarPaquetes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_show.png"))); // NOI18N
        menuMostrarPaquetes.setText("Mostrar");
        menuMostrarPaquetes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMostrarPaquetesActionPerformed(evt);
            }
        });
        menuPaquetes.add(menuMostrarPaquetes);

        menuEditarPaquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        menuEditarPaquete.setText("Editar...");
        menuEditarPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarPaqueteActionPerformed(evt);
            }
        });
        menuPaquetes.add(menuEditarPaquete);

        menuEliminarPaquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        menuEliminarPaquete.setText("Eliminar");
        menuEliminarPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEliminarPaqueteActionPerformed(evt);
            }
        });
        menuPaquetes.add(menuEliminarPaquete);

        menuEdicion.add(menuPaquetes);

        menuCiudades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_city.png"))); // NOI18N
        menuCiudades.setText("Ciudades");

        menuAnadirCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        menuAnadirCiudad.setText("Añadir...");
        menuAnadirCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAnadirCiudadActionPerformed(evt);
            }
        });
        menuCiudades.add(menuAnadirCiudad);

        menuMostrarCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_show.png"))); // NOI18N
        menuMostrarCiudad.setText("Mostrar");
        menuMostrarCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMostrarCiudadActionPerformed(evt);
            }
        });
        menuCiudades.add(menuMostrarCiudad);

        menuEditarCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        menuEditarCiudad.setText("Editar...");
        menuEditarCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarCiudadActionPerformed(evt);
            }
        });
        menuCiudades.add(menuEditarCiudad);

        menuEliminarCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        menuEliminarCiudad.setText("Eliminar");
        menuEliminarCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEliminarCiudadActionPerformed(evt);
            }
        });
        menuCiudades.add(menuEliminarCiudad);

        menuEdicion.add(menuCiudades);

        menuAsignaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_driver.png"))); // NOI18N
        menuAsignaciones.setText("Asignaciones");

        menuAnadirAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_add.png"))); // NOI18N
        menuAnadirAsignacion.setText("Añadir...");
        menuAnadirAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAnadirAsignacionActionPerformed(evt);
            }
        });
        menuAsignaciones.add(menuAnadirAsignacion);

        menuMostrarAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_show.png"))); // NOI18N
        menuMostrarAsignacion.setText("Mostrar");
        menuMostrarAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMostrarAsignacionActionPerformed(evt);
            }
        });
        menuAsignaciones.add(menuMostrarAsignacion);

        menuEditarAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_edit.png"))); // NOI18N
        menuEditarAsignacion.setText("Editar...");
        menuEditarAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarAsignacionActionPerformed(evt);
            }
        });
        menuAsignaciones.add(menuEditarAsignacion);

        menuEliminarAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_delete.png"))); // NOI18N
        menuEliminarAsignacion.setText("Eliminar");
        menuEliminarAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEliminarAsignacionActionPerformed(evt);
            }
        });
        menuAsignaciones.add(menuEliminarAsignacion);

        menuEdicion.add(menuAsignaciones);

        menuSuperior.add(menuEdicion);

        menuConfiguracion.setText("Configuración");
        menuConfiguracion.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
                menuConfiguracionMenuDeselected(evt);
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                menuConfiguracionMenuSelected(evt);
            }
        });

        menuConfigurarConexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_connect.png"))); // NOI18N
        menuConfigurarConexion.setText("Configurar conexión...");
        menuConfigurarConexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfigurarConexionActionPerformed(evt);
            }
        });
        menuConfiguracion.add(menuConfigurarConexion);

        menuActualizarTablas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark_refresh.png"))); // NOI18N
        menuActualizarTablas.setText("Actualizar tablas");
        menuActualizarTablas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActualizarTablasActionPerformed(evt);
            }
        });
        menuConfiguracion.add(menuActualizarTablas);

        checkModoOscuro.setText("Modo oscuro");
        checkModoOscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dark-mode.png"))); // NOI18N
        checkModoOscuro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkModoOscuroItemStateChanged(evt);
            }
        });
        checkModoOscuro.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkModoOscuroStateChanged(evt);
            }
        });
        checkModoOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkModoOscuroActionPerformed(evt);
            }
        });
        menuConfiguracion.add(checkModoOscuro);

        menuSuperior.add(menuConfiguracion);

        setJMenuBar(menuSuperior);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void checkModoOscuroStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkModoOscuroStateChanged

    }//GEN-LAST:event_checkModoOscuroStateChanged

    private void checkModoOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkModoOscuroActionPerformed
        if (checkModoOscuro.isSelected()) {
            try {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        SwingUtilities.updateComponentTreeUI(this);
    }//GEN-LAST:event_checkModoOscuroActionPerformed

    private void menuConfiguracionMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_menuConfiguracionMenuSelected

    }//GEN-LAST:event_menuConfiguracionMenuSelected

    private void menuConfiguracionMenuDeselected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_menuConfiguracionMenuDeselected

    }//GEN-LAST:event_menuConfiguracionMenuDeselected

    private void checkModoOscuroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkModoOscuroItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_checkModoOscuroItemStateChanged

    private void menuMostrarCamionerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMostrarCamionerosActionPerformed
        cambiarPestanna(2);
    }//GEN-LAST:event_menuMostrarCamionerosActionPerformed

    private void menuMostrarPaquetesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMostrarPaquetesActionPerformed
        cambiarPestanna(3);
    }//GEN-LAST:event_menuMostrarPaquetesActionPerformed

    private void menuMostrarCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMostrarCiudadActionPerformed
        cambiarPestanna(4);
    }//GEN-LAST:event_menuMostrarCiudadActionPerformed

    private void menuEliminarCamioneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEliminarCamioneroActionPerformed
        try {
            logicaEliminarCamionero();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEliminarCamioneroActionPerformed

    private void menuEliminarPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEliminarPaqueteActionPerformed
        try {
            logicaEliminarPaquete();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEliminarPaqueteActionPerformed

    private void menuEliminarCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEliminarCiudadActionPerformed
        try {
            logicaEliminarCiudad();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEliminarCiudadActionPerformed

    private void menuConfigurarConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfigurarConexionActionPerformed
        new ConfigConnection(this, true).setVisible(true);
    }//GEN-LAST:event_menuConfigurarConexionActionPerformed

    private void menuCrearCamioneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCrearCamioneroActionPerformed
        cambiarPestanna(2);
        new AddTrucker(this, true).setVisible(true);
    }//GEN-LAST:event_menuCrearCamioneroActionPerformed

    private void menuEliminarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEliminarCamionActionPerformed
        try {
            logicaEliminarCamion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEliminarCamionActionPerformed

    private void menuEditarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarCamionActionPerformed
        try {
            logicaEditarCamion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEditarCamionActionPerformed

    private void menuMostrarCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMostrarCamionesActionPerformed
        cambiarPestanna(1);
    }//GEN-LAST:event_menuMostrarCamionesActionPerformed

    private void menuAnadirCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAnadirCamionActionPerformed
        cambiarPestanna(1);
        new AddTruck(this, true).setVisible(true);
    }//GEN-LAST:event_menuAnadirCamionActionPerformed

    private void botonAnadirCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirCamionActionPerformed
        new AddTruck(this, true).setVisible(true);
    }//GEN-LAST:event_botonAnadirCamionActionPerformed

    private void barraBusquedaCamionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barraBusquedaCamionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_barraBusquedaCamionesActionPerformed

    private void barraBusquedaCamionesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaCamionesKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_barraBusquedaCamionesKeyTyped

    private void barraBusquedaCamionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaCamionesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_barraBusquedaCamionesKeyPressed

    private void barraBusquedaCamionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaCamionesKeyReleased

        ArrayList<Truck> listaCamiones;

        try {
            listaCamiones = crud.CrudTrucks.leerTodos();
            if (barraBusquedaCamiones.getText().length() == 0) {
                listaDTM.get(1).setRowCount(0);
                int contador = 0;
                for (Truck camion : listaCamiones) {
                    listaDTM.get(1).addRow(camion.toArrayStrings());
                    contador++;
                }
                cuentaCamiones.setText(contador + " registros");
            } else {
                ArrayList<Truck> listaC = new ArrayList();
                listaDTM.get(1).setRowCount(0);
                int contador = 0;
                for (Truck camion : listaCamiones) {
                    if (camion.getPlaca().matches(".*" + barraBusquedaCamiones.getText() + ".*")
                            || camion.getModelo().matches(".*" + barraBusquedaCamiones.getText() + ".*")
                            || camion.getTipo().matches(".*" + barraBusquedaCamiones.getText() + ".*")
                            || camion.getPotencia().matches(".*" + barraBusquedaCamiones.getText() + ".*")) {

                        listaC.add(camion);
                    }
                }
                for (Truck camion : listaC) {
                    listaDTM.get(1).addRow(camion.toArrayStrings());
                    contador++;
                }
                cuentaCamiones.setText(contador + " registros");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_barraBusquedaCamionesKeyReleased

    private void botonesActualizarTablas() {
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void botonEditarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarCamionActionPerformed
        try {
            logicaEditarCamion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEditarCamionActionPerformed

    private void botonActualizarTablas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarTablas2ActionPerformed
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonActualizarTablas2ActionPerformed

    private void botonActualizarTablas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarTablas1ActionPerformed
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonActualizarTablas1ActionPerformed

    private void botonActualizarTablas3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarTablas3ActionPerformed
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonActualizarTablas3ActionPerformed

    private void botonActualizarTablas4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarTablas4ActionPerformed
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonActualizarTablas4ActionPerformed

    private void botonActualizartablas5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizartablas5ActionPerformed
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonActualizartablas5ActionPerformed

    private void botonActualizartablas6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizartablas6ActionPerformed
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonActualizartablas6ActionPerformed

    private void botonEliminarAsignacionCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarAsignacionCamionActionPerformed
        try {
            logicaEliminarAsignacion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEliminarAsignacionCamionActionPerformed

    private void botonEliminarCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarCamionActionPerformed
        try {
            logicaEliminarCamion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEliminarCamionActionPerformed

    private void barraBusquedaCamionerosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaCamionerosKeyReleased

        ArrayList<Trucker> listaCamioneros;

        try {
            listaCamioneros = crud.CrudTruckers.leerTodos();
            if (barraBusquedaCamioneros.getText().length() == 0) {
                listaDTM.get(2).setRowCount(0);
                int contador = 0;
                for (Trucker camionero : listaCamioneros) {
                    listaDTM.get(2).addRow(camionero.toArrayStrings());
                    contador++;
                }
                cuentaCamioneros.setText(contador + " registros");
            } else {
                ArrayList<Trucker> listaCc = new ArrayList();
                listaDTM.get(2).setRowCount(0);
                int contador = 0;
                for (Trucker camionero : listaCamioneros) {
                    if (camionero.getRfc().matches(".*" + barraBusquedaCamioneros.getText() + ".*")
                            || camionero.getNombre().matches(".*" + barraBusquedaCamioneros.getText() + ".*")
                            || String.valueOf(camionero.getTelefono()).matches(".*" + barraBusquedaCamioneros.getText() + ".*")
                            || camionero.getDireccion().matches(".*" + barraBusquedaCamioneros.getText() + ".*")
                            || String.valueOf(camionero.getSalario()).matches(".*" + barraBusquedaCamioneros.getText() + ".*")
                            || camionero.getPoblacion().matches(".*" + barraBusquedaCamioneros.getText() + ".*")) {

                        listaCc.add(camionero);
                    }
                }
                for (Trucker camionero : listaCc) {
                    listaDTM.get(2).addRow(camionero.toArrayStrings());
                    contador++;
                }
                cuentaCamioneros.setText(contador + " registros");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_barraBusquedaCamionerosKeyReleased

    private void barraBusquedaPaquetesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaPaquetesKeyReleased

        ArrayList<Package> listaPaquetes;

        try {
            listaPaquetes = crud.CrudPackages.leerTodos();
            if (barraBusquedaPaquetes.getText().length() == 0) {
                listaDTM.get(3).setRowCount(0);
                int contador = 0;
                for (Package paquete : listaPaquetes) {
                    listaDTM.get(3).addRow(paquete.toArrayStrings());
                    contador++;
                }
                cuentaPaquetes.setText(contador + " registros");
            } else {
                ArrayList<Package> listaP = new ArrayList();
                listaDTM.get(3).setRowCount(0);
                int contador = 0;
                for (Package paquete : listaPaquetes) {
                    if (String.valueOf(paquete.getCodigopaquete()).matches(".*" + barraBusquedaPaquetes.getText() + ".*")
                            || paquete.getDescripcion().matches(".*" + barraBusquedaPaquetes.getText() + ".*")
                            || paquete.getDestino().matches(".*" + barraBusquedaPaquetes.getText() + ".*")
                            || paquete.getDirecciondestinatario().matches(".*" + barraBusquedaPaquetes.getText() + ".*")
                            || String.valueOf(paquete.getApartadopostalciudad()).matches(".*" + barraBusquedaPaquetes.getText() + ".*")
                            || paquete.getRfccamionero().matches(".*" + barraBusquedaPaquetes.getText() + ".*")) {

                        listaP.add(paquete);
                    }
                }
                for (Package paquete : listaP) {
                    listaDTM.get(3).addRow(paquete.toArrayStrings());
                    contador++;
                }
                cuentaPaquetes.setText(contador + " registros");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_barraBusquedaPaquetesKeyReleased

    private void barraBusquedaCiudadesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaCiudadesKeyReleased

        ArrayList<City> listaCiudades;

        try {
            listaCiudades = crud.CrudCities.leerTodos();
            if (barraBusquedaCiudades.getText().length() == 0) {
                listaDTM.get(4).setRowCount(0);
                int contador = 0;
                for (City ciudad : listaCiudades) {
                    listaDTM.get(4).addRow(ciudad.toArrayStrings());
                    contador++;
                }
                cuentaCiudades.setText(contador + " registros");
            } else {
                ArrayList<City> listaCiu = new ArrayList();
                listaDTM.get(4).setRowCount(0);
                int contador = 0;
                for (City ciudad : listaCiudades) {
                    if (String.valueOf(ciudad.getApartadopostal()).matches(".*" + barraBusquedaCiudades.getText() + ".*")
                            || ciudad.getNombre().matches(".*" + barraBusquedaCiudades.getText() + ".*")) {

                        listaCiu.add(ciudad);
                    }
                }
                for (City ciudad : listaCiu) {
                    listaDTM.get(4).addRow(ciudad.toArrayStrings());
                    contador++;
                }
                cuentaCiudades.setText(contador + " registros");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_barraBusquedaCiudadesKeyReleased

    private void barraBusquedaAsignacionesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaAsignacionesKeyReleased

        ArrayList<Assignment> listaAsig;

        try {
            listaAsig = crud.CrudAssignment.leerTodos();
            if (barraBusquedaAsignaciones.getText().length() == 0) {
                listaDTM.get(5).setRowCount(0);
                int contador = 0;
                for (Assignment asigna : listaAsig) {
                    listaDTM.get(5).addRow(asigna.toArrayStrings());
                    contador++;
                }
                cuentaAsignaciones.setText(contador + " registros");
            } else {
                ArrayList<Assignment> listaAsigna = new ArrayList();
                listaDTM.get(5).setRowCount(0);
                int contador = 0;
                for (Assignment asignacion : listaAsig) {
                    if (asignacion.getPlacacamion().matches(".*" + barraBusquedaAsignaciones.getText() + ".*")
                            || asignacion.getRfccamionero().matches(".*" + barraBusquedaAsignaciones.getText() + ".*")
                            || asignacion.getFecha().matches(".*" + barraBusquedaAsignaciones.getText() + ".*")) {

                        listaAsigna.add(asignacion);
                    }
                }
                for (Assignment asignacion : listaAsigna) {
                    listaDTM.get(5).addRow(asignacion.toArrayStrings());
                    contador++;
                }
                cuentaAsignaciones.setText(contador + " registros");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_barraBusquedaAsignacionesKeyReleased

    private void barraBusquedaInformeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barraBusquedaInformeKeyReleased

        ArrayList<Report> listaInformes;

        try {
            listaInformes = crud.CrudReports.leerTodos();
            if (barraBusquedaInforme.getText().length() == 0) {
                listaDTM.get(0).setRowCount(0);
                int contador = 0;
                for (Report informe : listaInformes) {
                    listaDTM.get(0).addRow(informe.toArrayStrings());
                    contador++;
                }
                cuentaInformes.setText(contador + " registros");
            } else {
                ArrayList<Report> listaInfo = new ArrayList();
                listaDTM.get(0).setRowCount(0);
                int contador = 0;
                for (Report informe : listaInformes) {
                    if (informe.getCamionero().matches(".*" + barraBusquedaInforme.getText() + ".*")
                            || informe.getCamion().matches(".*" + barraBusquedaInforme.getText() + ".*")
                            || informe.getDestino().matches(".*" + barraBusquedaInforme.getText() + ".*")
                            || informe.getFecha().matches(".*" + barraBusquedaInforme.getText() + ".*")) {

                        listaInfo.add(informe);
                    }
                }
                for (Report informe : listaInfo) {
                    listaDTM.get(0).addRow(informe.toArrayStrings());
                    contador++;
                }
                cuentaInformes.setText(contador + " registros");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_barraBusquedaInformeKeyReleased

    private void menuMostrarAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMostrarAsignacionActionPerformed
        cambiarPestanna(5);
    }//GEN-LAST:event_menuMostrarAsignacionActionPerformed

    private void menuEliminarAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEliminarAsignacionActionPerformed
        try {
            logicaEliminarAsignacion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEliminarAsignacionActionPerformed

    private void menuMostrarInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMostrarInformesActionPerformed
        cambiarPestanna(0);
    }//GEN-LAST:event_menuMostrarInformesActionPerformed

    private void botonEliminarCamioneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarCamioneroActionPerformed
        try {
            logicaEliminarCamionero();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEliminarCamioneroActionPerformed

    private void botonEliminarPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarPaqueteActionPerformed
        try {
            logicaEliminarPaquete();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEliminarPaqueteActionPerformed

    private void botonEliminarCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarCiudadActionPerformed
        try {
            logicaEliminarCiudad();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEliminarCiudadActionPerformed

    private void menuAnadirPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAnadirPaqueteActionPerformed
        cambiarPestanna(3);
        try {
            new AddPackage(this, true).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuAnadirPaqueteActionPerformed

    private void menuActualizarTablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActualizarTablasActionPerformed
        try {
            if (actualizarTablas()) {
                JOptionPane.showMessageDialog(this, "Todas las tablas fueron actualizadas.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuActualizarTablasActionPerformed

    private void botonEditarCamioneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarCamioneroActionPerformed
        try {
            logicaEditarCamionero();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEditarCamioneroActionPerformed

    private void menuEditarCamioneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarCamioneroActionPerformed
        try {
            logicaEditarCamionero();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEditarCamioneroActionPerformed

    private void menuEditarPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarPaqueteActionPerformed
        try {
            logicaEditarPaquetes();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEditarPaqueteActionPerformed

    private void botonEditarPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarPaqueteActionPerformed
        try {
            logicaEditarPaquetes();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEditarPaqueteActionPerformed

    private void botonAnadirPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirPaqueteActionPerformed
        try {
            new AddPackage(this, true).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonAnadirPaqueteActionPerformed

    private void botonAnadirCamioneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirCamioneroActionPerformed
        new AddTrucker(this, true).setVisible(true);
    }//GEN-LAST:event_botonAnadirCamioneroActionPerformed

    private void botonAnadirCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirCiudadActionPerformed
        new AddCity(this, true).setVisible(true);
    }//GEN-LAST:event_botonAnadirCiudadActionPerformed

    private void botonAnadirAsignacionCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirAsignacionCamionActionPerformed
        try {
            new AddAssignment(this, true).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonAnadirAsignacionCamionActionPerformed

    private void botonEditarCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarCiudadActionPerformed
        try {
            logicaEditarCiudad();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEditarCiudadActionPerformed

    private void botonEditarAsignacionCamionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarAsignacionCamionActionPerformed
        try {
            logicaEditarAsignacion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEditarAsignacionCamionActionPerformed

    private void menuAnadirCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAnadirCiudadActionPerformed
        cambiarPestanna(4);
        new AddCity(this, true).setVisible(true);
    }//GEN-LAST:event_menuAnadirCiudadActionPerformed

    private void menuAnadirAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAnadirAsignacionActionPerformed
        try {
            cambiarPestanna(5);
            new AddAssignment(this, true).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuAnadirAsignacionActionPerformed

    private void menuEditarAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarAsignacionActionPerformed
                try {
            logicaEditarAsignacion();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEditarAsignacionActionPerformed

    private void menuEditarCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarCiudadActionPerformed
                try {
            logicaEditarCiudad();
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuEditarCiudadActionPerformed

    private void menuExportarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportarBDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuExportarBDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    new App().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barraBusquedaAsignaciones;
    private javax.swing.JTextField barraBusquedaCamioneros;
    private javax.swing.JTextField barraBusquedaCamiones;
    private javax.swing.JTextField barraBusquedaCiudades;
    private javax.swing.JTextField barraBusquedaInforme;
    private javax.swing.JTextField barraBusquedaPaquetes;
    private javax.swing.JButton botonActualizarTablas1;
    private javax.swing.JButton botonActualizarTablas2;
    private javax.swing.JButton botonActualizarTablas3;
    private javax.swing.JButton botonActualizarTablas4;
    private javax.swing.JButton botonActualizartablas5;
    private javax.swing.JButton botonActualizartablas6;
    private javax.swing.JButton botonAnadirAsignacionCamion;
    private javax.swing.JButton botonAnadirCamion;
    private javax.swing.JButton botonAnadirCamionero;
    private javax.swing.JButton botonAnadirCiudad;
    private javax.swing.JButton botonAnadirPaquete;
    private javax.swing.JButton botonEditarAsignacionCamion;
    private javax.swing.JButton botonEditarCamion;
    private javax.swing.JButton botonEditarCamionero;
    private javax.swing.JButton botonEditarCiudad;
    private javax.swing.JButton botonEditarPaquete;
    private javax.swing.JButton botonEliminarAsignacionCamion;
    private javax.swing.JButton botonEliminarCamion;
    private javax.swing.JButton botonEliminarCamionero;
    private javax.swing.JButton botonEliminarCiudad;
    private javax.swing.JButton botonEliminarPaquete;
    private javax.swing.JCheckBoxMenuItem checkModoOscuro;
    private javax.swing.JLabel cuentaAsignaciones;
    private javax.swing.JLabel cuentaCamioneros;
    private javax.swing.JLabel cuentaCamiones;
    private javax.swing.JLabel cuentaCiudades;
    private javax.swing.JLabel cuentaInformes;
    private javax.swing.JLabel cuentaPaquetes;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JMenuItem menuActualizarTablas;
    private javax.swing.JMenuItem menuAnadirAsignacion;
    private javax.swing.JMenuItem menuAnadirCamion;
    private javax.swing.JMenuItem menuAnadirCiudad;
    private javax.swing.JMenuItem menuAnadirPaquete;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAsignaciones;
    private javax.swing.JMenu menuCamioneros;
    private javax.swing.JMenu menuCamiones;
    private javax.swing.JMenu menuCiudades;
    private javax.swing.JMenu menuConfiguracion;
    private javax.swing.JMenuItem menuConfigurarConexion;
    private javax.swing.JMenuItem menuCrearCamionero;
    private javax.swing.JMenu menuEdicion;
    private javax.swing.JMenuItem menuEditarAsignacion;
    private javax.swing.JMenuItem menuEditarCamion;
    private javax.swing.JMenuItem menuEditarCamionero;
    private javax.swing.JMenuItem menuEditarCiudad;
    private javax.swing.JMenuItem menuEditarPaquete;
    private javax.swing.JMenuItem menuEliminarAsignacion;
    private javax.swing.JMenuItem menuEliminarCamion;
    private javax.swing.JMenuItem menuEliminarCamionero;
    private javax.swing.JMenuItem menuEliminarCiudad;
    private javax.swing.JMenuItem menuEliminarPaquete;
    private javax.swing.JMenuItem menuExportarBD;
    private javax.swing.JMenu menuInformes;
    private javax.swing.JMenuItem menuMostrarAsignacion;
    private javax.swing.JMenuItem menuMostrarCamioneros;
    private javax.swing.JMenuItem menuMostrarCamiones;
    private javax.swing.JMenuItem menuMostrarCiudad;
    private javax.swing.JMenuItem menuMostrarInformes;
    private javax.swing.JMenuItem menuMostrarPaquetes;
    private javax.swing.JMenu menuPaquetes;
    private javax.swing.JMenuBar menuSuperior;
    private javax.swing.JPanel panelBotonesAsignacionCamiones;
    private javax.swing.JPanel panelBotonesCamionero;
    private javax.swing.JPanel panelBotonesCamiones;
    private javax.swing.JPanel panelBotonesCiudades;
    private javax.swing.JPanel panelBotonesInforme;
    private javax.swing.JPanel panelBotonesPaquetes;
    private javax.swing.JPanel panelBusquedaInforme;
    private javax.swing.JPanel panelBusquedaInforme1;
    private javax.swing.JPanel panelBusquedaInforme2;
    private javax.swing.JPanel panelBusquedaInforme3;
    private javax.swing.JPanel panelBusquedaInforme4;
    private javax.swing.JPanel panelBusquedaInforme5;
    private javax.swing.JPanel panelCuentaRegistrosCamiones;
    private javax.swing.JPanel panelCuentaRegistrosCamiones1;
    private javax.swing.JPanel panelCuentaRegistrosCamiones2;
    private javax.swing.JPanel panelCuentaRegistrosCamiones3;
    private javax.swing.JPanel panelCuentaRegistrosCamiones4;
    private javax.swing.JPanel panelCuentaRegistrosInforme;
    private javax.swing.JTabbedPane panelPestannas;
    private javax.swing.JPanel panelSuperiorCamiones;
    private javax.swing.JPanel panelSuperiorCamiones1;
    private javax.swing.JPanel panelSuperiorCamiones2;
    private javax.swing.JPanel panelSuperiorCamiones3;
    private javax.swing.JPanel panelSuperiorCamiones4;
    private javax.swing.JPanel panelSuperiorInforme;
    private javax.swing.JPanel tabAsignacionCamiones;
    private javax.swing.JPanel tabCamioneros;
    private javax.swing.JPanel tabCamiones;
    private javax.swing.JPanel tabCiudad;
    private javax.swing.JPanel tabInforme;
    private javax.swing.JPanel tabPaquetes;
    private javax.swing.JTable tablaAsignacionCamiones;
    private javax.swing.JTable tablaCamioneros;
    private javax.swing.JTable tablaCamiones;
    private javax.swing.JTable tablaCiudades;
    private javax.swing.JTable tablaInforme;
    private javax.swing.JTable tablaPaquetes;
    // End of variables declaration//GEN-END:variables

}
