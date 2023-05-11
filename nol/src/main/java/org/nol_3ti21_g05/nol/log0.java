package org.nol_3ti21_g05.nol;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "log0", value = "/log0")
public class log0 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public log0() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        String usuario = request.getParameter("user");

        response.setContentType("text/html");
        printWriter.printf(
                "<!DOCTYPE html>\n<html>\n<head>\n" +
                        "<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />" +
                        "</head><body>%s %s %s %s %s %s %s</body></html>",
                LocalDateTime.now(),
                request.getQueryString(),
                usuario,
                request.getRemoteAddr(),
                getServletName(),
                request.getRequestURI(),
                request.getMethod()
        );
        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}