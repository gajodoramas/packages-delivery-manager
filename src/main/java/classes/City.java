package classes;


public class City {

    private int apartadopostal;
    private String nombre;

    public City() {
    }

    public City(int apartadopostal, String nombre) {
        this.apartadopostal = apartadopostal;
        this.nombre = nombre;
    }

    public int getApartadopostal() {
        return apartadopostal;
    }

    public void setApartadopostal(int apartadopostal) {
        this.apartadopostal = apartadopostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String[] toArrayStrings() {
        return new String[]{String.valueOf(this.getApartadopostal()), this.nombre};
    }
}
