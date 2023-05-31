package org.nol_3ti21_g05.endpoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public abstract class ProtectedEndpoint extends HttpServlet {

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String dni = (String) req.getSession().getAttribute("user-dni");
        String token = (String) req.getSession().getAttribute("ce-token");
        String ceSession = (String) req.getSession().getAttribute("ce-session");

        if (dni == null || token == null || ceSession == null ||
                dni.isEmpty() || token.isEmpty() || ceSession.isEmpty() ||
                !Pattern.matches("^[0-9]{8}[A-Z]$", dni)
        ) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.err.println("user is not logged in");
            return;
        }

        this.doGetProtected(req, res, dni, token, ceSession);
    }

    void doGetProtected(
            HttpServletRequest req,
            HttpServletResponse res,
            String dni,
            String token,
            String ceSession
    ) throws ServletException, IOException {
        super.doGet(req, res);
    }

}
