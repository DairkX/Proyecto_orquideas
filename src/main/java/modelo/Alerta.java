package modelo;

import java.sql.Date;
import java.sql.Time;

public class Alerta {
    private int idAlerta; // Identificador único de la alerta
    private int idCondicion; // Identificador de la condición asociada
    private String mensaje; // Mensaje descriptivo de la alerta
    private Date fecha; // Fecha de la alerta
    private Time hora; // Hora de la alerta

    // Constructor vacío
    public Alerta() {}

    // Constructor con parámetros
    public Alerta(int idAlerta, int idCondicion, String mensaje, Date fecha, Time hora) {
        this.idAlerta = idAlerta;
        this.idCondicion = idCondicion;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y Setters
    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    public int getIdCondicion() {
        return idCondicion;
    }

    public void setIdCondicion(int idCondicion) {
        this.idCondicion = idCondicion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    // Método opcional para depuración
    @Override
    public String toString() {
        return "Alerta{" +
                "idAlerta=" + idAlerta +
                ", idCondicion=" + idCondicion +
                ", mensaje='" + mensaje + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
