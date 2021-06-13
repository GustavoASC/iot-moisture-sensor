package com.openfaas.function;

import com.openfaas.model.IResponse;

import java.util.Optional;

import com.openfaas.function.settings.SettingsLoader;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

public class Handler extends com.openfaas.model.AbstractHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
        var moistureOpt = extractMoisture(req);
        if (moistureOpt.isPresent()) {
            var settingsOpt = new SettingsLoader().loadFromDatabase();
            if (settingsOpt.isPresent()) {
                var settings = settingsOpt.get();
                var moisture = moistureOpt.get();
                new SensorHandler(settings).handleSensorData(moisture);
                res.setBody("OK");
            } else {
                res.setBody("Could not load settings from database.");
            }
        } else {
            res.setBody("Could not extract moisture from: " + req.getBody());
        }
        return res;
    }

    Optional<Double> extractMoisture(IRequest req) {
        var payload = req.getBody();
        return new MoistureExtractor().extractMoisture(payload);
    }

}
