package main.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendBackupOnYandexDisk {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Autowired
    Environment env;
	
	@Scheduled(fixedDelayString = "${save_backup_yandex_disk.delay_milliseconds}")
	public void send() {
		
		try {

			//call proc CREATE_BACKUP_PROC
			/////////////////////////////////////////////////////////
			SimpleJdbcCall spCall = new SimpleJdbcCall(jdbcTemplate);
			spCall.withProcedureName("CREATE_BACKUP_PROC");
			spCall.addDeclaredParameter(new SqlParameter("path_MSSQLBackup", Types.NVARCHAR));
			spCall.execute(env.getProperty("save_backup_yandex_disk.path_MSSQLBackup"));
			
			System.out.println("Date : "+new Date().toLocaleString());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		/*https://oauth.yandex.ru/authorize?response_type=token&client_id=dee1bd42bd86406eaf429a53afdcff62*/
		
		String pathFile = 
				"C:\\Users\\Denis\\Documents\\Деталь.m3d";
		String tokenOAuth = "y0_AgAEA7qjGxM3AAkPgwAAAADa5Qg6OREkZyy-QLaZpAivr57x4ERnQZk";
		String toPathDiskYandex = "%2FDrawing1.dwg";
		
		HttpRequest request;
		HttpResponse<String> response;
		try {
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
					    Paths.get(pathFile)))
					  .build();

			 response = HttpClient.newBuilder()
					  .build()
					  .send(request, BodyHandlers.ofString());

			 System.out.println("---------------------------------------------------"); 
			System.out.println(response.statusCode()); 
			System.out.println(response.body());
		} catch (Exception e) {
			e.printStackTrace();
		}	        
	
	}
}