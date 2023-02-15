package main.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import main.dao.service.TelegramBot;


public class SendingMessages {
	
    @Autowired
    Environment env;
    
	@Autowired
	TelegramBot telegramBot;
    
    public void sendMailError(Exception err) throws MessagingException {
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		err.printStackTrace(pw);
		//PROPERTIEs
		final String username =          env.getProperty("save_backup_yandex_disk.gmail_smtp_mail");
        final String password =          env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_app_password");
        final String gmail_from_ardess = env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_from_adress");
        final String gmail_to_ardess =   env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_to_adress");

        Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

            Message message = new MimeMessage( session );
            message.setFrom(new InternetAddress( gmail_from_ardess ));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse( gmail_to_ardess )
            );
            message.setSubject("ERROR!!! CREATE BACKUP DATABASE(REWORKBASE)!");
            message.setText(sw.toString());

            Transport.send(message);

            System.out.println("Send-mail ERROR!!!");
	}
    
    public void sendMail(String sendingMessage) throws MessagingException {
		
		//PROPERTIEs
		final String username =          env.getProperty("save_backup_yandex_disk.gmail_smtp_mail");
        final String password =          env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_app_password");
        final String gmail_from_ardess = env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_from_adress");
        final String gmail_to_ardess =   env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_to_adress");

        Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "587");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

            Message message = new MimeMessage( session );
            message.setFrom(new InternetAddress( gmail_from_ardess ));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse( gmail_to_ardess )
            );
            message.setSubject("MESSAGE DATABASE(REWORKBASE)!");
            message.setText(sendingMessage);

            Transport.send(message);
	}
    
	public void sendTelegramError(Exception err) throws MessagingException {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		err.printStackTrace(pw);
		telegramBot.sendMessage(sw.toString());
	}
	
	public void sendTelegram(String sendingMessage) throws MessagingException {
		telegramBot.sendMessage(sendingMessage);
	}

}
