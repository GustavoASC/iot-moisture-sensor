package com.cassel.function.sensorhandler;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Optional;

import com.openfaas.model.IRequest;

public class HandlerTest {
    
    @Test
    public void validBodyContent() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{ \"moisture\": 10}";
            }
    
        };
        var moisture = handler.extractMoisture(req);
        assertEquals(Optional.of(10.0), moisture);
    }
    
    @Test
    public void negativeBodyContent() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{ \"moisture\": -1}";
            }
    
        };
        var moisture = handler.extractMoisture(req);
        assertEquals(Optional.of(-1.0), moisture);
    }
    
    @Test
    public void invalidBodyContent() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{ \"moisture\": \"abc\"}";
            }
    
        };
        var moisture = handler.extractMoisture(req);
        assertEquals(Optional.empty(), moisture);
    }
    
    @Test
    public void missingBodyContent() {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{ \"abc\": \"abc\"}";
            }
    
        };
        var moisture = handler.extractMoisture(req);
        assertEquals(Optional.empty(), moisture);
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
