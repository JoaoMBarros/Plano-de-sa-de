package prova2.gabarito.plano;

import java.util.ArrayList;


public class ListaDeBeneficiarios {

    private ArrayList<Beneficiario> array = new ArrayList<>();

    public void inserirBeneficiario(Beneficiario c) throws BeneficiarioExistenteException {
        try {
            pesquisarBeneficiario(c.cpf);
            throw new BeneficiarioExistenteException();
        }catch(BeneficiarioInexistenteException e){
            array.add(c);
        }
    }

    public Beneficiario pesquisarBeneficiario(int cpf) throws BeneficiarioInexistenteException {
        for (Beneficiario c : array) {
            if (c.getCpf() == cpf) {
                return c;
            }
        }
        throw new BeneficiarioInexistenteException();
    }

}