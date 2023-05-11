package org.nol_3ti21_g05.nol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "log1", value = "/log1")
public class log1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public log1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file1 = new File("/home/user/Escritorio/log-NOL-dew.log");

		try {
			file1.createNewFile();
		}catch(Exception e) {
			System.out.println("No se pudo crear el fichero");
		}
		PrintWriter pw2 = new PrintWriter(new FileOutputStream(new File("/home/user/Escritorio/log-NOL-dew.log"), true));
		String usuario = request.getParameter("user");
		
		pw2.println(LocalDateTime.now().toString() + " " + request.getQueryString() + " " + usuario + " "  + request.getRemoteAddr() + " " + getServletName() + " " + request.getRequestURI() + " " + request.getMethod());
		pw2.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}