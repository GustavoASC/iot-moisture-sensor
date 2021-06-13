package com.openfaas.function.mail;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MailDataTest {
    
    @Test
    public void testBuildEmailPayload() {
        var data = MailData.of("teste@gmail.com", "Mail subject", "Mail text");
        var payload = data.toJsonText();
        var expected = "{\"recipient\":\"teste@gmail.com\",\"subject\":\"Mail subject\",\"text\":\"Mail text\"}";
        assertEquals(expected, payload);
    }

}
