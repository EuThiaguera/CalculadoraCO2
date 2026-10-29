package br.com.fiap.model;

public class CalculadoraCO2 {
    public static final int PONTOS_POR_KM = 3;
    public static final double CASHBACK_POR_PONTO = 0.01;

    private Long id;
    private String nome;
    private String tipoTransporte;
    private double distanciaKm;
    private double co2Economizado;
    private int pontos;
    private double cashback;

    public CalculadoraCO2() {
    }

    public CalculadoraCO2(String nome, String tipoTransporte, double distanciaKm) {
        this.nome = nome;
        this.tipoTransporte = tipoTransporte;
        this.distanciaKm = distanciaKm;
    }

    public double calcularCO2(Transporte transporte) {
        co2Economizado = Math.round(transporte.calcularCO2(distanciaKm) * 1000.0) / 1000.0;
        return co2Economizado;
    }

    public int calcularPontos() {
        pontos = (int) (distanciaKm * PONTOS_POR_KM);
        return pontos;
    }

    public double calcularCashback() {
        cashback = Math.round(pontos * CASHBACK_POR_PONTO * 100.0) / 100.0;
        return cashback;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipoTransporte() { return tipoTransporte; }
    public void setTipoTransporte(String tipoTransporte) { this.tipoTransporte = tipoTransporte; }

    public double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }

    public double getCo2Economizado() { return co2Economizado; }
    public void setCo2Economizado(double co2Economizado) { this.co2Economizado = co2Economizado; }

    public int getPontos() { return pontos; }
    public void setPontos(int pontos) { this.pontos = pontos; }

    public double getCashback() { return cashback; }
    public void setCashback(double cashback) { this.cashback = cashback; }
}