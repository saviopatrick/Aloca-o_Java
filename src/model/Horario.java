package model;

import java.io.Serializable;

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dias;
    private String turnos;
    private String horarios;

    public Horario(String dias, String turnos, String horarios) {
        this.dias = dias;
        this.turnos = turnos;
        this.horarios = horarios;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getTurnos() {
        return turnos;
    }

    public void setTurnos(String turnos) {
        this.turnos = turnos;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    @Override
    public String toString() {
        return dias + turnos+ horarios;
    }
}
