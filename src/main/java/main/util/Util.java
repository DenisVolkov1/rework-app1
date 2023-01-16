package main.util;

public class Util {
	
	private Util() {}
	
	public static String getUnicodeStatusWebApp(String sqlStatus) {
		
		
		String ret = "";
		switch (sqlStatus) {
	        case  (""):
	     	   ret= "";
	            break;
           case ("OK"):
        	   ret= "✔";
               break;
           case ("TEST"):
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
           case ("✔"):
        	   ret= "OK";
               break;
           case ("\uD83E\uDDEA"):
        	   ret= "TEST";
               break;
           default:
        	   ret= "OK";
               break;
		}
		return ret;
	}
}
