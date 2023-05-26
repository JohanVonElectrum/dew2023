package org.nol_3ti21_g05.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class SignedData {

    private final String data;
    private final String signature;
    private final byte[] key;

    public SignedData(String data, String signature, byte[] key) {
        this.data = data;
        this.signature = signature;
        this.key = key;

        validate();
    }

    public SignedData(String data, String passphrase) throws NoSuchAlgorithmException {
        this.data = data;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        this.key = digest.digest(passphrase.getBytes(StandardCharsets.UTF_8));
        digest.reset();
        digest.update(key);
        this.signature = Base64.getEncoder().encodeToString(digest.digest(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static SignedData fromString(String data, String passphrase) throws NoSuchAlgorithmException {
        String[] parts = data.split("\\.");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid data");
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return new SignedData(parts[0], parts[1], digest.digest(passphrase.getBytes(StandardCharsets.UTF_8)));
    }

    public String get() {
        return data;
    }

    public void validate() {
        if (data == null || signature == null) throw new IllegalArgumentException("data and signature must not be null");

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(key);
            String newSignature = Base64.getEncoder().encodeToString(digest.digest(data.getBytes(StandardCharsets.UTF_8)));
            if (!newSignature.equals(signature)) throw new IllegalArgumentException("Invalid signature");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("%s.%s", data, signature);
    }
}
