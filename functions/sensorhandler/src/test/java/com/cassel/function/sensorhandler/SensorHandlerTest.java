package com.cassel.function.sensorhandler;

import org.junit.Test;
import static org.junit.Assert.*;

public class SensorHandlerTest {
    
    @Test
    public void testOutOfRangeDifferentRange() {
        var settings = Settings.of(5, 10, "teste@gmail.com");
        var handler = new SensorHandler(settings);
        assertTrue(handler.isOutOfRange(-1));
        assertTrue(handler.isOutOfRange(-0));
        assertTrue(handler.isOutOfRange(0));
        assertTrue(handler.isOutOfRange(1));
        assertTrue(handler.isOutOfRange(4.99999999));
        assertTrue(handler.isOutOfRange(10.00000000001));
        assertFalse(handler.isOutOfRange(5));
        assertFalse(handler.isOutOfRange(5.0000001));
        assertFalse(handler.isOutOfRange(9.99999999999));
        assertFalse(handler.isOutOfRange(10));
    }
    
    @Test
    public void testOutOfRangeSameRange() {
        var settings = Settings.of(5, 5, "teste@gmail.com");
        var handler = new SensorHandler(settings);
        assertTrue(handler.isOutOfRange(-1));
        assertTrue(handler.isOutOfRange(-0));
        assertTrue(handler.isOutOfRange(0));
        assertTrue(handler.isOutOfRange(1));
        assertTrue(handler.isOutOfRange(4.99999999));
        assertTrue(handler.isOutOfRange(10.00000000001));
        assertFalse(handler.isOutOfRange(5));
        assertTrue(handler.isOutOfRange(5.0000001));
        assertTrue(handler.isOutOfRange(9.99999999999));
        assertTrue(handler.isOutOfRange(10));
    }
    
    @Test
    public void testBuildPayload() {
        var settings = Settings.of(5, 5, "teste@gmail.com");
        var handler = new SensorHandler(settings);
        var expected = "{\"recipient\":\"teste@gmail.com\",\"subject\":\"Plant needs attention\",\"text\":\"The plant needs attention\"}";
        assertEquals(expected, handler.buildPayload(10.0));
    }
    
}
