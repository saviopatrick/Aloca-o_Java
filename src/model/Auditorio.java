package model;
public class Auditorio extends EspacoFisico implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public Auditorio(String nome, int capacidade, String localizacao) {
        super(nome, capacidade, localizacao);
    }
}