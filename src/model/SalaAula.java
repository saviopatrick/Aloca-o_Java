package model;

public class SalaAula extends EspacoFisico implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public SalaAula(String nome, int capacidade, String localizacao) {
        super(nome, capacidade, localizacao);
    }
}