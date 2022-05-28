package main.util;

public class Util {
	
	private Util() {}
	
	public static String getUnicodeStatusWebApp(String sqlStatus) {
		
		String ret = "";
		switch (sqlStatus) {
	        case  (""):
	     	   ret= "";
	            break;
           case  ("Нов."):
        	   ret= "\uD83D\uDCC4";
               break;
           case ("Уст."):
        	   ret= "✔";
               break;
           case ("Тест"):
        	   ret= "\uD83E\uDDEA";
               break;
           default:
        	   ret= "✔";
               break;
		}
		return ret;
	}
	
	public static String getStatusSql(String sqlStatus) {
		
		String ret = "";
		switch (sqlStatus) {
           case  ("\uD83D\uDCC4"):
        	   ret= "Нов.";
               break;
           case ("✔"):
        	   ret= "Уст.";
               break;
           case ("\uD83E\uDDEA"):
        	   ret= "Тест";
               break;
           default:
        	   ret= "Уст.";
               break;
		}
		return ret;
	}

}
