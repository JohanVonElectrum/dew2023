package org.nol_3ti21_g05;

import org.nol_3ti21_g05.data.CentroEducativo;

import javax.servlet.http.HttpServlet;

public class NOL extends HttpServlet {

    private static CentroEducativo centroEducativo;
    private static String secret;

    @Override
    public void init() {
        System.out.println("NOL init");

        String dataBaseUrl = "http://" + getServletContext().getInitParameter("data-base-url")
                .replaceAll("^https?://", "")
                .replaceAll("/$", "");

        centroEducativo = new CentroEducativo(dataBaseUrl);
        secret = getServletContext().getInitParameter("secret");
    }

    public static CentroEducativo getCentroEducativo() {
        return centroEducativo;
    }

    public static String getSecret() {
        return secret;
    }

}
