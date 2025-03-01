package com.fundorotativo.fundo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoHandler {

    private static final String ARQUIVO_SOLICITANTES = "solicitantes.txt";
    private static final String ARQUIVO_VOLUNTARIOS = "voluntarios.txt";
    private static final String ARQUIVO_EMPRESTIMOS = "emprestimos.txt"; // Novo arquivo de empréstimos
    private static final String ARQUIVO_CONTRATOS = "contratos.txt"; // Arquivo de contratos
    private static final String ARQUIVO_RELATORIO = "relatorio.txt"; // Arquivo para controle do fundo solidário

    // Método para verificar se o arquivo existe. Se não, cria o arquivo
    private static void verificarArquivoExistente(String arquivo) {
        File file = new File(arquivo);
        if (!file.exists()) {
            try {
                file.createNewFile();  // Cria o arquivo se ele não existir
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para ler o valor do fundo solidário do arquivo
    private static double lerFundoSolidario() {
        verificarArquivoExistente(ARQUIVO_RELATORIO);  // Verifica se o arquivo existe
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_RELATORIO))) {
            String linha = reader.readLine();
            if (linha != null) {
                String[] dados = linha.split(": ");
                if (dados.length > 1) {
                    return Double.parseDouble(dados[1].trim()); // Retorna o valor do fundo
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 20000.0; // Retorna 20.000 se não for possível ler o valor (valor inicial)
    }

    // Método para atualizar o valor do fundo solidário no arquivo
    private static void atualizarFundoSolidario(double novoValor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_RELATORIO))) {
            writer.write("Fundo Solidário: " + novoValor);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para salvar solicitante no arquivo
    public static void salvarSolicitante(Solicitante solicitante) {
        verificarArquivoExistente(ARQUIVO_SOLICITANTES);

        // Verifica se o CPF já existe no arquivo
        if (!cpfExistente(ARQUIVO_SOLICITANTES, solicitante.getCpf())) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_SOLICITANTES, true))) {
                writer.write(solicitante.toString());  // Salva no arquivo
                writer.newLine();  // Adiciona uma nova linha
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O CPF já existe no arquivo!");
        }
    }

    // Método para verificar se o CPF já existe no arquivo
    private static boolean cpfExistente(String arquivo, String cpf) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[1].equals(cpf)) {
                    return true;  // CPF já existe no arquivo
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Método para ler todos os solicitantes do arquivo
    public static List<Solicitante> lerSolicitantes() {
        List<Solicitante> solicitantes = new ArrayList<>();
        verificarArquivoExistente(ARQUIVO_SOLICITANTES);  // Verifica se o arquivo existe

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_SOLICITANTES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Solicitante solicitante = Solicitante.fromString(linha);  // Cria o solicitante a partir da linha
                solicitantes.add(solicitante);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return solicitantes;
    }

    // Método para salvar voluntário no arquivo
    public static void salvarVoluntario(Voluntario voluntario) {
        verificarArquivoExistente(ARQUIVO_VOLUNTARIOS);

        // Verifica se o CPF já existe no arquivo
        if (!cpfExistente(ARQUIVO_VOLUNTARIOS, voluntario.getCpf())) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_VOLUNTARIOS, true))) {
                writer.write(voluntario.toString());  // Salva no arquivo
                writer.newLine();  // Adiciona uma nova linha
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O CPF do voluntário já existe no arquivo!");
        }
    }

    // Método para ler todos os voluntários do arquivo
    public static List<Voluntario> lerVoluntarios() {
        List<Voluntario> voluntarios = new ArrayList<>();
        verificarArquivoExistente(ARQUIVO_VOLUNTARIOS);  // Verifica se o arquivo existe

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_VOLUNTARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Voluntario voluntario = Voluntario.fromString(linha);  // Cria o voluntário a partir da linha
                voluntarios.add(voluntario);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voluntarios;
    }

    // Método para salvar o empréstimo no arquivo
    public static void salvarEmprestimo(Emprestimo emprestimo) {
        verificarArquivoExistente(ARQUIVO_EMPRESTIMOS);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_EMPRESTIMOS, true))) {
            writer.write(emprestimo.toString());  // Salva o empréstimo no arquivo
            writer.newLine();  // Adiciona uma nova linha

            // Atualiza o fundo solidário, retirando o valor do empréstimo
            double fundoSolidario = lerFundoSolidario();
            double novoFundo = fundoSolidario - emprestimo.getValorEmprestimo();
            atualizarFundoSolidario(novoFundo);  // Atualiza o fundo solidário

            // Agora salvamos o contrato associado a este empréstimo
            Contrato contrato = new Contrato(
                    emprestimo.getSolicitante().getNome(),
                    "Parcela 1", // Exemplo
                    emprestimo.getDataInicial().toString(),
                    emprestimo.getValorEmprestimo() / emprestimo.getParcelas(),
                    emprestimo.getId()
            );

            // Salvando o contrato
            salvarContrato(contrato);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para salvar o contrato no arquivo
    public static void salvarContrato(Contrato contrato) {
        verificarArquivoExistente(ARQUIVO_CONTRATOS);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTRATOS, true))) {
            writer.write(contrato.toString());  // Salva o contrato no arquivo
            writer.newLine();  // Adiciona uma nova linha
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ler todos os contratos do arquivo
    public static List<Contrato> lerContratos() {
        List<Contrato> contratos = new ArrayList<>();
        verificarArquivoExistente(ARQUIVO_CONTRATOS);

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTRATOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Contrato contrato = Contrato.fromString(linha);  // Cria o contrato a partir da linha
                contratos.add(contrato);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contratos;
    }

    // Método para remover solicitante do arquivo
    public static void removerSolicitante(Solicitante solicitante) {
        List<Solicitante> solicitantes = lerSolicitantes();
        solicitantes.removeIf(s -> s.getCpf().equals(solicitante.getCpf()));

        // Reescrever o arquivo com a lista atualizada
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_SOLICITANTES))) {
            for (Solicitante s : solicitantes) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para remover voluntário do arquivo
    public static void removerVoluntario(Voluntario voluntario) {
        List<Voluntario> voluntarios = lerVoluntarios();
        voluntarios.removeIf(v -> v.getCpf().equals(voluntario.getCpf()));

        // Reescrever o arquivo com a lista atualizada
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_VOLUNTARIOS))) {
            for (Voluntario v : voluntarios) {
                writer.write(v.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para remover empréstimo do arquivo
    public static void removerEmprestimo(Emprestimo emprestimo) {
        List<Emprestimo> emprestimos = lerEmprestimos();
        emprestimos.removeIf(e -> e.getId() == emprestimo.getId());

        // Reescrever o arquivo com a lista atualizada
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_EMPRESTIMOS))) {
            for (Emprestimo e : emprestimos) {
                writer.write(e.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ler todos os empréstimos do arquivo
    public static List<Emprestimo> lerEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        verificarArquivoExistente(ARQUIVO_EMPRESTIMOS);  // Verifica se o arquivo existe

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_EMPRESTIMOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Emprestimo emprestimo = Emprestimo.fromString(linha);  // Cria o empréstimo a partir da linha
                emprestimos.add(emprestimo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }


    // Método para remover contrato do arquivo
    public static void removerContrato(Contrato contrato) {
        List<Contrato> contratos = lerContratos();
        contratos.removeIf(c -> c.getNome().equals(contrato.getNome()) && c.getParcela().equals(contrato.getParcela()));

        // Reescrever o arquivo com a lista atualizada
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTRATOS))) {
            for (Contrato c : contratos) {
                writer.write(c.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para editar contrato no arquivo (para marcar como pago)
    public static void editarContrato(Contrato contrato) {
        List<Contrato> contratos = lerContratos();
        for (int i = 0; i < contratos.size(); i++) {
            if (contratos.get(i).getIdEmprestimo() == contrato.getIdEmprestimo() && contratos.get(i).getParcela().equals(contrato.getParcela())) {
                contratos.set(i, contrato);  // Atualiza o contrato com o novo status
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTRATOS))) {
            for (Contrato c : contratos) {
                writer.write(c.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para editar solicitante no arquivo
    public static void editarSolicitante(Solicitante solicitante) {
        List<Solicitante> solicitantes = lerSolicitantes();
        for (int i = 0; i < solicitantes.size(); i++) {
            if (solicitantes.get(i).getCpf().equals(solicitante.getCpf())) {
                solicitantes.set(i, solicitante);  // Substitui o solicitante
            }
        }

        // Reescrever o arquivo com os dados atualizados
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_SOLICITANTES))) {
            for (Solicitante s : solicitantes) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para editar voluntário no arquivo
    public static void editarVoluntario(Voluntario voluntario) {
        List<Voluntario> voluntarios = lerVoluntarios();
        for (int i = 0; i < voluntarios.size(); i++) {
            if (voluntarios.get(i).getCpf().equals(voluntario.getCpf())) {
                voluntarios.set(i, voluntario);  // Substitui o voluntário
            }
        }

        // Reescrever o arquivo com os dados atualizados
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_VOLUNTARIOS))) {
            for (Voluntario v : voluntarios) {
                writer.write(v.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para editar empréstimo no arquivo
    public static void editarEmprestimo(Emprestimo emprestimo) {
        List<Emprestimo> emprestimos = lerEmprestimos();
        for (int i = 0; i < emprestimos.size(); i++) {
            if (emprestimos.get(i).getId() == emprestimo.getId()) {
                emprestimos.set(i, emprestimo);  // Substitui o empréstimo
            }
        }

        // Reescrever o arquivo com os dados atualizados
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_EMPRESTIMOS))) {
            for (Emprestimo e : emprestimos) {
                writer.write(e.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
