package com.openfaas.function;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.function.Consumer;

public class MailSender implements Consumer<String> {

    private static final String MAIL_URL = "http://192.168.0.111:8080/function/mail";

    @Override
    public void accept(String payload) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(new URI(MAIL_URL))
                                             .POST(BodyPublishers.ofString(payload))
                                             .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var body = response.body();
            var status = response.statusCode();
            var content = response.toString();

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
