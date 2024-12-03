package modelo;

public class Orquidea {
    private int idOrquidea;
    private String nombreOrquidea;
    private String especie;

    // Constructor vacío
    public Orquidea() {}
    
 
    // Constructor completo
    public Orquidea(int idOrquidea, String nombreOrquidea) {
        this.idOrquidea = idOrquidea;
        this.nombreOrquidea = nombreOrquidea;
        this.especie = especie;
    }


    // Getters y setters
    public int getIdOrquidea() {
        return idOrquidea;
    }

    public void setIdOrquidea(int idOrquidea) {
        this.idOrquidea = idOrquidea;
    }

    public String getNombreOrquidea() {
        return nombreOrquidea;
    }

    public void setNombreOrquidea(String nombreOrquidea) {
        this.nombreOrquidea = nombreOrquidea;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}