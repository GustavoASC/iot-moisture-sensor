package com.openfaas.function;

import java.util.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class to extract moisture information from a given
 * JSON payload, received from the HTTP request.
 * 
 * <p>
 * It works with {@link Optional} instances as the JSON
 * may come in invalid conditions e.g. missing information. 
 */
public class MoistureExtractor {

    /* Moisture field found from payload JSON */
    private final String MOISTURE_FIELD = "moisture";
 
    public Optional<Double> extractMoisture(String payload) {
        var element = JsonParser.parseString(payload);
        return extractMoisture(element);
    }

    private Optional<Double> extractMoisture(JsonElement element) {
        if (element.isJsonObject()) {
            var json = element.getAsJsonObject();
            return extractMoisture(json);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Double> extractMoisture(JsonObject json) {
        try {
            if (json.has(MOISTURE_FIELD)) {
                var value = json.get(MOISTURE_FIELD).getAsDouble();
                return Optional.of(value);
            }
            return Optional.empty();
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

}
