package classes;


public class Assignment {
    
    private String rfccamionero;
    private String placacamion;
    private String fecha;

    public Assignment() {
    }

    public Assignment(String rfccamionero, String placacamion, String fecha) {
        this.rfccamionero = rfccamionero;
        this.placacamion = placacamion;
        this.fecha = fecha;
    }

    public String getRfccamionero() {
        return rfccamionero;
    }

    public void setRfccamionero(String rfccamionero) {
        this.rfccamionero = rfccamionero;
    }

    public String getPlacacamion() {
        return placacamion;
    }

    public void setPlacacamion(String placacamion) {
        this.placacamion = placacamion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
     public String[] toArrayStrings() {
        return new String[] {this.placacamion, this.rfccamionero, this.fecha};
    }
    
}
