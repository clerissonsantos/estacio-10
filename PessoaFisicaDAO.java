package cadastrobd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import cadastrobd.model.util.ConectorBD;

public class PessoaFisicaDAO {
    private ConectorBD conector;

    public PessoaFisicaDAO() {
        this.conector = new ConectorBD();
    }

    public void incluir(PessoaFisica pessoaFisica) {
        String sql = "INSERT INTO Pessoa (nome, logradouro, numero, bairro, cidade, estado) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPF = "INSERT INTO PessoaFisica (cpf, id) VALUES (?, SCOPE_IDENTITY())";

        try (Connection con = conector.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, pessoaFisica.getNome());
                stmt.setString(2, pessoaFisica.getLogradouro());
                stmt.setInt(3, pessoaFisica.getNumero());
                stmt.setString(4, pessoaFisica.getBairro());
                stmt.setString(5, pessoaFisica.getCidade());
                stmt.setString(6, pessoaFisica.getEstado());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmtPF = con.prepareStatement(sqlPF)) {
                stmtPF.setString(1, pessoaFisica.getCpf());
                stmtPF.executeUpdate();
            }

            System.out.println("Pessoa física inserida no banco: " + pessoaFisica.getNome());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir pessoa física: " + e.getMessage(), e);
        }
    }
}
