package prova2.gabarito.plano;

public class BeneficiarioExistenteException extends Exception{
    public BeneficiarioExistenteException(){
        super("Beneficiario ja cadastrado");
    }
}
