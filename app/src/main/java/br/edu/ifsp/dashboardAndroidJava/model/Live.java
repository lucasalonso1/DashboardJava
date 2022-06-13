package br.edu.ifsp.dashboardAndroidJava.model;

public class Live {
    private String temperatura;
    private String velocidade;

    public Live(){
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(String velocidade) {
        this.velocidade = velocidade;
    }

    public Live(String temperatura, String velocidade){
        this.temperatura = temperatura;
        this.velocidade =  velocidade;
    }

}
