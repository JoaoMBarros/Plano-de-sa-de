package prova2.gabarito.plano;

import java.sql.*;
import java.util.Date;

public class BeneficiarioDAO {

    public void inserirBeneficiario(Beneficiario b) throws BeneficiarioExistenteException {

        try{
            pesquisarBeneficiario(b.getCpf());
            throw new BeneficiarioExistenteException();
        } catch (BeneficiarioInexistenteException e) {
            //
        }
            try{
                Connection conexao = Conexao.getConexao();
                Statement st = conexao.createStatement();
                String comandoInserir = "INSERT INTO beneficiario (cpf, nome, ingressoplano, nasc, genero) VALUES (" +
                        b.getCpf() + ", '" + b.getNome() + "', " + b.getIngressoNoPlano().getTime() + ", " + b.getNasc().getTime() + ", " + b.getGenero() + ")";
                st.executeUpdate(comandoInserir);
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    public Beneficiario pesquisarBeneficiario(int cpf) throws BeneficiarioInexistenteException{
        try {
            Connection conexao = Conexao.getConexao();
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM beneficiario WHERE cpf = " + cpf);
            if(rs.next()){
                String nome = rs.getString("nome");
                long ingresso = rs.getLong("ingressoplano");
                long nascBanco = rs.getLong("nasc");
                int genero = rs.getInt("genero");
                Date ingressoNoPlano = new Date(ingresso);
                Date nasc = new Date(nascBanco);
                return new Beneficiario(cpf, nome, ingressoNoPlano, nasc, genero);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        throw new BeneficiarioInexistenteException();
    }
}
