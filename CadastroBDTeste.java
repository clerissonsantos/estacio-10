public class CadastroBDTeste {
    public static void main(String[] args) {
        PessoaFisica pessoaFisica = new PessoaFisica(
            21, 
            "JJC", 
            "Rua 11, Centro", 
            "Riacho do Sul", 
            "PA", 
            "1212-1212", 
            "jjc@riacho.com", 
            "11111111111"
        );

        PessoaJuridica pessoaJuridica = new PessoaJuridica(
            1, 
            "João Ltda", 
            "Rua 11, Centro", 
            "Riacho do Sul", 
            "PA", 
            "1111-1111", 
            "joao@riacho.com", 
            "11111111111111"
        );

        System.out.println("Dados de Pessoa Física:");
        pessoaFisica.imprimirInformacoes();

        System.out.println("\nDados de Pessoa Jurídica:");
        pessoaJuridica.imprimirInformacoes();
    }
}
