package com.openfaas.function.settings;

import java.util.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class to create instances of {@link Settings} given a JSON
 * payload on String format.
 */
public class SettingsFactory {

    private static final String MIN_MOISTURE_FIELD = "min_moisture";
    private static final String EMAIL_FIELD = "email";

    public Optional<Settings> createFromJson(String json) {
        var element = JsonParser.parseString(json);
        return createFromJson(element);
    }

    private Optional<Settings> createFromJson(JsonElement element) {
        if (element.isJsonObject()) {
            var json = element.getAsJsonObject();
            return createFromJson(json);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Settings> createFromJson(JsonObject json) {
        try {
            if (json.has(MIN_MOISTURE_FIELD) && json.has(EMAIL_FIELD)) {
                var minMoisture = json.get(MIN_MOISTURE_FIELD).getAsInt();
                var email = json.get(EMAIL_FIELD).getAsString();
                var settings = Settings.of(minMoisture, email);
                return Optional.of(settings);
            } else {
                return Optional.empty();
            }
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

}
