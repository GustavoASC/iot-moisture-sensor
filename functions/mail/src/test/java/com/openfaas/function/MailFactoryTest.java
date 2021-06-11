package com.openfaas.function;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.junit.Test;

public class MailFactoryTest {

    @Test
    public void emptyBody() {
        assertEquals(Optional.empty(), new MailFactory().createFromJsonText(""));
    }

    @Test
    public void emptyJson() {
        assertEquals(Optional.empty(), new MailFactory().createFromJsonText("{}"));
    }

    @Test
    public void onlyRecipient() {
        assertEquals(Optional.empty(), new MailFactory().createFromJsonText("{\"recipient\": \"teste@gmail.com\"}"));
    }

    @Test
    public void onlyRecipientAndSubject() {
        assertEquals(Optional.empty(), new MailFactory().createFromJsonText("{\"recipient\": \"teste@gmail.com\", \"subject\": \"my subject\"}"));
    }

    @Test
    public void onlyRecipientAndText() {
        assertEquals(Optional.empty(), new MailFactory().createFromJsonText("{\"recipient\": \"teste@gmail.com\", \"text\": \"my text\"}"));
    }

    @Test
    public void validJsonContent() {
        var mail = new MailFactory().createFromJsonText("{\"recipient\": \"guscassel@gmail.com\", \"text\": \"my text\", \"subject\": \"my subject\"}").get();
        assertEquals("guscassel@gmail.com", mail.getTo());
        assertEquals("my subject", mail.getSubject());
        assertEquals("my text", mail.getText());
    }

    @Test
    public void emptyFields() {
        var mail = new MailFactory().createFromJsonText("{\"recipient\": \"\", \"text\": \"\", \"subject\": \"\"}").get();
        assertEquals("", mail.getTo());
        assertEquals("", mail.getSubject());
        assertEquals("", mail.getText());
    }

}
