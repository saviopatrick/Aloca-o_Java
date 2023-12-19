package view;

import model.*;
import controller.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Departamento departamento = new Departamento();

        ArrayList<Solicitacao> solicitacoes = departamento.carregarSolicitacoes("solicitacoes.txt");
        ArrayList<EspacoFisico> espacos = departamento.pegandoEspacoArquivo();


        if (espacos == null) {
            espacos = new ArrayList<>();
        }
        if (solicitacoes == null) {
            solicitacoes = new ArrayList<>();
        }

        while (true) {

            System.out.println("\t1 - Adicionar Espacos" +
                    "\n\t2 - Adicionar Solicitações" +
                    "\n\t3 - Alocar Espaco" +
                    "\n\t4 - Ler relatorio" +
                    "\n\t5 - Sair");

            int opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digime o nome do espaço: ");
                    String nomeEspaco = entrada.nextLine();
                    System.out.println("Digite a localizacao do espaço: ");
                    String localizacaoEspaco = entrada.nextLine();
                    System.out.println("digite a capacidade do espaço: ");
                    int capacidadeEspaco = entrada.nextInt();
                    entrada.nextLine();
                    EspacoFisico espacofisico = new EspacoFisico(nomeEspaco, capacidadeEspaco, localizacaoEspaco);
                    departamento.salvarEspacos(espacofisico);
                    espacos.add(espacofisico);
                    break;

                case 2:
                    Solicitacao s;
                    System.out.println("Fale se é fixa ou eventual");
                    String tipo = entrada.nextLine().toUpperCase();
                    System.out.println("Fale o ano");
                    int ano = entrada.nextInt();
                    entrada.nextLine();
                    System.out.println("Fale o semestre");
                    String semestre = entrada.nextLine();
                    System.out.println("Fale o curso");
                    String curso = entrada.nextLine();
                    System.out.println("Fale a quantidade de vagas");
                    int vagas = entrada.nextInt();
                    entrada.nextLine();
                    System.out.println("Fale os dias do horario");
                    String dias = entrada.nextLine();
                    System.out.println("Fale o turno do horario");
                    String turno = entrada.nextLine();
                    System.out.println("Fale os horarios do horario");
                    String horarios = entrada.nextLine();

                    Horario h = new Horario(dias, turno, horarios);

                    if(tipo.equals("EVENTUAL")){
                        System.out.println("Fale a finalidade");
                        String finalidade = entrada.nextLine();
                        System.out.println("Fale a data inicio");
                        String dataInicio = entrada.nextLine();
                        System.out.println("Fale a data do fim");
                        String dataFim = entrada.nextLine();

                        s = new SolicitacaoEventual(tipo, ano, semestre, curso, finalidade, vagas,h, dataInicio, dataFim);
                    } else {
                        System.out.println("Fale a Disciplina");
                        String disciplina = entrada.nextLine();
                        s = new SolicitacaoFixa(tipo, ano, semestre, curso, disciplina, vagas, h);
                    }
                    departamento.salvarSolicitacao(s);
                    solicitacoes.add(s);
                    break;

                case 3:
                    System.out.println("Lista de Solicitações Disponíveis para Alocar:");
                    for (int i = 0; i < solicitacoes.size(); i++) {
                        System.out.println(i + " - " + solicitacoes.get(i));
                    }

                    System.out.println("Digite o número da solicitação que deseja alocar: ");
                    int numeroSolicitacao = entrada.nextInt();
                    entrada.nextLine();

                    if (numeroSolicitacao >= 0 && numeroSolicitacao < solicitacoes.size()) {
                        Solicitacao solicitacaoEscolhida = solicitacoes.get(numeroSolicitacao);

                        if (solicitacaoEscolhida instanceof SolicitacaoEventual) {
                            System.out.println("Espaços Disponíveis para Alocação:");
                            for (EspacoFisico espaco : espacos) {
                                if (espaco.getNome().toLowerCase().contains("auditorio") || espaco.getNome().toLowerCase().contains("auditório")) {
                                    System.out.println(espacos.indexOf(espaco) + " - " + espaco.getNome());
                                }
                            }
                        } else if (solicitacaoEscolhida instanceof SolicitacaoFixa) {

                            System.out.println("Espaços Disponíveis para Alocação:");
                            for (int i = 0; i < espacos.size(); i++) {
                                System.out.println(i + " - " + espacos.get(i).getNome());
                            }
                        }

                        System.out.println("Digite o número do espaço que deseja alocar: ");
                        int numeroEspaco = entrada.nextInt();
                        entrada.nextLine();

                        if (numeroEspaco >= 0 && numeroEspaco < espacos.size()) {
                            EspacoFisico espacoEscolhido = espacos.get(numeroEspaco);
                            String nomeSala = espacoEscolhido.getNome();
                            String nomeCurso= solicitacaoEscolhida.getCurso();

                            boolean alocacaoBemSucedida = departamento.alocarEspaco(solicitacaoEscolhida, nomeSala,nomeCurso);

                            if (alocacaoBemSucedida) {
                                System.out.println("Alocação bem-sucedida!");
                                solicitacoes.remove(numeroSolicitacao);

                                departamento.salvarNoArquivoEspacosAlocadosporCurso(solicitacaoEscolhida,nomeCurso);
                                departamento.salvarNoArquivoEspacosAlocadosporEspacos(solicitacaoEscolhida,nomeSala);


                            } else {
                                System.out.println("Conflito de horário ou condições não atendidas.");
                            }
                        } else {
                            System.out.println("Número de espaço inválido.");
                        }
                    } else {
                        System.out.println("Número de solicitação inválido.");
                    }
                    break;

                case 4:
                    System.out.println("O relatório vai ser: (1 - Por Curso | 2 - Por Espaço Fisico)");
                    int escolharelatorio = entrada.nextInt();
                    entrada.nextLine();

                    if (escolharelatorio == 1) {
                        Map<Solicitacao, String> espacosCurso = departamento.lerArquivoEspacosAlocados("espacosAlocadosporCurso.txt");
                        String cursoAtual = null;

                        for (Map.Entry<Solicitacao, String> entry : espacosCurso.entrySet()) {
                            String cursodasolicitacao = entry.getValue();
                            if (!cursodasolicitacao.equals(cursoAtual)) {
                                cursoAtual = cursodasolicitacao;
                                System.out.println("- Curso: " + cursodasolicitacao);
                            }
                            System.out.println("  Solicitação: " + entry.getKey());
                        }
                    } else if (escolharelatorio == 2) {
                        Map<Solicitacao, String> espacosEspacos = departamento.lerArquivoEspacosAlocados("espacosAlocadosporEspacos.txt");
                        String espacoAtual = null;

                        for (Map.Entry<Solicitacao, String> entry : espacosEspacos.entrySet()) {
                            String espacodasolicitacao = entry.getValue();
                            if (!espacodasolicitacao.equals(espacoAtual)) {
                                espacoAtual = espacodasolicitacao;
                                System.out.println("- Espaço Físico: " + espacodasolicitacao);
                            }
                            System.out.println("  Solicitação: " + entry.getKey());
                        }
                    } else {
                        System.out.println("Escolha Inválida");
                    }

                    break;
                case 5:
                    System.exit(0);





                }

            }
        }
    }

