package com.openfaas.function.settings;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * Class to load settings from database, triggering an external function
 * which returns the information on JSON format.
 */
public class SettingsLoader {

    private static final String SETTINGS_URL = "http://192.168.0.111:8080/function/sensorsettings";

    public Optional<Settings> loadFromDatabase() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(new URI(SETTINGS_URL))
                                             .GET()
                                             .build();
            var result = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new SettingsFactory().createFromJson(result.body());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
