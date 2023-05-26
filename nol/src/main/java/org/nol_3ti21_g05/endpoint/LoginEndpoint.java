package org.nol_3ti21_g05.endpoint;

import org.nol_3ti21_g05.NOL;
import org.nol_3ti21_g05.data.CentroEducativo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@WebServlet(name = "LoginEndpoint", value = "/api/login")
public class LoginEndpoint extends HttpServlet {

    private static final String DATA_LOGIN_ENDPOINT = "/login";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String dni = req.getParameter("dni");
        String password = req.getParameter("password");

        if (dni == null || password == null || dni.isEmpty() || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("dni or password is missing");
            return;
        }

        if (!Pattern.matches("^[0-9]{8}[A-Z]$", dni)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("dni is not valid");
            return;
        }

        CentroEducativo centroEducativo = NOL.getCentroEducativo();
        CompletableFuture<String[]> future = centroEducativo.login(dni, password);

        try {
            String[] session = future.get();
            if (session == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.err.println("session is null");
                return;
            }
            req.getSession().setAttribute("ce-session", session[1]);
            resp.getWriter().write(session[0]);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println("error");
        }
    }
}
