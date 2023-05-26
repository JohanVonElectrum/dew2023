package org.nol_3ti21_g05.data;

import org.nol_3ti21_g05.NOL;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class Alumno extends User {
    protected Alumno(String dni, String nombre, String apellidos) {
        super(dni, nombre, apellidos);
    }

    public CompletableFuture<Collection<Asignatura>> getAsignaturas() {
        CentroEducativo centroEducativo = NOL.getCentroEducativo();

        return centroEducativo.getAsignaturasAlumno(this.getDNI(), this.getToken(), this.getCeSession());
    }
}
