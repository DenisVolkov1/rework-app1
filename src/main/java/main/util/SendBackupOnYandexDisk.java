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
@PropertySource("classpath:config-${REWORK_APP1_MONETKA_RUNTYPE}.properties")
@Component
public class SendBackupOnYandexDisk {
	
	@Autowired
	TelegramBot telegramBot;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Autowired
    Environment env;
    
    @Autowired
    SendingMessages sm;

    
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
			sm.sendMailError(e);
			// stack trace on telegram
			sm.sendTelegramError(e);
		}	        
	}
	
	private static List<File> listSortedFiles(String dir) {
	    return Stream.of(new File(dir).listFiles()).filter((t)->{return t.getAbsolutePath().matches(".*REWORKBASE_.*\\.bak$");}).sorted(Comparator.comparing(File::lastModified)).collect(Collectors.toList());
	}
	
	private static File lastedBackup(String dir) {
		List<File> backupFiles = listSortedFiles(dir);
		return backupFiles.get(backupFiles.size()-1);
	}
}