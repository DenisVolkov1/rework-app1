package main.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeRus {

	private LocalDateTime localDateTime;
	private Pattern pattern;
	
	public enum Pattern {
		DATE("dd.MM.yy"),
		DATE_TIME("dd.MM.yyyy HH:mm");
		
		String string;
		Pattern(String string) { this.string = string; }
		

		public String getPattern() { return string; }
	}
	
	public LocalDateTimeRus(LocalDateTime localDateTime, Pattern pattern) {
		this.localDateTime = localDateTime;
		this.pattern = pattern;
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
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.getPattern());
		    String formatDateTime = localDateTime.format(formatter);
		    return formatDateTime;
		} else return "";
	}	
}
