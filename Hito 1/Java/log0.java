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