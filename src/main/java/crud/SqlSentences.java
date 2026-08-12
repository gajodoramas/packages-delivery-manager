package crud;


public class SqlSentences {
    
    //SENTENCIAS SQL DE CAMIONES
    
    private static final String INSERTAR_CAMION = "INSERT INTO camiones (placa, modelo, tipo, potencia) values (?, ?, ?, ?)";
    private static final String SELECCIONAR_CAMION = "SELECT * FROM camiones WHERE placa = ?";
    private static final String SELECCIONAR_CAMIONES = "SELECT * FROM camiones";
    private static final String ACTUALIZAR_CAMION = "UPDATE camiones SET placa = ?, modelo = ?, tipo = ?, potencia = ? WHERE placa = ?";
    private static final String BORRAR_CAMION = "DELETE FROM camiones WHERE placa = ?";
    private static final String BUSCAR_PK_REPETIDA_CAMION = "SELECT placa FROM camiones WHERE placa = ?";
    
    //SENTENCIAS SQL DE CAMIONEROS
    
    private static final String INSERTAR_CAMIONERO = "INSERT INTO camioneros (rfc, nombre, telefono, direccion, salario, poblacion) values (?, ?, ?, ?, ?, ?)";
    private static final String SELECCIONAR_CAMIONERO = "SELECT * FROM camioneros WHERE rfc = ?";
    private static final String SELECCIONAR_CAMIONEROS = "SELECT * FROM camioneros";
    private static final String ACTUALIZAR_CAMIONERO = "UPDATE camioneros SET rfc = ?, nombre = ?, telefono = ?, direccion = ?, salario = ?, poblacion = ? WHERE rfc = ?";
    private static final String BORRAR_CAMIONERO = "DELETE FROM camioneros WHERE rfc = ?";
    private static final String BUSCAR_PK_REPETIDA_CAMIONERO = "SELECT rfc FROM camioneros WHERE rfc = ?";
    
    //SENTENCIAS SQL DE ASIGNACIONES
    
    private static final String INSERTAR_ASIGNACION = "INSERT INTO camionescamioneros (placacamion, rfccamionero, fecha) values (?, ?, ?)";
    private static final String SELECCIONAR_ASIGNACION = "SELECT * FROM camionescamioneros WHERE placacamion = ? AND rfccamionero = ? AND fecha = ?";
    private static final String SELECCIONAR_ASIGNACIONES = "SELECT * FROM camionescamioneros";
    private static final String ACTUALIZAR_ASIGNACION = "UPDATE camionescamioneros SET placacamion = ?, rfccamionero = ?, fecha = ? WHERE placacamion = ? AND rfccamionero = ? AND fecha = ?";
    private static final String BORRAR_ASIGNACION = "DELETE FROM camionescamioneros WHERE placacamion = ? AND rfccamionero = ? AND fecha = ?";
    private static final String BUSCAR_PK_REPETIDA_ASIGNACION = "SELECT rfccamionero FROM camionescamioneros WHERE placacamion = ? AND rfccamionero = ? AND fecha = ?";
    
    //SENTENCIAS SQL DE PAQUETES
    
    private static final String INSERTAR_PAQUETE = "INSERT INTO paquetes (descripcion, destino, direcciondestinatario, apartadopostalciudad, rfccamionero) values (?, ?, ?, ?, ?)";
    private static final String SELECCIONAR_PAQUETE = "SELECT * FROM paquetes WHERE codigopaquete = ?";
    private static final String SELECCIONAR_PAQUETES = "SELECT * FROM paquetes";
    private static final String ACTUALIZAR_PAQUETE = "UPDATE paquetes SET descripcion = ?, destino = ?, direcciondestinatario = ?, apartadopostalciudad = ?, rfccamionero = ? WHERE codigopaquete = ?";
    private static final String BORRAR_PAQUETE = "DELETE FROM paquetes WHERE codigopaquete = ?";
    private static final String BUSCAR_PK_REPETIDA_PAQUETE = "SELECT codigopaquete FROM paquetes WHERE codigopaquete = ?";
    
    //SENTENCIAS SQL DE CIUDADES
    
