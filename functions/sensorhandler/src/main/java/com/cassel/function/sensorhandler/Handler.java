package com.cassel.function.sensorhandler;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import java.util.Optional;
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
        String payload = req.getBody();
        JsonObject json = JsonParser.parseString(payload)
                                    .getAsJsonObject();
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
