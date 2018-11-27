package com.books.notegatewayapi.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    public void sendMail(String toEmail, String ccEmail, String subject, String message) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("support@groupbaz.com", "India1");

            }
        });
        session.setDebug(true);
        Message mes = new MimeMessage(session);
        mes.setFrom(new InternetAddress("support@groupbaz.com"));

        mes.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        mes.setRecipients(RecipientType.CC, InternetAddress.parse(ccEmail));
        mes.setSubject(subject);
        mes.setContent(message, "text/html; charset=utf-8");

        System.out.println("Sending");
        Transport.send(mes);
        System.out.println("Done");
        System.out.println("mail has been sent");
    }

    public void sendMailAttachment(String toEmail, String ccEmail, String subject, String message,
            String attachmentPath) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("support@groupbaz.com", "India1");
            }
        });
        session.setDebug(true);
        Multipart multipart = new MimeMultipart();
        Message mes = new MimeMessage(session);
        mes.setFrom(new InternetAddress("Groupbaz" + "<support@groupbaz.com>"));

        mes.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        mes.setRecipients(RecipientType.CC, InternetAddress.parse(ccEmail));
        mes.setSubject(subject);
        // mes.setContent(message, "text/html; charset=utf-8");

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setContent(message, "text/html; charset=utf-8");
        multipart.addBodyPart(textBodyPart);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        if (!attachmentPath.equals("")) {
            messageBodyPart = new MimeBodyPart();
            String fileName = "invoice.pdf";
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

        }
        mes.setContent(multipart);
        System.out.println("Sending");
        Transport.send(mes);
        System.out.println("Done");
        System.out.println("mail has been sent");
    }

    public static void main(String[] args) throws MessagingException {
        // sendMail("", "", "","");
    }
}
