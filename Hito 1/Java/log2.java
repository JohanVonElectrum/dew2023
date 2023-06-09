import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


@WebServlet(name = "log2", value = "/log2")
public class log2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public log2() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getServletContext().getInitParameter("log-path");
        File file1 = new File(path);

        PrintWriter printWriter = new PrintWriter(
                new FileOutputStream(path, true)
        );
        String usuario = request.getParameter("user");

        try {
            file1.createNewFile();
        }catch(Exception e) {
            System.out.println("No se pudo crear el fichero");
        }

        printWriter.printf(
                "%s %s %s %s %s %s %s%n",
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
        String path = getServletContext().getInitParameter("log-path");
        File file1 = new File(path);

        PrintWriter printWriter = new PrintWriter(
                new FileOutputStream(path, true)
        );
        String usuario = request.getParameter("email");

        try {
            file1.createNewFile();
        }catch(Exception e) {
            System.out.println("No se pudo crear el fichero");
        }

        printWriter.printf(
                "%s email=%s&password=%s %s %s %s %s%n",
                LocalDateTime.now(),
                request.getParameter("email"),
                request.getParameter("password"),
                usuario,
                request.getRemoteAddr(),
                getServletName(),
                request.getRequestURI(),
                request.getMethod()
        );
        printWriter.close();
    }
}