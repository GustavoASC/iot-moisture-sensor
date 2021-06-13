package com.openfaas.function.mail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

/**
 * Class to send e-mail through external function.
 * 
 * <p>
 * This class basically invokes a different function on OpenFaaS
 * which is responsible for sending e-mail, given the JSON payload
 * sent on HTTP body.
 */
public class MailSender {

    private static final String MAIL_URL = "http://192.168.0.111:8080/function/mail";

    public void send(MailData data) {
        try {
            String payload = data.toJsonText();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(new URI(MAIL_URL))
                                             .POST(BodyPublishers.ofString(payload))
                                             .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
