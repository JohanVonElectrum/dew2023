package org.nol_3ti21_g05.data;

import com.google.gson.JsonObject;
import org.nol_3ti21_g05.NOL;
import org.nol_3ti21_g05.security.SignedData;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public abstract class User {

    private final String dni;
    private final String nombre;
    private final String apellidos;
    private String img;
    private String token;
    private String ceSession;

    protected User(String dni, String nombre, String apellidos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCeSession(String ceSession) {
        this.ceSession = ceSession;
    }

    public String getToken() {
        return token;
    }

    public String getCeSession() {
        return ceSession;
    }

    public String getDNI() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String encoded() throws NoSuchAlgorithmException {
        JsonObject json = new JsonObject();
        json.add("user", toJson());
        json.addProperty("token", token);
        String data = Base64.getEncoder().encodeToString(json.toString().getBytes(StandardCharsets.UTF_8));
        SignedData signedData = new SignedData(data, NOL.getSecret());
        return signedData.toString();
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("dni", dni);
        json.addProperty("nombre", nombre);
        json.addProperty("apellidos", apellidos);
        if (img != null) {
            json.addProperty("img", img);
        }
        return json;
    }
}
