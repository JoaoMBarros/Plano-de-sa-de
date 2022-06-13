package prova2.gabarito.plano;

public class Exame extends Procedimento {
    int carencia;
    int periodicidade;
    double porte;
    double custoOperacional;

    public Exame(int codigo, String nome, int idadeMin, int idadeMax, int genero, int carencia, int periodicidade, double porte, double custoOperacional) {
        super(nome, codigo, idadeMin, idadeMax, genero);
        this.carencia = carencia;
        this.periodicidade = periodicidade;
        this.porte = porte;
        this.custoOperacional = custoOperacional;
    }

    public int getCarencia() {
        return carencia;
    }

    public int getPeriodicidade() {
        return periodicidade;
    }

    public double getPorte() {
        return porte;
    }

    public double getCustoOperacional() { return custoOperacional; }

}
