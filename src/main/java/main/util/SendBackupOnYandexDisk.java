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
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.model.ITemplateStart;

@Component
public class SendBackupOnYandexDisk {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Autowired
    Environment env;
	
	@Scheduled(fixedDelayString = "${save_backup_yandex_disk.delay_milliseconds}")
	public void send() {
		try {
			int y = 1/0;
			//PROPERTIEs
			String pathOnBackupFolder = env.getProperty("save_backup_yandex_disk.path_MSSQLBackup");
			String tokenOAuth = env.getProperty("save_backup_yandex_disk.tokenOAuth");
			String toPathDiskYandex = env.getProperty("save_backup_yandex_disk.topath_disk_yandex");
			//call proc CREATE_BACKUP_PROC
			/////////////////////////////////////////////////////////
			SimpleJdbcCall spCall = new SimpleJdbcCall(jdbcTemplate);
			spCall.withProcedureName("CREATE_BACKUP_PROC");
			spCall.addDeclaredParameter(new SqlParameter("path_MSSQLBackup", Types.NVARCHAR));
			spCall.execute(pathOnBackupFolder);
			
			File backup = lastedBackup(pathOnBackupFolder);
	
			/*https://oauth.yandex.ru/authorize?response_type=token&client_id=dee1bd42bd86406eaf429a53afdcff62*/
			
			HttpRequest request;
			HttpResponse<String> response;

			request = HttpRequest.newBuilder()
					  .uri(new URI("https://cloud-api.yandex.net/v1/disk/resources/upload"
					  		+ "?path=" + toPathDiskYandex
					  		+ "&overwrite=true"))
					  .setHeader("Authorization",  tokenOAuth)
					  .GET()
					  .build();

			 response = HttpClient.newBuilder()
					  .build()
					  .send(request, BodyHandlers.ofString());	
			
			
			System.out.println("statuscode= "+response.statusCode()); 
			
			
			JSONObject json = new JSONObject(response.body());  
			System.out.println("method = " + json.get("method"));  
			System.out.println("templated = " + json.get("templated"));  
			System.out.println("operation_id = " + json.get("operation_id"));  
			System.out.println("href = " + json.get("href")); 
	
			
			 request = HttpRequest.newBuilder()
					  .uri(new URI((String) json.get("href")))
					  .PUT(HttpRequest.BodyPublishers.ofFile(
							  backup.toPath()))
					  .build();

			 response = HttpClient.newBuilder()
					  .build()
					  .send(request, BodyHandlers.ofString());

			 System.out.println("---------------------------------------------------"); 
			 System.out.println(response.statusCode()); 
			 System.out.println(response.body());
		} catch (Exception e) {
			// stack trace on mail 
			sendMailError(e);	
			
		}	        
	}
	
	private void sendMailError(Exception err) {
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		err.printStackTrace(pw);
		//PROPERTIEs
		final String username = env.getProperty("save_backup_yandex_disk.gmail_smtp_mail");
        final String password = env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_app_password");
        final String gmail_from_ardess = env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_from_adress");
        final String gmail_to_ardess = env.getProperty("save_backup_yandex_disk.gmail_smtp_mail_to_adress");
        

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

        try {

            Message message = new MimeMessage( session );
            message.setFrom(new InternetAddress( gmail_from_ardess ));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse( gmail_to_ardess )
            );
            message.setSubject("ERROR!!! create backup database(REWORKBASE)! ");
            message.setText(sw.toString());

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}
	private static List<File> listSortedFiles(String dir) {
	    //return Stream.of(new File(dir).listFiles()).filter((t)->{return t.getAbsolutePath().matches(".*REWORKBASE_.*\\.bak$");}).sorted(Comparator.comparing(File::lastModified)).collect(Collectors.toList());
		return Stream.of(new File(dir).listFiles()).filter((t)->{return t.getAbsolutePath().matches(".*\\.bak$");}).sorted(Comparator.comparing(File::lastModified)).collect(Collectors.toList());
	}
	
	private static File lastedBackup(String dir) {
		List<File> backupFiles = listSortedFiles(dir);
		return backupFiles.get(backupFiles.size()-1);
	}
}