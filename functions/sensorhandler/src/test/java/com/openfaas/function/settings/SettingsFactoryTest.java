package com.openfaas.function.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class SettingsFactoryTest {

    @Test
    public void testCreateValidSettingsFromJson() {
        var json = "{ \"email\": \"mymail@gmail.com\", \"min_moisture\": 5 }";
        var result = new SettingsFactory().createFromJson(json).get();
        assertEquals("mymail@gmail.com", result.getEmail());
        assertEquals(5, result.getMinMoisture());
    }

    @Test
    public void testCreateInvalidMoistureFromJson() {
        var json = "{ \"email\": \"mymail@gmail.com\", \"min_moisture\": \"abc\" }";
        var result = new SettingsFactory().createFromJson(json);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateMissingMoistureFromJson() {
        var json = "{ \"email\": \"mymail@gmail.com\" }";
        var result = new SettingsFactory().createFromJson(json);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateEmptyFromJson() {
        var json = "{ }";
        var result = new SettingsFactory().createFromJson(json);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateMissingEmailFromJson() {
        var json = "{ \"min_moisture\": \"10\" }";
        var result = new SettingsFactory().createFromJson(json);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateMissingEmailAndInvalidMoistureFromJson() {
        var json = "{ \"min_moisture\": \"abc\" }";
        var result = new SettingsFactory().createFromJson(json);
        assertFalse(result.isPresent());
    }

}
