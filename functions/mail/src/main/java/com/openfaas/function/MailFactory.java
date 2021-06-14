package com.openfaas.function;

import java.util.Optional;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Factory to load Mail instances from JSON
 * payload.
 */
public class MailFactory {

    /* Field representing the email address to send the message */
    private static final String RECIPIENT = "recipient";
    /* Field representing the email subject */
    private static final String SUBJECT = "subject";
    /* Field representing the email text */
    private static final String MAIL_TEXT = "text";

    /**
     * Creates and returns an {@link Optional} instance regarding
     * the Mail information. When incomplete information is found,
     * an {@link Optional#empty()} is returned.
     * 
     * @param text the JSON text which will be parsed
     * @return the instance created
     */
    public Optional<Mail> createFromJsonText(String text) {
        return parseJson(text).map(this::convertJsonToMail);
    }

    private Optional<JsonObject> parseJson(String payload) {
        var element = JsonParser.parseString(payload);
        if (element.isJsonObject()) {
            var json = element.getAsJsonObject();
            return hasMandatoryData(json) ? Optional.of(json) : Optional.empty();
        } else {
            return Optional.empty();
        }
    }

    private Mail convertJsonToMail(JsonObject json) {
        return Mail.of("testemonitorplantas@gmail.com",
                       extract(json, RECIPIENT),
                       extract(json, SUBJECT),
                       extract(json, MAIL_TEXT));
    } 

    private boolean hasMandatoryData(JsonObject json) {
        return json.has(RECIPIENT) &&
               json.has(SUBJECT) &&
               json.has(MAIL_TEXT);
    }

    private String extract(JsonObject json, String field) {
        return json.get(field).getAsString();
    }


}
