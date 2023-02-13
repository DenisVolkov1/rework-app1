package main.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import main.config.TelegramBotConfig;


@Component
public class TelegramBot extends TelegramLongPollingBot{


    @Autowired
    TelegramBotConfig botConfig;
    
    @Value("${save_backup_yandex_disk.telegram_chat_id}")
    long chatID;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotKey();
    }

	@Override
	public void onUpdateReceived(Update update) {
		System.out.println("Telegram.chatID="+update.getMessage().getChatId());
	}
	
    //@Scheduled(fixedRateString = "10000")
    public void sendMessage(String messageString){

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(messageString);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
