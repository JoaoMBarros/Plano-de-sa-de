package prova2.gabarito.plano;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ControlePlanoDeSaude extends PlanoDeSaude {

    ListaDeBeneficiarios clientes = new ListaDeBeneficiarios();
    BeneficiarioDAO clientesDAO = new BeneficiarioDAO();

    ListarProcedimentos procedimento = new ListarProcedimentos();
    ProcedimentoDAO procedimentosDAO = new ProcedimentoDAO();

//    Historico historico = new Historico();
    HistoricoDAO historicoDAO = new HistoricoDAO();

    Calendar calendar = Calendar.getInstance();
    Date hoje = calendar.getTime();

    @Override
    public void cadastrarBeneficiario(Beneficiario b) throws BeneficiarioExistenteException, DadosInvalidosException, IdadeInvalidaException {

        if(b.getNasc() == null || b.getIngressoNoPlano() == null){
            throw new DadosInvalidosException();
        }
        else if(calculaIdade(b.getNasc()) < 18 || calculaIdade(b.getNasc()) > 65){
            throw new IdadeInvalidaException();
        }else if(b.getGenero() < 1 || b.getGenero() > 2){
            throw new DadosInvalidosException();
        }
        else if(hoje.before(b.getIngressoNoPlano())){
            throw new DadosInvalidosException();
        }
        else if(b.getNome() == null){
            throw new DadosInvalidosException();
        }
        else if(b.getNome().trim().isEmpty()){
            throw new DadosInvalidosException();
        }
        else if(b.getCpf() < 1){
            throw new DadosInvalidosException();
        }
        clientesDAO.inserirBeneficiario(b);
    }

    @Override
    public void salvarProcedimento(Procedimento p) throws DadosInvalidosException{

        if(p.getCodigo() < 0){
            throw new DadosInvalidosException();
        }
        else if(p.getNome() == null || p.getNome().trim().isEmpty()){
            throw new DadosInvalidosException();
        }
        else if(p.getIdadeMin() < 0 || p.getIdadeMax() < 0 || p.getIdadeMax() < p.getIdadeMin()){
            throw new DadosInvalidosException();
        }
        else if(p.getGenero() > 3 || p.getGenero() < 1){
            throw new DadosInvalidosException();
        }
        else if(p instanceof Exame){
            if(((Exame)p).getPeriodicidade() < 0 || ((Exame)p).getCarencia() < 0
                    || ((Exame)p).getPorte() < 0 || ((Exame)p).getCustoOperacional() < 0) {
                throw new DadosInvalidosException();
            }
        }
        else if(p instanceof Cirurgia) {
            if (((Cirurgia) p).getUnicidade() < 0 || ((Cirurgia) p).getHonorario() < 0
                    || ((Cirurgia) p).getMaterial() < 0) {
                throw new DadosInvalidosException();
            }
        }

        procedimentosDAO.inserirProcedimento(p);
    }

    @Override
    public void solicitar(int cpf, int codProcedimento, Date data) throws IdadeInvalidaException, GeneroInvalidoException, CarenciaException, PeriodicidadeException, UnicidadeException, BeneficiarioInexistenteException, ProcedimentoInexistenteException {
        Beneficiario cliente = clientesDAO.pesquisarBeneficiario(cpf);
        Procedimento procedimentoSolicitado = procedimentosDAO.pesquisarProcedimento(codProcedimento);
        Registro add = new Registro(cpf, codProcedimento, data);

            if(cliente == null){
                throw new BeneficiarioInexistenteException();
            }
            else if(calculaIdade(cliente.getNasc()) < procedimentoSolicitado.getIdadeMin() ||
                    calculaIdade(cliente.getNasc()) > procedimentoSolicitado.getIdadeMax()){
                throw new IdadeInvalidaException();
            }
            else if(procedimentoSolicitado.getGenero() != 3 && cliente.getGenero() != procedimentoSolicitado.getGenero()){
                throw new GeneroInvalidoException();
            }
            else if(procedimentoSolicitado instanceof Exame){
                long ultimoExameLong = historicoDAO.dataUltimoProcedimento(cliente.getCpf(), procedimentoSolicitado.getCodigo());
                Date ultimoExame = new Date(ultimoExameLong);
                if((difDatas(data, cliente.getIngressoNoPlano())) < ((Exame) procedimentoSolicitado).getCarencia()){
                    throw new CarenciaException();
                }
                else if(ultimoExameLong != 0 && difDatas(data, ultimoExame) < ((Exame) procedimentoSolicitado).getPeriodicidade()) {
                    throw new PeriodicidadeException();
                }
            } else if(procedimentoSolicitado instanceof Cirurgia){
                if(historicoDAO.quantidadeProcedimentos(cpf, codProcedimento) >= ((Cirurgia) procedimentoSolicitado).getUnicidade()){
                    throw new UnicidadeException();
                }
            }

            historicoDAO.inserirRegistro(add);
    }

    @Override
    public double faturar(int mes, int ano) {
        return historicoDAO.faturamentoMes(mes, ano);
    }

    @Override
    public Procedimento pesquisarProcedimento(int cod) throws ProcedimentoInexistenteException {
        return procedimentosDAO.pesquisarProcedimento(cod);
    }

    @Override
    public Beneficiario pesquisarBeneficiario(int cpf) throws BeneficiarioInexistenteException {
        return clientesDAO.pesquisarBeneficiario(cpf);
    }

    protected int difDatas(Date dt1, Date dt2) {
        long tempo1 = dt1.getTime();
        long tempo2 = dt2.getTime();
        long difTempo = tempo1 - tempo2;
        return (int) ((difTempo + 60L * 60 * 1000) / (24L * 60 * 60 * 1000));
    }

    protected int calculaIdade(Date dataNasc) {
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNasc);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        dateOfBirth.add(Calendar.YEAR, age);
        if (today.before(dateOfBirth)) {
            age--;
        }
        return age;
    }

}
