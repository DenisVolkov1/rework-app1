package main.util;

import java.util.Arrays;
import java.util.List;

import main.dao.model.ReworkDetail;
import main.dto.ReworkDetailDto;

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
        	   ret= sqlStatus;
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
        	   ret= sqlStatus;
               break;
		}
		return ret;
	}
	
	public static ReworkDetailDto[] getArrReworkDetailDto(int countServers , List<ReworkDetail> listrd) {
		
		ReworkDetailDto[] arrayDetailDtos = new ReworkDetailDto[ countServers ];
		
		for (int i = 0; i < listrd.size(); i++) {
			arrayDetailDtos[i] = new ReworkDetailDto(listrd.get(i));
		}
		
		Arrays.sort(arrayDetailDtos );
		
		/*for(ReworkDetail rd : listrd) {
			
			switch (rd.getServer()) {
				case "Дев сервер" :
					{
						arrayDetailDtos[0] = new ReworkDetailDto(rd);
					}
					break;
				case "ЕКБ ТЭЦ" :
					{
						arrayDetailDtos[1] = new ReworkDetailDto(rd);
					}
					break;
				case "ЕКБ РЦ Берёзовский" :
					{
						arrayDetailDtos[2] = new ReworkDetailDto(rd);
					}
					break;
				case "Нефтеюганск" :
					{
						arrayDetailDtos[3] = new ReworkDetailDto(rd);
					}
					break;
				case "Новосибирск" :
					{
						arrayDetailDtos[4] = new ReworkDetailDto(rd);
					}
				break;
				case "Уфа" :
					{
						arrayDetailDtos[5] = new ReworkDetailDto(rd);
					}
				break;
				case "Пермь" :
				{
					arrayDetailDtos[6] = new ReworkDetailDto(rd);
				}
			break;
				default: {throw new IllegalArgumentException("Не существующий сервер! : " + rd.getServer());}
			}
		}*/
		
		return arrayDetailDtos;
		
		
	}
}
