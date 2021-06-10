package com.cassel.function.mail;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Map;
import java.util.Optional;
import com.openfaas.model.IRequest;

public class HandlerTest {

    @Test
    public void emptyBody() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "";
            }
    
        };
        assertEquals(Optional.empty(), handler.parseJsonMail(req));
    }

    @Test
    public void emptyJson() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{}";
            }
    
        };
        assertEquals(Optional.empty(), handler.parseJsonMail(req));
    }

    @Test
    public void onlyRecipient() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{\"recipient\": \"teste@gmail.com\"}";
            }
    
        };
        assertEquals(Optional.empty(), handler.parseJsonMail(req));
    }

    @Test
    public void onlyRecipientAndSubject() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{\"recipient\": \"teste@gmail.com\", \"subject\": \"my subject\"}";
            }
    
        };
        assertEquals(Optional.empty(), handler.parseJsonMail(req));
    }

    @Test
    public void onlyRecipientAndText() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{\"recipient\": \"teste@gmail.com\", \"text\": \"my text\"}";
            }
    
        };
        assertEquals(Optional.empty(), handler.parseJsonMail(req));
    }

    @Test
    public void validJsonContent() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{\"recipient\": \"teste@gmail.com\", \"text\": \"my text\", \"subject\": \"my subject\"}";
            }
    
        };
        var mail = handler.parseJsonMail(req).get();
        assertEquals("teste@gmail.com", mail.getTo());
        assertEquals("my subject", mail.getSubject());
        assertEquals("my text", mail.getText());



        handler.Handle(req);
    }

    @Test
    public void emptyFields() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{\"recipient\": \"\", \"text\": \"\", \"subject\": \"\"}";
            }
    
        };
        var mail = handler.parseJsonMail(req).get();
        assertEquals("", mail.getTo());
        assertEquals("", mail.getSubject());
        assertEquals("", mail.getText());
    }

    private class AbstractRequest implements IRequest {

        @Override
        public String getBody() {
            return null;
        }

        @Override
        public Map<String, String> getHeaders() {
            return null;
        }

        @Override
        public String getHeader(String key) {
            return null;
        }

        @Override
        public String getQueryRaw() {
            return null;
        }

        @Override
        public Map<String, String> getQuery() {
            return null;
        }

        @Override
        public String getPathRaw() {
            return null;
        }

        @Override
        public Map<String, String> getPath() {
            return null;
        }

    }

}
