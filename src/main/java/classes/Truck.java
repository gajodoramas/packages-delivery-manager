package classes;


public class Truck {
    
    private String placa;
    private String modelo;
    private String tipo;
    private String potencia;

    public Truck() {
    }
    
    public Truck(String placa, String modelo, String tipo, String potencia) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
        this.potencia = potencia;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }
   
    public String[] toArrayStrings() {
        return new String[] {this.placa, this.modelo, this.tipo, this.potencia + "CV"};
    }
    
}
