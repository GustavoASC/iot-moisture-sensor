package com.openfaas.function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    private final String from;
    private final String to;
    private final String subject;
    private final String text;

    private Mail(String from, String to, String subject, String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public static Mail of(String from, String to, String subject, String text) {
        return new Mail(from, to, subject, text);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public void send() throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(from));
        Address[] toUser = InternetAddress.parse(to);
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(subject);
        message.setContent(text, "text/html");
        Transport.send(message);
    }

    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props, new Authenticator());
        session.setDebug(true);
        return session;
    }

    private class Authenticator extends javax.mail.Authenticator {
        
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            try {
                String pass = Files.readString(Path.of("/var/openfaas/secrets/mail-pass"));
                return new PasswordAuthentication(from, pass);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
