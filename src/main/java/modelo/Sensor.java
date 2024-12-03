/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


public class Sensor {
    private int idSensor;
    private String tipoSensor;

    // Constructor vacío
    public Sensor() {}

    // Constructor con parámetros
    public Sensor(int idSensor, String tipoSensor) {
        this.idSensor = idSensor;
        this.tipoSensor = tipoSensor;
    }

    // Getters y Setters
    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }
}
