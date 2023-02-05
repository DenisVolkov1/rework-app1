package main.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.model.ITemplateStart;

import main.dao.service.TelegramBot;

/*
 * https://oauth.yandex.ru/authorize?response_type=token&client_id=dee1bd42bd86406eaf429a53afdcff62
 */
@PropertySource("classpath:config.properties")
@Component
public class SendBackupOnYandexDisk {
	
	@Autowired
	TelegramBot telegramBot;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Autowired
    Environment env;
    
    @Value("${save_backup_yandex_disk.path_MSSQLBackup}")
    private String pathOnBackupFolder;
    
	@Scheduled(initialDelayString = "${save_backup_yandex_disk.delay_milliseconds}", fixedDelayString = "${save_backup_yandex_disk.delay_milliseconds}")
	public void send() throws MessagingException {
		try {//int g = 1 / 0;
			
			//PROPERTIEs		
			String tokenOAuth = 		env.getProperty("save_backup_yandex_disk.tokenOAuth");
			String toPathDiskYandex = 	env.getProperty("save_backup_yandex_disk.topath_disk_yandex");
			//call proc CREATE_BACKUP_PROC
			/////////////////////////////////////////////////////////
			SimpleJdbcCall spCall = new SimpleJdbcCall(jdbcTemplate);
			spCall.withProcedureName("CREATE_BACKUP_PROC");
			spCall.addDeclaredParameter(new SqlParameter("path_MSSQLBackup", Types.NVARCHAR));
				spCall.execute(pathOnBackupFolder);
			
			File backupFileBAK = lastedBackup(pathOnBackupFolder);
			String nameFileAndExtension = backupFileBAK.getName();
			
			HttpRequest request;
			HttpResponse<String> response;

			request = HttpRequest.newBuilder()
					  .uri(new URI("https://cloud-api.yandex.net/v1/disk/resources/upload"
					  		+ "?path=" + toPathDiskYandex + nameFileAndExtension
					  		+ "&overwrite=true"))
					  .setHeader("Authorization",  tokenOAuth)
					  .GET()
					  .build();

			 response = HttpClient.newBuilder()
					  .build()
					  .send(request, BodyHandlers.ofString());	
			 
			JSONObject json = new JSONObject(response.body());
			
			if(response.statusCode() != 200) {
				throw new RuntimeException("\nError response status code:= "+response.statusCode() 
										  +"\nMessage = " + json.get("message") 
										  +"\nDescription = " + json.get("description")
										  +"\nError = " + json.get("error")+"\n");
			}		 
//			System.out.println("method = " + json.get("message"));  
//			System.out.println("templated = " + json.get("templated"));  
//			System.out.println("operation_id = " + json.get("operation_id"));  
//			System.out.println("href = " + json.get("href"));
			 request = HttpRequest.newBuilder()
					  .uri(new URI((String) json.get("href")))
					  .PUT(HttpRequest.BodyPublishers.ofFile(
							  backupFileBAK.toPath()))
					  .build();

			 response = HttpClient.newBuilder()
					  .build()
					  .send(request, BodyHandlers.ofString());
			 
			if(response.statusCode() != 201) {
				throw new RuntimeException("\nError response status code:= "+response.statusCode() 
										  +"\nMessage = " + response.body() + "\n");
			}
			
		} catch (Exception e) {
			// stack trace on mail 
			sendMailError(e);
			// stack trace on telegram
			sendTelegramError(e);
		}	        
	}
	
	private void sendMailError(Exception err) throws MessagingException {
		
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

            System.out.println("Send-mail");
	}
	
	private void sendTelegramError(Exception err) throws MessagingException {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		err.printStackTrace(pw);
		
		telegramBot.sendMessage(sw.toString());
		
	}
	
	private static List<File> listSortedFiles(String dir) {
	    return Stream.of(new File(dir).listFiles()).filter((t)->{return t.getAbsolutePath().matches(".*REWORKBASE_.*\\.bak$");}).sorted(Comparator.comparing(File::lastModified)).collect(Collectors.toList());
	}
	
	private static File lastedBackup(String dir) {
		List<File> backupFiles = listSortedFiles(dir);
		return backupFiles.get(backupFiles.size()-1);
	}
}