package com.openfaas.function;

import org.junit.Test;
import static org.junit.Assert.*;

import com.openfaas.function.settings.Settings;

public class SensorHandlerTest {
    
    @Test
    public void testOutOfRangeDifferentRange() {
        var settings = Settings.of(5, "teste@gmail.com");
        var handler = new SensorHandler(settings);
        assertTrue(handler.isOutOfRange(-1));
        assertTrue(handler.isOutOfRange(-0));
        assertTrue(handler.isOutOfRange(0));
        assertTrue(handler.isOutOfRange(1));
        assertTrue(handler.isOutOfRange(4.99999999));
        assertFalse(handler.isOutOfRange(10.00000000001));
        assertFalse(handler.isOutOfRange(5));
        assertFalse(handler.isOutOfRange(5.0000001));
        assertFalse(handler.isOutOfRange(9.99999999999));
        assertFalse(handler.isOutOfRange(10));
    }
    
    @Test
    public void testOutOfRangeSameRange() {
        var settings = Settings.of(5, "teste@gmail.com");
        var handler = new SensorHandler(settings);
        assertTrue(handler.isOutOfRange(-1));
        assertTrue(handler.isOutOfRange(-0));
        assertTrue(handler.isOutOfRange(0));
        assertTrue(handler.isOutOfRange(1));
        assertTrue(handler.isOutOfRange(4.99999999));
        assertFalse(handler.isOutOfRange(10.00000000001));
        assertFalse(handler.isOutOfRange(5));
        assertFalse(handler.isOutOfRange(5.0000001));
        assertFalse(handler.isOutOfRange(9.99999999999));
        assertFalse(handler.isOutOfRange(10));
    }
    
    @Test
    public void testBuildEmailPayload() {
        var settings = Settings.of(50, "teste@gmail.com");
        var handler = new SensorHandler(settings);
        var expected = "{\"recipient\":\"teste@gmail.com\",\"subject\":\"Plant needs attention\",\"text\":\"Water the plant, please! The soil has only 10.0% of the expected moisture level, whereas the minimum accepted level is 50%.\"}".replaceAll("\n", "");
        assertEquals(expected, handler.buildEmailData(10.0).toJsonText().replaceAll("\n", ""));
    }
    
    @Test
    public void testBuildEmailZeroMoisturePayload() {
        var settings = Settings.of(50, "teste@gmail.com");
        var handler = new SensorHandler(settings);
        var expected = "{\"recipient\":\"teste@gmail.com\",\"subject\":\"Plant needs attention\",\"text\":\"Water the plant, please! The soil has only 0.0% of the expected moisture level, whereas the minimum accepted level is 50%.\"}".replaceAll("\n", "");
        assertEquals(expected, handler.buildEmailData(0.0).toJsonText().replaceAll("\n", ""));
    }
    
}
