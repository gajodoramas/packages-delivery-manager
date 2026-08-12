package classes;


public class Package {
    
    private int codigopaquete;
    private String descripcion;
    private String destino;
    private String direcciondestinatario;
    private int apartadopostalciudad;
    private String rfccamionero;

    public Package() {
    }

    public Package(String descripcion, String destino, String direcciondestinatario,
            int apartadopostalciudad, String rfccamionero) {
        this.descripcion = descripcion;
        this.destino = destino;
        this.direcciondestinatario = direcciondestinatario;
        this.apartadopostalciudad = apartadopostalciudad;
        this.rfccamionero = rfccamionero;
    }

    
    
    public Package(int codigopaquete, String descripcion, String destino,
            String direcciondestinatario, int apartadopostalciudad, String rfccamionero) {
        this.codigopaquete = codigopaquete;
        this.descripcion = descripcion;
        this.destino = destino;
        this.direcciondestinatario = direcciondestinatario;
        this.apartadopostalciudad = apartadopostalciudad;
        this.rfccamionero = rfccamionero;
    }

    public int getCodigopaquete() {
        return codigopaquete;
    }

    public void setCodigopaquete(int codigopaquete) {
        this.codigopaquete = codigopaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDirecciondestinatario() {
        return direcciondestinatario;
    }

    public void setDirecciondestinatario(String direcciondestinatario) {
        this.direcciondestinatario = direcciondestinatario;
    }

    public int getApartadopostalciudad() {
        return apartadopostalciudad;
    }

    public void setApartadopostalciudad(int apartadopostalciudad) {
        this.apartadopostalciudad = apartadopostalciudad;
    }

    public String getRfccamionero() {
        return rfccamionero;
    }

    public void setRfccamionero(String rfccamionero) {
        this.rfccamionero = rfccamionero;
    }
    
        public String[] toArrayStrings() {
        return new String[] {String.valueOf(this.codigopaquete), this.descripcion,
            this.destino, this.direcciondestinatario,
            String.valueOf(this.apartadopostalciudad), this.rfccamionero};
    }
    
}
