package com.openfaas.function.mail;

import com.google.gson.JsonObject;

/**
 * Class representing the data to be sent through
 * e-mail.
 */
public class MailData {

    private final String recipient;
    private final String subject;
    private final String text;

    private MailData(String recipient, String subject, String text) {
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
    }

    public static MailData of(String recipient, String subject, String text) {
        return new MailData(recipient, subject, text);
    }

    public String toJsonText() {
        var object = new JsonObject();
        object.addProperty("recipient", recipient);
        object.addProperty("subject", subject);
        object.addProperty("text", text);
        return object.toString();
    }

}
