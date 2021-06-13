package com.openfaas.function;

import com.openfaas.function.mail.MailData;
import com.openfaas.function.mail.MailSender;
import com.openfaas.function.settings.Settings;

public class SensorHandler {

    private final Settings settings;

    public SensorHandler(Settings settings) {
        this.settings = settings;
    }

    public void handleSensorData(double moisture) {
        if (isOutOfRange(moisture)) {
            var payload = buildEmailData(moisture);
            new MailSender().send(payload);
        }
    }

    boolean isOutOfRange(double moisture) {
        return moisture < settings.getMinMoisture();
    }

    MailData buildEmailData(double moisture) {
        var mail = MailData.of(settings.getEmail(),
                               "Plant needs attention",
                               "Water the plant, please! The soil has only " + moisture + "% of the expected moisture level, whereas the minimum accepted level is " + settings.getMinMoisture() + "%.");
        return mail;
    }
    
}
