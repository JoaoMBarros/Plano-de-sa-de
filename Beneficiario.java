package prova2.gabarito.plano;

import java.util.Date;

public class Beneficiario {
    int cpf;
    String nome;
    Date ingressoNoPlano;
    Date nasc;
    int genero;

    public Beneficiario(int cpf, String nome, Date ingressoNoPlano, Date nasc, int genero){
        this.cpf = cpf;
        this.nome = nome;
        this.ingressoNoPlano = ingressoNoPlano;
        this.nasc = nasc;
        this.genero = genero;
    }

    public int getCpf() { return cpf; }

    public String getNome() {
        return nome;
    }

    public Date getIngressoNoPlano() {
        return ingressoNoPlano;
    }

    public Date getNasc() {
        return nasc;
    }

    public int getGenero() {
        return genero;
    }
}
