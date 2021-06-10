package com.cassel.function.mail;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

public class Handler implements IHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
	    res.setBody("Hello, world!");

	    return res;
    }
}
