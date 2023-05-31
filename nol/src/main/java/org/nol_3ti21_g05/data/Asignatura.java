package org.nol_3ti21_g05.data;

public class Asignatura {

    private final String nombre;
    private final String acronimo;
    private final int creditos;
    private final String cuatrimestre;
    private final int curso;
    private int nota;

    public Asignatura(String nombre, String acronimo, int creditos, String cuatrimestre, int curso) {
        this.nombre = nombre;
        this.acronimo = acronimo;
        this.creditos = creditos;
        this.cuatrimestre = cuatrimestre;
        this.curso = curso;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public int getCreditos() {
        return creditos;
    }

    public String getCuatrimestre() {
        return cuatrimestre;
    }

    public int getCurso() {
        return curso;
    }
}
