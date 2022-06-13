package prova2.gabarito.plano;

import java.util.ArrayList;

public class ListarProcedimentos {

    private ArrayList<Procedimento> array = new ArrayList<>();

    public void inserirProcedimento(Procedimento a){
        try{
            int aux = array.indexOf(pesquisarProcedimento(a.getCodigo()));
            array.set(aux, a);
        }catch(ProcedimentoInexistenteException e){
            array.add(a);
        }
    }

    public Procedimento pesquisarProcedimento(int cod) throws ProcedimentoInexistenteException{
        for(Procedimento e : array){
            if(e.getCodigo() == cod){
                return e;
            }
        }
        throw new ProcedimentoInexistenteException();
    }

}
