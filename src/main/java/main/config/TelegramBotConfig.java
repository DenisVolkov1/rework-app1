package main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config-${REWORK_APP1_MONETKA_RUNTYPE}.properties")
public class TelegramBotConfig {
	
    @Value("${save_backup_yandex_disk.telegram_bot_name}")
    String botName;
    @Value("${save_backup_yandex_disk.telegram_bot_key}")
    String botKey;
    
	public String getBotKey() {
		return botKey;
	}

	public String getBotName() {
		return botName;
	}
}
