package controller;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import  java.io.FileNotFoundException;
import java.util.Map;

public class Departamento implements Serializable {
    private Hashtable<String, ArrayList<Solicitacao> > solicitacoesEspaco;
    private Hashtable<String, ArrayList<Solicitacao> > solicitacoesCurso;
    private ArrayList<Solicitacao> solicitacoes;

    public Departamento() {
        solicitacoesEspaco = new Hashtable<>();
        solicitacoesCurso = new Hashtable<>();
        solicitacoes = new ArrayList<>();

    }
    public void salvarEspacos(EspacoFisico espacoFisicos){
        try{
            BufferedWriter escrever = new BufferedWriter(new FileWriter("EspacosFisicos.txt", true));
            escrever.write(espacoFisicos.getNome() + ";" + espacoFisicos.getLocalizacao() + ";"+ espacoFisicos.getCapacidade());
            escrever.newLine();


            escrever.close();

        } catch (Exception e){
            return;
        }
    }
    public ArrayList<EspacoFisico> pegandoEspacoArquivo(){
        EspacoFisico espacoFisico;
        ArrayList<EspacoFisico> espacos = new ArrayList<>();
        try{
            BufferedReader ler = new BufferedReader(new FileReader("EspacosFisicos.txt"));
            String linha;

            while((linha = ler.readLine()) != null){
                String[] dados = linha.split(";");
                String nome = dados[0];
                String localizacao = dados[1];
                int capacidade = Integer.parseInt(dados[2]);

                if(nome.contains("auditorio") || nome.contains("AUDITORIO")){
                    espacoFisico = new Auditorio(nome, capacidade, localizacao);
                } else {
                    espacoFisico = new SalaAula(nome, capacidade, localizacao);
                }

                espacos.add(espacoFisico);
            }
            return espacos;
        } catch(Exception e){
            return null;
        }
    }

    public Horario dividirHorario(String horario) {
        String[] horarios = horario.split(" ");

        for (String horarioAtual : horarios) {
            // Variáveis para armazenar dias, turno e horario
            StringBuilder dias = new StringBuilder();
            StringBuilder turnos = new StringBuilder();
            StringBuilder horarioRestante = new StringBuilder();

            // Percorrer os caracteres da string horarioAtual
            for (int i = 0; i < horarioAtual.length(); i++) {
                char caractere = horarioAtual.charAt(i);
                int count = 0;
                for(int j = 0; j < horarioAtual.length(); j++){
                    char car= horarioAtual.charAt(i);
                    if(car == 'T' || car == 'M' || car == 'N'){
                        break;
                    }
                    count++;
                }

                if (count > i+2) {
                    // Se é um dígito, adiciona ao dias
                    dias.append(caractere);
                } else if (caractere == 'T' || caractere == 'N' || caractere == 'M') {
                    // Encontrou o "T", "N" ou "M", agora adiciona ao turnos
                    turnos.append(caractere);
                } else {
                    // Qualquer outro caractere é adicionado ao horarioRestante
                    horarioRestante.append(caractere);
                }
            }

            Horario hor = new Horario(dias.toString(), turnos.toString(), horarioRestante.toString());
            return hor;
        }
        return null;
    }
    public void salvarSolicitacao (Solicitacao solicitacao){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("solicitacoes.txt", true));

            if (solicitacao instanceof SolicitacaoFixa) {
                SolicitacaoFixa solicitacaoFixa = (SolicitacaoFixa) solicitacao;
                writer.write(solicitacao.getTipo() + ";" + solicitacao.getAno() + ";" +
                        solicitacao.getSemestre() + ";" + solicitacao.getCurso() + ";" + solicitacaoFixa.getDisciplina() + ";" + solicitacao.getVagas() + ";" +
                        solicitacao.getHorario());
            } else if (solicitacao instanceof SolicitacaoEventual) {
                SolicitacaoEventual solicitacaoEventual = (SolicitacaoEventual) solicitacao;
                writer.write(solicitacao.getTipo() + ";" + solicitacao.getAno() + ";" +
                        solicitacao.getSemestre() + ";" + solicitacao.getCurso() + ";" + solicitacaoEventual.getFinalidade() + ";" + solicitacao.getVagas() + ";" +
                        solicitacao.getHorario() + ";" + solicitacaoEventual.getDataInicio() + ";" + solicitacaoEventual.getDataFim());
            }

