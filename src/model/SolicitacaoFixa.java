package model;

public class SolicitacaoFixa extends Solicitacao{
    private static final long serialVersionUID = 1L;
    private String disciplina;


    public SolicitacaoFixa(String tipo, int ano, String semestre, String curso, String disciplina, int vagas, Horario horario ) {
        super(tipo, ano, semestre, curso, vagas, horario);
        this.disciplina = disciplina;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
    @Override
    public String toString() {
        return "Solicitacao{" +
                "Tipo='" + tipo + '\'' +
                ", Ano=" + ano +
                ", Semestre='" + semestre + '\'' +
                ", Curso='" + curso + '\'' +
                ", Disciplina='" + disciplina + '\'' +
                ", Vagas=" + vagas +
                ", Horario='" + horario + '\'' +
                '}';
    }
}