package org.nol_3ti21_g05.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CentroEducativo {

    private final String dataBaseUrl;
    private final ExecutorService executorService;
    private final OkHttpClient client;

    public CentroEducativo(String dataBaseUrl) {
        this.dataBaseUrl = dataBaseUrl;
        this.executorService = Executors.newFixedThreadPool(4);
        this.client = new OkHttpClient();
    }

    public CompletableFuture<String[]> login(String dni, String password) {
        JsonObject json = new JsonObject();
        json.addProperty("dni", dni);
        json.addProperty("password", password);
        Request request = new Request.Builder()
                .url(dataBaseUrl + "/login")
                .post(RequestBody.create(json.toString(), MediaType.parse("application/json")))
                .build();
        return wrap(request).thenApply(response -> {
            if (response.code() == 200) {
                try {
                    return new String[] {
                            response.body().string(),
                            response.headers("Set-Cookie").stream()
                                    .filter(s -> s.startsWith("JSESSIONID="))
                                    .map(s -> s.substring("JSESSIONID=".length()))
                                    .map(s -> s.substring(0, s.indexOf(';')))
                                    .findAny().get()
                    };
                } catch (IOException | NoSuchElementException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    public CompletableFuture<Alumno> getAlumno(String dni, String token, String ceSession) {
        Request request = new Request.Builder()
                .url(dataBaseUrl + "/alumnos/" + dni + "?key=" + token)
                .header("Cookie", "JSESSIONID=" + ceSession)
                .build();
        return wrap(request).thenApply(response -> {
            if (response.code() == 200) {
                try {
                    JsonObject json = new Gson().fromJson(response.body().string(), JsonObject.class);
                    Alumno alumno = new Alumno(
                            json.get("dni").getAsString(),
                            json.get("nombre").getAsString(),
                            json.get("apellidos").getAsString()
                    );
                    alumno.setToken(token);
                    alumno.setCeSession(ceSession);
                    return alumno;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    public CompletableFuture<Profesor> getProfesor(String dni, String token, String ceSession) {
        Request request = new Request.Builder()
                .url(dataBaseUrl + "/profesores/" + dni + "?key=" + token)
                .header("Cookie", "JSESSIONID=" + ceSession)
                .build();
        return wrap(request).thenApply(response -> {
            if (response.code() == 200) {
                try {
                    JsonObject json = new Gson().fromJson(response.body().string(), JsonObject.class);
                    Profesor profesor = new Profesor(
                            json.get("dni").getAsString(),
                            json.get("nombre").getAsString(),
                            json.get("apellidos").getAsString()
                    );
                    profesor.setToken(token);
                    profesor.setCeSession(ceSession);
                    return profesor;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    public CompletableFuture<Collection<Asignatura>> getAsignaturasAlumno(String dni, String token, String ceSession) {
        Request request = new Request.Builder()
                .url(dataBaseUrl + "/alumnos/" + dni + "/asignaturas?key=" + token)
                .header("Cookie", "JSESSIONID=" + ceSession)
                .build();
        return wrap(request).thenApply(response -> {
            if (response.code() == 200) {
                try {
                    JsonArray array = new Gson().fromJson(response.body().string(), JsonArray.class);
                    return StreamSupport.stream(array.spliterator(), false)
                            .map(JsonElement::getAsJsonObject)
                            .map(json -> getAsignatura(json.get("asignatura").getAsString(), token, ceSession).thenApply(asignatura -> {
                                asignatura.setNota(json.get("nota").getAsString().isEmpty() ?
                                        -1 :
                                        json.get("nota").getAsInt()
                                );
                                return asignatura;
                            }))
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    public CompletableFuture<Asignatura> getAsignatura(String codigo, String token, String ceSession) {
        Request request = new Request.Builder()
                .url(dataBaseUrl + "/asignaturas/" + codigo + "?key=" + token)
                .header("Cookie", "JSESSIONID=" + ceSession)
                .build();
        return wrap(request).thenApply(response -> {
            if (response.code() == 200) {
                try {
                    JsonObject json = new Gson().fromJson(response.body().string(), JsonObject.class);
                    return new Asignatura(
                            json.get("nombre").getAsString(),
                            json.get("acronimo").getAsString(),
                            json.get("creditos").getAsInt(),
                            json.get("cuatrimestre").getAsString(),
                            json.get("curso").getAsInt()
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    private CompletableFuture<Response> wrap(Request request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    private URL buildUrl(String endpoint) throws MalformedURLException {
        return new URL(dataBaseUrl + endpoint);
    }
}
