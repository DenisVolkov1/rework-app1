package main.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeRus {

	private LocalDateTime localDateTime;
	
	public LocalDateTimeRus(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	@Override
	public String toString() {
		if(localDateTime != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
		    String formatDateTime = localDateTime.format(formatter);
		    return formatDateTime;
		} else return "";
	}	
}
