package com.cassel.function.sensorhandler;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import java.util.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Handler implements IHandler {

    public IResponse Handle(IRequest req) {

        var moistureOpt = extractMoisture(req); 
        moistureOpt.ifPresent(moisture -> {
            var settings = loadSettings();
            new SensorHandler(settings).handleSensorData(moisture);
        });
        
        Response res = new Response();
        res.setBody("Hello, world!");
        return res;
    }

    Optional<Double> extractMoisture(IRequest req) {
        var payload = req.getBody();
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
            if (json.has("moisture")) {
                var value = json.get("moisture").getAsDouble();
                return Optional.of(value);
            }
            return Optional.empty();
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    private Settings loadSettings() {
        return Settings.of(5, 10, "guscassel@gmail.com");
    }

}
