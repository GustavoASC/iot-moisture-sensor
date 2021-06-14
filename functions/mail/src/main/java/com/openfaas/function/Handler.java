package com.openfaas.function;

import java.util.Optional;

import javax.mail.MessagingException;
import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

public class Handler extends com.openfaas.model.AbstractHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
        try {
            var payload = req.getBody();
            var mailOpt = new MailFactory().createFromJsonText(payload);
            if (mailOpt.isPresent()) {
                var mail = mailOpt.get();
                try {                
                    mail.send();
                    res.setStatusCode(200);
                    res.setBody("Sent OK.");
                } catch (MessagingException e) {
                    res.setStatusCode(400);
                    res.setBody(e.toString());
                }
            } else {
                res.setStatusCode(400);
                res.setBody("Not all mandatory information was supplied.");
            }
        } catch (Throwable e) {
            res.setBody(e.toString());
        }
	    return res;
    }

}
