package prova2.gabarito.plano;

import java.util.Date;

public class Registro {
    int cpf;
    int procedimento;
    Date data;

    public Registro(int e, int f, Date d) {
        this.cpf = e;
        this.procedimento = f;
        this.data = d;
    }

    public int getBeneficiario() {
        return cpf;
    }

    public Date getData() { return data; }

    public int getProcedimento() { return procedimento; }

}
