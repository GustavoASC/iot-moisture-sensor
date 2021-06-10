package com.cassel.function.mail;

import java.util.Optional;

import javax.mail.MessagingException;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

public class Handler implements IHandler {

    public IResponse Handle(IRequest req) {
        parseJsonMail(req).ifPresent(mail -> {
            try {                
                mail.send();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        Response res = new Response();
	    res.setBody("Hello, world!");
	    return res;
    }

    Optional<Mail> parseJsonMail(IRequest req) {
        var payload = req.getBody();
        return new MailFactory().createFromJsonText(payload);
    }

}
