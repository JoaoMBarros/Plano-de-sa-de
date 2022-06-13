package prova2.gabarito.plano;

public class Cirurgia extends Procedimento{
    int unicidade;
    double honorario;
    double material;

    public Cirurgia(int codigo, String nome, int idadeMin, int idadeMax, int genero, int unicidade, double honorario, double material) {
        super(nome, codigo, idadeMin, idadeMax, genero);
        this.unicidade = unicidade;
        this.honorario = honorario;
        this.material = material;
    }
    public int getUnicidade() {
        return unicidade;
    }

    public double getHonorario() { return honorario; }

    public double getMaterial() {
        return material;
    }

}
