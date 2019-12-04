package com.example.webviewcaptains;

public class Posicion {

    private Double latitud;
    private Double longitud;

    public void Posicion() {

    }

    public void Posicion(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return this.latitud;
    }
    public Double getLongitud() {
        return this.longitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
