package org.nol_3ti21_g05.endpoint;

import org.nol_3ti21_g05.NOL;
import org.nol_3ti21_g05.data.CentroEducativo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class LoginEndpoint extends HttpServlet {

    private static final String DATA_LOGIN_ENDPOINT = "/login";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        res.setCharacterEncoding("UTF-8");

        String dni = req.getParameter("dni");
        String password = req.getParameter("password");

        if (dni == null || password == null || dni.isEmpty() || password.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("dni or password is missing");
            return;
        }

        if (!Pattern.matches("^[0-9]{8}[A-Z]$", dni)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("dni is not valid");
            return;
        }

        CentroEducativo centroEducativo = NOL.getCentroEducativo();
        CompletableFuture<String[]> future = centroEducativo.login(dni, password);

        try {
            String[] session = future.get();
            if (session == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.err.println("session is null");
                return;
            }
            req.getSession().setAttribute("user-dni", dni);
            req.getSession().setAttribute("ce-token", session[0]);
            req.getSession().setAttribute("ce-session", session[1]);
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println("error");
        }
    }
}
