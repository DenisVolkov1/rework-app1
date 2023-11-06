package main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import main.dao.service.TelegramBot;

@Component
@PropertySource("classpath:config-${REWORK_APP1_MONETKA_RUNTYPE}.properties")
public class BotInit {
	
    @Autowired
    TelegramBot telegramBot;
    
    @Value("${is_telegram_bot_run}")
    String isTelegramBotRun;
    
    @EventListener({ContextRefreshedEvent.class})
    public void init() {
    	//System.out.println("_telegram_init");
    	if(isTelegramBotRun.equals("1")) {
    		//System.out.println("_telegram_bot_run");
    		TelegramBotsApi botsApi = null;
	        try {
	            botsApi = new TelegramBotsApi(DefaultBotSession.class);
	            botsApi.registerBot(telegramBot);
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
    	}

    }
}
