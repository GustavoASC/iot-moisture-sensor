package com.openfaas.function;

import com.google.gson.JsonObject;

public class SensorHandler {

    private final Settings settings;

    public SensorHandler(Settings settings) {
        this.settings = settings;
    }

    public void handleSensorData(double moisture) {
        if (isOutOfRange(moisture)) {
            var payload = buildPayload(moisture);
            new MailSender().accept(payload);
        }
    }

    boolean isOutOfRange(double moisture) {
        return moisture < settings.getLower() ||
               moisture > settings.getHigher();
    }

    String buildPayload(double moisture) {
        var object = new JsonObject();
        object.addProperty("recipient", settings.getRecipient());
        object.addProperty("subject", "Plant needs attention");
        object.addProperty("text", "The plant needs attention");
        return object.toString();
    }
    
}
