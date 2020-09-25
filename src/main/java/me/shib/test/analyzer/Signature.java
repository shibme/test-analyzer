package me.shib.test.analyzer;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Signature {

    private static final transient String signatureURL = "https://shibme.github.io/test-analyzer/signatures.json";
    private static final transient Gson gson = new Gson();

    private String name;
    private String regex;

    private static String readFromURL() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(signatureURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line).append("\n");
        }
        rd.close();
        return result.toString();
    }

    static List<Signature> getSignatures() throws IOException {
        LiveSignatures liveSignatures = gson.fromJson(readFromURL(), LiveSignatures.class);
        return liveSignatures.signatures;
    }

    public String getName() {
        return name;
    }

    public String getRegex() {
        return regex;
    }

    private static class LiveSignatures {
        private List<Signature> signatures;
    }

}
