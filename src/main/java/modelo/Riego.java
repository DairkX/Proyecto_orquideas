/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Riego {
    private int idRiego;
    private int idOrquidea;
    private String fechaHora;
    private String observaciones;

    // Constructor vacío
    public Riego() {}

    // Constructor con parámetros
    public Riego(int idRiego, int idOrquidea, String fechaHora, String observaciones) {
        this.idRiego = idRiego;
        this.idOrquidea = idOrquidea;
        this.fechaHora = fechaHora;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getIdRiego() {
        return idRiego;
    }

    public void setIdRiego(int idRiego) {
        this.idRiego = idRiego;
    }

    public int getIdOrquidea() {
        return idOrquidea;
    }

    public void setIdOrquidea(int idOrquidea) {
        this.idOrquidea = idOrquidea;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
