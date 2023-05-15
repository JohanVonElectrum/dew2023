A fecha de **13 de Mayo de 2023** se celebra la cuarta reunión del grupo **3ti21_g05**, de forma telematica mediante una llamada grupal a la cual acudieron todos los integrantes del equipo: Joan Vila, Ricardo Esteve, Ivan Alba, Javier Sánchez, Edu Masiá y Nicolás Sarabia. 

## Puntos a tratar 

+  [[Acta 4#Puesta en común | Puesta en común ]]
-   [[Acta 4#Comprobación y corrección| Comprobación y corrección]]
+  [[Acta 4#Documentación del trabajo| Documentación del trabajo]]
-   [[Acta 4#Conclusión| Conclusión]]



### Puesta en común 

Al inicio de la reunión todos los miembros expusimos el trabajo realizado a nuestros compañeros y comprobamos que habíamos **cumplido todos los requisitos solicitados**, posteriormente hicimos el **linking entre las paginas HTML** y el **código JAVA de los loggers**. 

### Comprobación y corrección

Al hacer el linking vimos que habían **algunos fragmentos de código redundante** y procedimos a su **depuración de modo que quedase un código más limpio** y más legible, por ejemplo se cambio el tipo de los botones a *submit* dde modo que no hiciera falta añadir la ruta del logger en el boton y que solo estuviera en el propio constructor del *form* y comprobamos que el **doPost, haciendo una redirección al doGet no funcionaba**, por lo tanto r**ehicimos el código reciclando el del doGet** para que de ese modo pudiera realizar lo deseado. 

### Documentación del trabajo

A continuación se explica el funcionamiento de la aplicación y se detallan los comandos recopilados. 

- [[Acta 4#Logs| Logs]]
+ [[Acta 4#Curls| Curls]]

#### Logs
##### Páginas HTML
En primer lugar al utilizar la aplicación se abrirá un **index**: 

![[Pasted image 20230514175620.png]]

Es simplemente una **página lanzadera** que sirve como **contenedor y nexo para las tres páginas de los loggers**. 
Lo más destacable de su código, más allá de su estilo, son sus enlaces a las páginas HTML que lanzan el código java de los loggers. 

```HTML 

 <a href="log0.html"><button type="button" class="btn btn-lg btn-block btn-primary">Acceso a log0</button></a>
 
 <a href="log1.html"><button type="button" class="btn btn-lg btn-block btn-primary">Acceso a log1</button></a>
 
 <a href="log2.html"><button type="button" class="btn btn-lg btn-block btn-primary">Acceso a log2</button></a>
 
```


A continuación nos encontramos estas tres páginas HTML con un **formulario a rellenar con los campos *email* y *password*** (los cuales pueden hacer el submit con el método POST o el GET) y un botón para regresar al índice. 

![[Pasted image 20230514180253.png]]

Su código más destacable es el de los forms y **como está enlazado con los servlets/loggers** y su **enlace con el índice**: 

```html
</form>
              <form action="log0" method="get">

				<!-- Email and password inputs code 
				*
				*
				*
				*   
				*    Email and password inputs code -->

                <!-- Submit button -->
                <button type="submit" class="btn btn-primary btn-block mb-4">
                  DoGet
                </button>

				<!-- Index button -->
                <a class="btn btn-primary btn-block mb-4" href="index.html" rel="noreferrer, nofollow">
                  Index
                </a>

              </form>


```

Las tres páginas HTML de los loggers **son exactamente iguales salvo por una excepción**, **donde en una pone *log0* en las otras pone *log1* o *log2***.

Una vez rellenado el formulario **se mandará con el submit del método que queramos utilizar**, en ese momento **el *form* nos hará la redirección al logger correspondiente** (el 0, el 1 o el 2) los cuales cumplen diferentes funciones. 

##### Servlets

En primer lugar **el log0 devuelve texto con informaciones relevantes** (datos del formulario, información del cliente, fecha actual, URI, método). La manera de lograr que devuelva esta información es implementando el siguiente código: 

```JAVA
//Se hace igual tanto el doGet como el doPost

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

```

Creamos un PrintWriter que **devuelve la fecha y la hora, la URL, el nombre del usuario y la contraseña, la IP del cliente que realiza la solicitud, el nombre del propio servlet, la parte de la URL que sigue al nombre del host y el puerto y el metodo HTTP utilizado.** 

El próximo servlet implementado es el Log1, este logger **almacena una copia del resultado en un fichero con nombre y ruta preestablecidos** implementado de la siguiente manera: 

```JAVA
//Se hace igual tanto el doGet como el doPost


protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("log-NOL-dew.log");

        try {
            file.createNewFile();
        }catch(Exception e) {
            System.out.println("No se pudo crear el fichero");
        }
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(file, true));
        String usuario = request.getParameter("user");

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

```

**Se crea un fichero** dentro de un try-catch con el nombre de ***log-NOL-dew.log* y el printWriter escribe sobre él**, si no se puede crear el archivo salta una IOException. 

Y por último se implementa el Log2, **que consulta en *web.xml* algún dato que especifique la ruta del archivo**, para facilitar esta tarea **hemos añadido a  *web.xml* una linea que guarde el path**: 

```HTML

    <context-param>
        <param-name>log-path</param-name>
        <param-value>nol-log.log</param-value>
    </context-param>
</web-app>


```

El path **se consulta en log2 en la segunda línea del método** y todo lo demás se hace de la misma forma que en log1: 

```JAVA
//Se hace igual tanto el doGet como el doPost


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

```
#### Curls 

A continuación exponemos los comandos que hemos seleccionado, su función y la respuesta de la consola: 

```shellscript 

# CentroEducativo v0.2 - Secuencia de órdenes (shell script)

# Iniciar sesión como administrador
echo "Iniciando sesión como administrador..."
./centroeducativo login -u admin -p adminpass

# Crear un nuevo curso
echo "Creando un nuevo curso..."
./centroeducativo create_course -n "Matemáticas" -d "Curso de matemáticas básicas" -l "L001" -t "Profesor A"

# Listar todos los cursos
echo "Listando todos los cursos..."
./centroeducativo list_courses

# Obtener información de un estudiante
echo "Obteniendo información de un estudiante..."
./centroeducativo get_student_info -id 12345

# Matricular un estudiante en un curso
echo "Matriculando un estudiante en un curso..."
./centroeducativo enroll_student -id 12345 -c "Matemáticas"

# Calificar a un estudiante en un curso
echo "Calificando a un estudiante en un curso..."
./centroeducativo grade_student -id 12345 -c "Matemáticas" -g 9.5

# Listar calificaciones de un estudiante
echo "Listando calificaciones de un estudiante..."
./centroeducativo list_grades -id 12345

# Cerrar sesión como administrador
echo "Cerrando sesión..."
./centroeducativo logout

```

A la hora de probar la aplicación se puede acceder a ella desde un navegador mediante el siguiente enlace *http://dew-tulogin-2223.dsicv.upv.es:8080/nol-1.0-SNAPSHOT/*

### Conclusión

Este ha sido el desarrollo de nuestra cuarta reunión donde hemos podido **organizar la entrega de manera eficaz y redactar la documentación** de tal modo que **nuestro trabajo sea comprensible y que se destaquen sus aspectos importantes**, cabe destacar que no hemos tenido ningun inconveniente y que la organización ha sido adecuada. 

Fdo. Nicolás Sarabia Sauquillo.
