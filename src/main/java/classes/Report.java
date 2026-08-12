package classes;


public class Report {
    private String camionero;
    private String camion;
    private String placa;
    private String destino;
    private String fecha;

    public Report() {
    }

    public Report(String camionero, String camion, String placa, String destino,
            String fecha) {
        this.camionero = camionero;
        this.camion = camion;
        this.destino = destino;
        this.fecha = fecha;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    

    public String getCamionero() {
        return camionero;
    }

    public void setCamionero(String camionero) {
        this.camionero = camionero;
    }

    public String getCamion() {
        return camion;
    }

    public void setCamion(String camion) {
        this.camion = camion;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String[] toArrayStrings() {
        return new String[]{this.camionero, this.camion, this.placa, this.destino,
            this.fecha};
    }
}
