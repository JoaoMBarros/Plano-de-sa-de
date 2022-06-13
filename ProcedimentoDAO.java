package prova2.gabarito.plano;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProcedimentoDAO {

    public void inserirProcedimento(Procedimento p) {

        try {
            Connection conexao = Conexao.getConexao();
            Statement st = conexao.createStatement();

                //Caso o procedimento exista, é substituido
                String removeProcedimento = "DELETE FROM procedimento WHERE codigo = " + p.getCodigo();
                st.executeUpdate(removeProcedimento);

                if (p instanceof Exame) {
                    String comandoInserirExame = "INSERT INTO procedimento (codigo, nome, idadeMin, idadeMax, genero, carencia, periodicidade, porte, custooperacional, discriminador, unicidade, honorario, material) VALUES (" +
                            p.getCodigo() + ", '" + p.getNome() + "', " + p.getIdadeMin() + ", " + p.getIdadeMax() + ", " + p.getGenero() + ", " + ((Exame) p).getCarencia() + ", " +
                            ((Exame) p).getPeriodicidade() + ", " + ((Exame) p).getPorte() + ", " + ((Exame) p).getCustoOperacional() + ", " + 1 + ", 0, 0, 0)";
                    st.executeUpdate(comandoInserirExame);
                } else if (p instanceof Cirurgia) {
                    String comandoInserirCirurgia = "INSERT INTO procedimento (codigo, nome, idademin, idademax, genero, unicidade, honorario, material, discriminador, carencia, periodicidade, porte, custooperacional) VALUES (" + p.getCodigo() + ", '" + p.getNome() + "', " +
                            p.getIdadeMin() + ", " + p.getIdadeMax() + ", " + p.getGenero() + ", " + ((Cirurgia) p).getUnicidade() + ", " + ((Cirurgia) p).getHonorario() + ", " +
                            ((Cirurgia) p).getMaterial() + ", " + 2 + ", 0, 0, 0, 0)";
                    st.executeUpdate(comandoInserirCirurgia);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    public Procedimento pesquisarProcedimento(int cod) throws ProcedimentoInexistenteException{

        try{
            Connection conexao = Conexao.getConexao();
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM procedimento WHERE codigo = " + cod);

            if(rs.next()){
                int codigo = rs.getInt("codigo");
                String nome = rs.getString("nome");
                int idadeMin = rs.getInt("idademin");
                int idadeMax = rs.getInt("idademax");
                int genero = rs.getInt("genero");
                int discriminador = rs.getInt("discriminador");

                if(discriminador == 1){
                    int carencia = rs.getInt("carencia");
                    int periodicidade = rs.getInt("periodicidade");
                    double porte = rs.getDouble("porte");
                    double custoOperacional = rs.getDouble("custooperacional");
                    return new Exame(codigo, nome, idadeMin, idadeMax, genero, carencia, periodicidade, porte, custoOperacional);
                }else if(discriminador == 2){
                    int unicidade = rs.getInt("unicidade");
                    double honorario = rs.getDouble("honorario");
                    double material = rs.getDouble("material");
                    return new Cirurgia(codigo, nome, idadeMin, idadeMax, genero, unicidade, honorario, material);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        throw new ProcedimentoInexistenteException();
    }
}