    private static final String INSERTAR_CIUDAD = "INSERT INTO ciudades (apartadopostal, nombre) values (?, ?)";
    private static final String SELECCIONAR_CIUDAD = "SELECT * FROM ciudades WHERE apartadopostal = ?";
    private static final String SELECCIONAR_CIUDADES = "SELECT * FROM ciudades";
    private static final String BUSCAR_PK_REPETIDA_CIUDAD = "SELECT apartadopostal FROM ciudades WHERE apartadopostal = ?";
    private static final String ACTUALIZAR_CIUDAD = "UPDATE ciudades SET apartadopostal = ?, nombre = ? WHERE apartadopostal = ?";
    private static final String BORRAR_CIUDAD = "DELETE FROM ciudades WHERE apartadopostal = ?";
    
    //SENTENCIAS SQL DE INFORMES
    
    private static final String LEER_INFORMES = "SELECT ceros.nombre, cones.modelo, cones.placa, p.destino, cc.fecha FROM camioneros as ceros INNER JOIN camionescamioneros as cc ON cc.rfccamionero = ceros.rfc INNER JOIN camiones as cones ON cones.placa = cc.placacamion INNER JOIN paquetes as p ON p.rfccamionero = ceros.rfc";

    public static String getLEER_INFORMES() {
        return LEER_INFORMES;
    }
    
    public static String getINSERTAR_CAMION() {
        return INSERTAR_CAMION;
    }

    public static String getSELECCIONAR_CAMION() {
        return SELECCIONAR_CAMION;
    }

    public static String getSELECCIONAR_CAMIONES() {
        return SELECCIONAR_CAMIONES;
    }

    public static String getACTUALIZAR_CAMION() {
        return ACTUALIZAR_CAMION;
    }

    public static String getBORRAR_CAMION() {
        return BORRAR_CAMION;
    }

    public static String getBUSCAR_PK_REPETIDA_CAMION() {
        return BUSCAR_PK_REPETIDA_CAMION;
    }

    public static String getINSERTAR_CAMIONERO() {
        return INSERTAR_CAMIONERO;
    }

    public static String getSELECCIONAR_CAMIONERO() {
        return SELECCIONAR_CAMIONERO;
    }

    public static String getSELECCIONAR_CAMIONEROS() {
        return SELECCIONAR_CAMIONEROS;
    }

    public static String getACTUALIZAR_CAMIONERO() {
        return ACTUALIZAR_CAMIONERO;
    }

    public static String getBORRAR_CAMIONERO() {
        return BORRAR_CAMIONERO;
    }

    public static String getBUSCAR_PK_REPETIDA_CAMIONERO() {
        return BUSCAR_PK_REPETIDA_CAMIONERO;
    }

    public static String getINSERTAR_ASIGNACION() {
        return INSERTAR_ASIGNACION;
    }

    public static String getSELECCIONAR_ASIGNACION() {
        return SELECCIONAR_ASIGNACION;
    }

    public static String getSELECCIONAR_ASIGNACIONES() {
        return SELECCIONAR_ASIGNACIONES;
    }

    public static String getACTUALIZAR_ASIGNACION() {
        return ACTUALIZAR_ASIGNACION;
    }

    public static String getBORRAR_ASIGNACION() {
        return BORRAR_ASIGNACION;
    }

    public static String getBUSCAR_PK_REPETIDA_ASIGNACION() {
        return BUSCAR_PK_REPETIDA_ASIGNACION;
    }

    public static String getINSERTAR_PAQUETE() {
        return INSERTAR_PAQUETE;
    }

    public static String getSELECCIONAR_PAQUETE() {
        return SELECCIONAR_PAQUETE;
    }

    public static String getSELECCIONAR_PAQUETES() {
        return SELECCIONAR_PAQUETES;
    }

    public static String getACTUALIZAR_PAQUETE() {
        return ACTUALIZAR_PAQUETE;
    }

    public static String getBORRAR_PAQUETE() {
        return BORRAR_PAQUETE;
    }

    public static String getBUSCAR_PK_REPETIDA_PAQUETE() {
        return BUSCAR_PK_REPETIDA_PAQUETE;
    }

    public static String getINSERTAR_CIUDAD() {
        return INSERTAR_CIUDAD;
    }

    public static String getSELECCIONAR_CIUDAD() {
        return SELECCIONAR_CIUDAD;
    }

    public static String getSELECCIONAR_CIUDADES() {
        return SELECCIONAR_CIUDADES;
    }

    public static String getBUSCAR_PK_REPETIDA_CIUDAD() {
        return BUSCAR_PK_REPETIDA_CIUDAD;
    }

    public static String getACTUALIZAR_CIUDAD() {
        return ACTUALIZAR_CIUDAD;
    }

    public static String getBORRAR_CIUDAD() {
        return BORRAR_CIUDAD;
    }
    
}