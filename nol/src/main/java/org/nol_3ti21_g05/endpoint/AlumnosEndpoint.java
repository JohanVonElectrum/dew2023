package org.nol_3ti21_g05.endpoint;

import com.google.gson.Gson;
import org.nol_3ti21_g05.NOL;
import org.nol_3ti21_g05.data.Alumno;
import org.nol_3ti21_g05.data.CentroEducativo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AlumnosEndpoint extends ProtectedEndpoint {

    @Override
    protected void doGetProtected(
            HttpServletRequest req,
            HttpServletResponse res,
            String dni,
            String token,
            String ceSession
    ) {
        res.setCharacterEncoding("UTF-8");

        CentroEducativo centroEducativo = NOL.getCentroEducativo();

        try {
            Optional<Alumno> alumno = getAlumno(dni, token, ceSession, centroEducativo);

            if (alumno.isPresent()) {
                String[] path = req.getPathInfo().substring(1).split("/");
                if (path.length == 2 && path[1].equals("asignaturas")) {
                    new Gson().toJson(alumno.get().getAsignaturas().get(), res.getWriter());
                } else {
                    res.getWriter().write(alumno.get().toJson().toString());
                }

                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.err.println("alumno is null");
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    private Optional<Alumno> getAlumno(
            String dni,
            String token,
            String ceSession,
            CentroEducativo centroEducativo
    ) {
        CompletableFuture<Alumno> future = centroEducativo.getAlumno(dni, token, ceSession);

        try {
            return Optional.ofNullable(future.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