            writer.newLine();
            writer.close();
        } catch (IOException e) {
            return;
        }
    }

    public ArrayList<Solicitacao> carregarSolicitacoes(String nomeArquivo) {
        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
            String linha;
            Solicitacao solicitacao = null;

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String tipo = dados[0];
                int ano = Integer.parseInt(dados[1]);
                String semestre = (dados[2]);
                String curso = dados[3];
                String escolha = dados[4];
                int vagas = Integer.parseInt(dados[5]);
                Horario horario = dividirHorario(dados[6]);

                if (tipo.equals("EVENTUAL")) {
                    String dataInicio = dados[7];
                    String dataFim = dados[8];
                    solicitacao = new SolicitacaoEventual(tipo, ano, semestre, curso, escolha, vagas, horario, dataInicio, dataFim);
                } else
                    solicitacao = new SolicitacaoFixa(tipo, ano, semestre, curso, escolha, vagas, horario);
                solicitacoes.add(solicitacao);
            }

            reader.close();
        } catch (Exception e){
            return null;
        }
        return solicitacoes;
    }

    public boolean alocarEspaco(Solicitacao solicitacao, String nomeSala, String nomeCurso){
        ArrayList<Solicitacao> alocadosEspaco = solicitacoesEspaco.get(nomeSala);
        ArrayList<Solicitacao> alocadosCurso = solicitacoesCurso.get(nomeCurso);

        if(alocadosEspaco == null){
            alocadosEspaco = new ArrayList<>();
            solicitacoesEspaco.put(nomeSala, alocadosEspaco);
        }

        if(alocadosCurso == null){

            alocadosCurso = new ArrayList<>();
            solicitacoesCurso.put(nomeCurso,alocadosCurso);
        }

        for(Solicitacao soli : alocadosEspaco) {
            if(solicitacao.getHorario().getDias().contains(soli.getHorario().getDias())){
                if(solicitacao.getHorario().getTurnos().contains(soli.getHorario().getTurnos())){
                    if(solicitacao.getHorario().getHorarios().contains(soli.getHorario().getHorarios())){
                        return false;
                    }
                }
            }
        }


        if(solicitacao.getTipo().equals("EVENTUAL")){

            if(nomeSala.contains("auditorio")||nomeSala.contains("auditório")){

                alocadosEspaco.add(solicitacao);
                alocadosCurso.add(solicitacao);
                solicitacoesEspaco.put(nomeSala, alocadosEspaco);
                solicitacoesCurso.put(nomeCurso, alocadosCurso);
                return true;
            } else{

                return false;
            }
        } else {
            alocadosEspaco.add(solicitacao);
            alocadosCurso.add(solicitacao);
            solicitacoesEspaco.put(nomeSala, alocadosEspaco);
            solicitacoesCurso.put(nomeCurso, alocadosCurso);
            return true;
        }


    }


    public void salvarNoArquivoEspacosAlocadosporCurso(Solicitacao solicitacao, String informacao) {
        Map<Solicitacao, String> espacosAlocadosporCurso = lerArquivoEspacosAlocados("espacosAlocadosporCurso.txt");
        if (espacosAlocadosporCurso == null) {
            espacosAlocadosporCurso = new HashMap<>();
        }
        espacosAlocadosporCurso.put(solicitacao, informacao);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("espacosAlocadosporCurso.txt"))) {
            outputStream.writeObject(espacosAlocadosporCurso);
        } catch (IOException e) {
            return;
        }
    }

    // Método para salvar no arquivo associando os espaços alocados por espaços físicos
    public void salvarNoArquivoEspacosAlocadosporEspacos(Solicitacao solicitacao, String informacao) {
        Map<Solicitacao, String> espacosAlocadosporEspacos = lerArquivoEspacosAlocados("espacosAlocadosporEspacos.txt");
        if (espacosAlocadosporEspacos == null) {
            espacosAlocadosporEspacos = new HashMap<>();
        }
        espacosAlocadosporEspacos.put(solicitacao, informacao);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("espacosAlocadosporEspacos.txt"))) {
            outputStream.writeObject(espacosAlocadosporEspacos);
        } catch (IOException e) {
            return;
        }
    }

    // Método para ler o arquivo de espaços alocados por curso e retornar o HashMap correspondente
    public Map<Solicitacao, String> lerArquivoEspacosAlocados(String nomeArquivo) {
        Map<Solicitacao, String> espacosAlocadosporCurso = new HashMap<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            espacosAlocadosporCurso = (Map<Solicitacao, String>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return espacosAlocadosporCurso;
    }

}