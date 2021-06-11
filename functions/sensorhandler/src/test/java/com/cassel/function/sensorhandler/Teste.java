package com.cassel.function.sensorhandler;

import java.util.Map;
import com.openfaas.model.IRequest;

public class Teste {

    public static void main(String[] args) {
        Handler handler = new Handler();
        IRequest req = new AbstractRequest() {

            @Override
            public String getBody() {
                return "{ \"moisture\": 2}";
            }
    
        };
        handler.Handle(req);
    }


    private static class AbstractRequest implements IRequest {

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
