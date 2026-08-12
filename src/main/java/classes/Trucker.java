package classes;


public class Trucker {

    private String rfc;
    private String nombre;
    private int telefono;
    private String direccion;
    private float salario;
    private String poblacion;

    public Trucker() {
    }

    public Trucker(String rfc, String nombre, int telefono, String direccion,
            float salario, String poblacion) {
        this.rfc = rfc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.salario = salario;
        this.poblacion = poblacion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String[] toArrayStrings() {
        return new String[]{this.rfc, this.nombre, String.valueOf(this.telefono),
            this.direccion, String.valueOf(this.salario), this.poblacion};
    }

}
