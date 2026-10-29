package br.com.fiap.model;

public abstract class Transporte {
    private final double taxaCO2;

    protected Transporte(double taxaCO2) {
        this.taxaCO2 = taxaCO2;
    }

    public double calcularCO2(double distanciaKm) {
        return distanciaKm * taxaCO2;
    }

    public double getTaxaCO2() {
        return taxaCO2;
    }
}