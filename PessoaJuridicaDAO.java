package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    private final ConectorBD conector;

    public PessoaJuridicaDAO() {
        this.conector = new ConectorBD();
    }

    public PessoaJuridica getPessoa(int id) {
        PessoaJuridica pessoaJuridica = null;
        String sql = "SELECT * FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id WHERE p.id = ?";

        try (Connection conn = conector.getConnection();
             PreparedStatement stmt = conector.getPrepared(conn, sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pessoaJuridica = new PessoaJuridica(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoaJuridica;
    }

    public List<PessoaJuridica> getPessoas() {
        List<PessoaJuridica> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id";

        try (Connection conn = conector.getConnection();
             PreparedStatement stmt = conector.getPrepared(conn, sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")
                );
                lista.add(pessoaJuridica);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public void incluir(PessoaJuridica pessoaJuridica) {
        String sqlPessoa = "INSERT INTO Pessoa (id, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (id, cnpj) VALUES (?, ?)";

        try (Connection conn = conector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPessoa = conector.getPrepared(conn, sqlPessoa);
                 PreparedStatement stmtPessoaJuridica = conector.getPrepared(conn, sqlPessoaJuridica)) {

                int novoId = SequenceManager.getValue("seq_pessoa");
                stmtPessoa.setInt(1, novoId);
                stmtPessoa.setString(2, pessoaJuridica.getNome());
                stmtPessoa.setString(3, pessoaJuridica.getLogradouro());
                stmtPessoa.setString(4, pessoaJuridica.getCidade());
                stmtPessoa.setString(5, pessoaJuridica.getEstado());
                stmtPessoa.setString(6, pessoaJuridica.getTelefone());
                stmtPessoa.setString(7, pessoaJuridica.getEmail());
                stmtPessoa.executeUpdate();

                stmtPessoaJuridica.setInt(1, novoId);
                stmtPessoaJuridica.setString(2, pessoaJuridica.getCnpj());
                stmtPessoaJuridica.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterar(PessoaJuridica pessoaJuridica) {
        String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE id = ?";
        String sqlPessoaJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE id = ?";

        try (Connection conn = conector.getConnection();
             PreparedStatement stmtPessoa = conector.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaJuridica = conector.getPrepared(conn, sqlPessoaJuridica)) {

            stmtPessoa.setString(1, pessoaJuridica.getNome());
            stmtPessoa.setString(2, pessoaJuridica.getLogradouro());
            stmtPessoa.setString(3, pessoaJuridica.getCidade());
            stmtPessoa.setString(4, pessoaJuridica.getEstado());
            stmtPessoa.setString(5, pessoaJuridica.getTelefone());
            stmtPessoa.setString(6, pessoaJuridica.getEmail());
            stmtPessoa.setInt(7, pessoaJuridica.getId());
            stmtPessoa.executeUpdate();

            stmtPessoaJuridica.setString(1, pessoaJuridica.getCnpj());
            stmtPessoaJuridica.setInt(2, pessoaJuridica.getId());
            stmtPessoaJuridica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sqlPessoa = "DELETE FROM Pessoa WHERE id = ?";
        String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE id = ?";

        try (Connection conn = conector.getConnection();
             PreparedStatement stmtPessoa = conector.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaJuridica = conector.getPrepared(conn, sqlPessoaJuridica)) {

            stmtPessoaJuridica.setInt(1, id);
            stmtPessoaJuridica.executeUpdate();

            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
