package prova2.gabarito.plano;

public abstract class Procedimento {
    String nome;
    int idadeMin;
    int codigo;
    int idadeMax;
    int genero;

    public Procedimento(String nome, int codigo, int idadeMin, int idadeMax, int genero) {
        this.nome = nome;
        this.idadeMin = idadeMin;
        this.codigo = codigo;
        this.idadeMax = idadeMax;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public int getIdadeMin() {
        return idadeMin;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getIdadeMax() {
        return idadeMax;
    }

    public int getGenero() {
        return genero;
    }
}
