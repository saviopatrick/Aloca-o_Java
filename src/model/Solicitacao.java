package model;

import java.io.Serializable;
public class Solicitacao implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected String tipo;
    protected int ano;
    protected String semestre;
    protected String curso;
    protected int vagas;
    protected Horario horario;


    public Solicitacao(String tipo, int ano, String semestre, String curso, int vagas, Horario horario) {
        this.tipo = tipo;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.vagas = vagas;
        this.horario = horario;
    }



    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }


    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }



    @Override
    public String toString() {
        return "Solicitacao{" +
                "tipo='" + tipo + '\'' +
                ", ano=" + ano +
                ", semestre='" + semestre + '\'' +
                ", curso='" + curso + '\'' +
                ", vagas=" + vagas +
                ", horario='" + horario + '\'' +
                '}';
    }
}