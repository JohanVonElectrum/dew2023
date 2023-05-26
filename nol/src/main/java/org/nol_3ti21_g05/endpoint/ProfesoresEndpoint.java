package org.nol_3ti21_g05.endpoint;

import org.nol_3ti21_g05.NOL;
import org.nol_3ti21_g05.data.CentroEducativo;
import org.nol_3ti21_g05.data.Profesor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

@WebServlet(name = "ProfesoresEndpoint", value = "/api/profesores/*")
public class ProfesoresEndpoint extends ProtectedEndpoint {

    @Override
    protected void doGetProtected(
            HttpServletRequest req,
            HttpServletResponse resp,
            String dni,
            String token,
            String ceSession
    ) {
        CentroEducativo centroEducativo = NOL.getCentroEducativo();
        CompletableFuture<Profesor> future = centroEducativo.getProfesor(dni, token, ceSession);

        try {
            Profesor profesor = future.get();
            if (profesor == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.err.println("profesor is null");
                return;
            }
            resp.getWriter().write(profesor.toJson().toString());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println("error");
        }
    }
}
