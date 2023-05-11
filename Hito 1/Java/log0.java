import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class log0 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public log0() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String usuario = request.getParameter("user");

		String preTituloHTML5 = "<!DOCTYPE html>\n<html>\n<head>\n<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />";
		response.setContentType("text/html");
		pw.println(preTituloHTML5);
		pw.println(LocalDateTime.now().toString() + " " + request.getQueryString() + " " + usuario + " " + request.getRemoteAddr() + " " + getServletName() + " " + request.getRequestURI() + " " + request.getMethod());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}