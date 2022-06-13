package prova2.gabarito.plano;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HistoricoDAO {
    GregorianCalendar gc = new GregorianCalendar();
    ProcedimentoDAO pesquisaProcedimento = new ProcedimentoDAO();

    public void inserirRegistro(Registro r){

        try {
            Connection conexao = Conexao.getConexao();
            Statement st = conexao.createStatement();
            String comandoInserirHistorico = "INSERT INTO historico (beneficiario, procedimento, data) VALUES ( " + r.getBeneficiario() + ", " +
                    r.getProcedimento() + ", " + r.getData().getTime() + ")";
            st.executeUpdate(comandoInserirHistorico);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public long dataUltimoProcedimento(int cpf, int cod){
        long data = 0;
        try {
            Connection conexao = Conexao.getConexao();
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM historico WHERE beneficiario = " + cpf + " AND procedimento = " + cod);

            if(rs != null && rs.next()){
                data = rs.getLong("data");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return data;
    }

    public int quantidadeProcedimentos(int cpf, int cod){
        int quant = 0;
        try {
            Connection conexao = Conexao.getConexao();
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM historico WHERE beneficiario = " + cpf + " AND procedimento = " + cod);

            while(rs.next()){
                quant++;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return quant;
    }


    public double faturamentoMes(int mes, int ano){

        //Pegando o primeiro e ultimo dia do determinado mes e ano
        gc.set(ano, mes-1, 1);
        Long primeiroDia = new Long(gc.getTimeInMillis());
        gc.set(ano, mes-1, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
        Long ultimoDia = new Long(gc.getTimeInMillis());

        double faturamento = 0;
        try {
            Connection conexao = Conexao.getConexao();
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM historico");
            while(rs.next()){
                Long data = new Long(rs.getLong("data"));
                if(data.compareTo(primeiroDia) > 0 && data.compareTo(ultimoDia) <= 0){
                    Procedimento p = pesquisaProcedimento.pesquisarProcedimento(rs.getInt("procedimento"));
                    if(p instanceof Exame){
                        faturamento += ((Exame)p).getPorte() * 100 + ((Exame)p).getCustoOperacional();
                    }
                    else if(p instanceof Cirurgia){
                        faturamento += ((Cirurgia)p).getHonorario() + ((Cirurgia)p).getMaterial();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return faturamento;
    }
}
