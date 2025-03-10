package com.fundorotativo.fundo;

public class Maintest {
    public static void main(String[] args) {
        // Testando criação de Solicitante e Voluntário
        Solicitante solicitante = new Solicitante("João Silva", "12345678900", "1234567", "Rua A, 100", "Centro");
        Voluntario voluntario = new Voluntario("Maria Oliveira", "98765432100", "7654321", "Rua B, 200", "Bairro Novo");

        System.out.println("✅ Solicitante Criado: " + solicitante);
        System.out.println("✅ Voluntário Criado: " + voluntario);

        // Testando gravação no arquivo
        ArquivoHandler.salvarSolicitante(solicitante);
        ArquivoHandler.salvarVoluntario(voluntario);
        System.out.println("📁 Dados gravados no arquivo com sucesso!");

        // Testando leitura dos arquivos
        System.out.println("📖 Lista de Solicitantes:");
        for (Solicitante s : ArquivoHandler.lerSolicitantes()) {
            System.out.println(s);
        }

        System.out.println("📖 Lista de Voluntários:");
        for (Voluntario v : ArquivoHandler.lerVoluntarios()) {
            System.out.println(v);
        }

        // Criando um empréstimo
        Emprestimo emprestimo = new Emprestimo(solicitante, voluntario, 500.0, 2, java.time.LocalDate.now(), java.time.LocalDate.now().plusMonths(2), 10);
        ArquivoHandler.salvarEmprestimo(emprestimo);
        System.out.println("✅ Empréstimo criado e salvo!");

        // Lendo os empréstimos do arquivo
        System.out.println("📖 Lista de Empréstimos:");
        for (Emprestimo e : ArquivoHandler.lerEmprestimos()) {
            System.out.println(e);
        }

        // Testando pagamento de uma parcela
        Contrato contrato = new Contrato(solicitante.getNome(), "Parcela 1", java.time.LocalDate.now().plusMonths(1).toString(), 525.0, emprestimo.getId());
        ArquivoHandler.salvarContrato(contrato);
        System.out.println("✅ Primeira parcela salva!");

        ContratoController contratoController = new ContratoController();
        contratoController.onMarcarPagoButtonClick(); // Simula pagamento de uma parcela
        System.out.println("✅ Parcela marcada como paga!");

        // Verificando fundo solidário atualizado
        double fundoAtual = ArquivoHandler.lerFundoSolidario();
        System.out.println("💰 Fundo Solidário Atualizado: R$ " + fundoAtual);

        System.out.println("🚀 Testes finalizados com sucesso!");
    }
}
