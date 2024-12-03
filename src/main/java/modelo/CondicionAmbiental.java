package modelo;

public class CondicionAmbiental {
    private int idCondicion;
    private int idSensor;
    private java.sql.Timestamp fechaHora;
    private float valor;

    // Constructor
    public CondicionAmbiental(int idCondicion, int idSensor, java.sql.Timestamp fechaHora, float valor) {
        this.idCondicion = idCondicion;
        this.idSensor = idSensor;
        this.fechaHora = fechaHora;
        this.valor = valor;
    }

    // Getters y setters
    public int getIdCondicion() {
        return idCondicion;
    }

    public void setIdCondicion(int idCondicion) {
        this.idCondicion = idCondicion;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public java.sql.Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(java.sql.Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
