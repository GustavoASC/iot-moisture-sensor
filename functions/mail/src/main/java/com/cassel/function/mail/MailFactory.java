package com.cassel.function.mail;

import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MailFactory {

    private static final String RECIPIENT = "recipient";
    private static final String SUBJECT = "subject";
    private static final String MAIL_TEXT = "text";

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
