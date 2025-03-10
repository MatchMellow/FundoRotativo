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

            // verificar se o arquivo existe. Se não, cria o arquivo
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

            // Metodo para atualizar o valor do fundo solidário no arquivo
            static void atualizarFundoSolidario(double novoValor) {
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

            // verificar se o CPF já existe no arquivo
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


            // leitura para  todos os solicitantes do arquivo
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

            // Metodo para salvar voluntário no arquivo
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

            // Metodo para ler todos os voluntários do arquivo
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

            // Método para salvar o empréstimo no arquivo sem calcular juros novamente
            public static void salvarEmprestimo(Emprestimo emprestimo) {
                verificarArquivoExistente(ARQUIVO_EMPRESTIMOS);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_EMPRESTIMOS, true))) {
                    writer.write(emprestimo.toString());  // Salva o empréstimo no arquivo
                    writer.newLine();  // Adiciona uma nova linha

                    // Pegando diretamente o valor da parcela já calculado no EmprestimoController.java
                    double valorParcela = emprestimo.getValorParcela();

                    // Atualizar o fundo solidário, retirando apenas o valor do empréstimo (sem juros)
                    double fundoSolidario = lerFundoSolidario();
                    double novoFundo = fundoSolidario - emprestimo.getValorEmprestimo();  // Apenas o valor emprestado é descontado
                    atualizarFundoSolidario(novoFundo);  // Atualiza o fundo solidário com o novo valor

                    // Criar contratos corretamente com os valores já ajustados
                    for (int i = 1; i <= emprestimo.getParcelas(); i++) {
                        String parcelaText = "Parcela " + i;
                        String dataVencimento = emprestimo.getDataInicial().plusMonths(i - 1).toString();
                        Contrato contrato = new Contrato(
                                emprestimo.getSolicitante().getNome(),
                                parcelaText,
                                dataVencimento,
                                valorParcela,  // Valor correto da parcela sem aplicar juros novamente
                                emprestimo.getId()
                        );

                        // Salvando o contrato
                        salvarContrato(contrato);
                    }

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
                        Contrato contrato = Contrato.fromString(linha);
                        contratos.add(contrato);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return contratos;
            }

            // Método para salvar o contrato no arquivo
            public static void salvarContrato(Contrato contrato) {
                verificarArquivoExistente(ARQUIVO_CONTRATOS);

                // Evita duplicação de parcelas antes de salvar
                if (!contratoJaExiste(contrato.getIdEmprestimo(), Integer.parseInt(contrato.getParcela().replace("Parcela ", "")))) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTRATOS, true))) {
                        writer.write(contrato.toString());
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Exemplo de cálculo de juros - deve ser ajustado conforme seu modelo de negócio
            public static double calcularValorComJuros(double valorBase) {
                return valorBase; // Já foi calculado antes
            }



            // Metodo para remover solicitante do arquivo
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

            // Metodo para remover voluntário do arquivo
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


            // Metodo para editar contrato no arquivo (para marcar como pago)
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

                // Chamada para remover a parcela paga do banco de dados
                removerParcela(contrato);  // Remover a parcela paga do banco de dados
            }
            // Metodo para remover parcela do arquivo
            public static void removerParcela(Contrato contrato) {
                List<Contrato> contratos = lerContratos();  // Lê todos os contratos do arquivo
                contratos.removeIf(c -> c.getIdEmprestimo() == contrato.getIdEmprestimo() && c.getParcela().equals(contrato.getParcela()));

                // Reescrever o arquivo com a lista atualizada
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTRATOS))) {
                    for (Contrato c : contratos) {
                        writer.write(c.toString());  // Escreve o contrato atualizado no arquivo
                        writer.newLine();  // Nova linha para o próximo contrato
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Metodo para editar solicitante no arquivo
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

            // Método para ler o valor do fundo solidário do arquivo
            public static double lerFundoSolidario() {
                verificarArquivoExistente(ARQUIVO_RELATORIO);  // Verifica se o arquivo existe
                double valor = 20000.0;  // Valor inicial do fundo solidário, caso o arquivo esteja vazio ou corrompido.

                try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_RELATORIO))) {
                    String linha = reader.readLine();  // Lê a primeira linha do arquivo
                    if (linha != null) {
                        String[] dados = linha.split(": ");  // Divide a linha onde ocorre a chave (ex.: "Fundo Solidário: 20000")
                        if (dados.length > 1) {
                            valor = Double.parseDouble(dados[1].trim());  // Converte o valor do fundo solidário para double
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();  // Mostra o erro no console caso não consiga ler o arquivo
                }
                return valor;  // Retorna o valor do fundo solidário
            }
            // Método para ler todos os empréstimos do arquivo
            public static List<Emprestimo> lerEmprestimos() {
                List<Emprestimo> emprestimos = new ArrayList<>();
                verificarArquivoExistente(ARQUIVO_EMPRESTIMOS);  // Verifica se o arquivo existe antes de tentar ler

                try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_EMPRESTIMOS))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        Emprestimo emprestimo = Emprestimo.fromString(linha);  // Converte a linha para um objeto Emprestimo
                        emprestimos.add(emprestimo);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return emprestimos;
            }


            public static boolean contratoJaExiste(int idEmprestimo, int numeroParcela) {
                List<Contrato> contratos = lerContratos();
                for (Contrato contrato : contratos) {
                    try {
                        int parcelaNumero = Integer.parseInt(contrato.getParcela().replaceAll("\\D+", ""));
                        if (contrato.getIdEmprestimo() == idEmprestimo && parcelaNumero == numeroParcela) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        }

