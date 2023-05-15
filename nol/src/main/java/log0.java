import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


@WebServlet(name = "log0", value = "/log0")
public class log0 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public log0() {
        super();
    }

    @Override
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        String usuario = request.getParameter("email");

        response.setContentType("text/html");
        printWriter.printf(
                "<!DOCTYPE html>\n<html>\n<head>\n" +
                        "<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />" +
                        "</head><body>%s email=%s&password=%s %s %s %s %s</body></html>",
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