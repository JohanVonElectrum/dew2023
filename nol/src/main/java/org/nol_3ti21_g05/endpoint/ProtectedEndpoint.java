package org.nol_3ti21_g05.endpoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public abstract class ProtectedEndpoint extends HttpServlet {

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getPathInfo().split("/")[1];
        String token = req.getParameter("key");
        String ceSession = (String) req.getSession().getAttribute("ce-session");

        if (dni == null || token == null || dni.isEmpty() || token.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("dni or token is missing");
            return;
        }

        if (!Pattern.matches("^[0-9]{8}[A-Z]$", dni)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("dni is not valid");
            return;
        }

        if (ceSession == null || ceSession.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.err.println("ce-session is null");
            return;
        }

        this.doGetProtected(req, resp, dni, token, ceSession);
    }

    void doGetProtected(
            HttpServletRequest request,
            HttpServletResponse response,
            String dni,
            String token,
            String ceSession
    ) throws ServletException, IOException {
        super.doGet(request, response);
    }

}
