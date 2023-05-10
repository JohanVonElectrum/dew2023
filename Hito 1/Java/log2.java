

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class log2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public log2() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File file1 = new File(getServletContext().getInitParameter("logPath"));
		
		PrintWriter pw2 = new PrintWriter(new FileOutputStream(new File(getServletContext().getInitParameter("logPath")),true));
		String usuario = request.getParameter("user");

		try {
			file1.createNewFile();
		}catch(Exception e) {
			System.out.println("No se pudo crear el fichero");
		}
	    
		pw2.println(LocalDateTime.now().toString() + " " + request.getQueryString() + " " + usuario + " "  + request.getRemoteAddr() + " " + getServletName() + " " + request.getRequestURI() + " " + request.getMethod());
		pw2.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}