package model;

public class SolicitacaoEventual extends Solicitacao {
    private static final long serialVersionUID = 1L;
    private String dataInicio;
    private String dataFim;
    private String finalidade;

    public SolicitacaoEventual(String tipo, int ano, String semestre, String curso, String finalidade ,int vagas, Horario horario, String dataInicio, String dataFim) {
        super(tipo, ano, semestre, curso, vagas, horario);
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.finalidade = finalidade;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "Solicitacao{" +
                "Tipo='" + this.tipo + '\'' +
                ", Ano=" + this.ano +
                ", Semestre='" + this.semestre + '\'' +
                ", Curso='" + this.curso + '\'' +
                ", Finalidade='" + finalidade + '\'' +
                ", Vagas=" + this.vagas +
                ", Horario='" + this.horario + '\'' +
                ", Data Inicio='" + dataInicio + '\'' +
                ", Data FIM='" + dataFim + '\''+
                '}';
    }
}