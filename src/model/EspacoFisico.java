package model;

import java.io.Serializable;

public class EspacoFisico implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private int capacidade;
    private String localizacao;

    public EspacoFisico(String nome, int capacidade, String localizacao) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}