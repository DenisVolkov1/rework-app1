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
		boolean b = isAllReworksInstalled(listrd); 
		for (int i = 0; i < listrd.size(); i++) {
			ReworkDetailDto detailDto = new ReworkDetailDto(listrd.get(i));
				detailDto.setAllReworksInstalled(b);
				arrayDetailDtos[i] = detailDto;
		}
		Arrays.sort(arrayDetailDtos);
		
		return arrayDetailDtos;
	}

	private static boolean isAllReworksInstalled(List<ReworkDetail> listrd) {
		for (ReworkDetail reworkDetail : listrd) {
			if(!reworkDetail.getStatus().equals("OK")) {
				return false;
			}
		}
		return true;
	}
}
