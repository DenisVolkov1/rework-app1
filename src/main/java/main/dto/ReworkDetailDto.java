package main.dto;

import main.dao.model.ReworkDetail;
import main.util.LocalDateTimeRus;
import main.util.Util;

public class ReworkDetailDto {
	
//	enum Server {
//		DEV("Дев сервер"),
//		EKB("ЕКБ ТЭЦ"),
//		EKB_BER("ЕКБ РЦ Берёзовский"),
//		NFU("Нефтеюганск"),
//		NSK("Новосибирск"),
//		UFA("Уфа");
//		
//		String name;
//		Server(String name) {
//			this.name = name;
//		}
//	}
	
	private ReworkDetail reworkDetail;
	
	private long serialKey;
	private int reworkNumber;
	private String server;
	private String status;
	private String addDate;
	private String addWho;
	private String editWho;
	private String editDate;
	
	public ReworkDetailDto(ReworkDetail reworkDetail) {
		this.reworkDetail = reworkDetail;
		
		this.serialKey = reworkDetail.getSerialKey();
		this.reworkNumber = reworkDetail.getReworkNumber();
		this.server = reworkDetail.getServer();
		this.status = Util.getUnicodeStatusWebApp(reworkDetail.getStatus());	
		this.addDate = (reworkDetail.getAddDate()) == null ? "" : new LocalDateTimeRus(reworkDetail.getAddDate().toLocalDateTime()).toString();
		this.addWho = reworkDetail.getAddWho();
		this.editWho = reworkDetail.getEditWho();
		this.editDate = (reworkDetail.getEditDate()) == null ? "" : new LocalDateTimeRus(reworkDetail.getEditDate().toLocalDateTime()).toString();
	}

	public long getSerialKey() {
		return serialKey;
	}

	public void setSerialKey(long serialKey) {
		this.serialKey = serialKey;
	}

	public int getReworkNumber() {
		return reworkNumber;
	}

	public void setReworkNumber(int reworkNumber) {
		this.reworkNumber = reworkNumber;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDateTimeRus addDate) {
		this.addDate = addDate.toString();
	}

	public String getAddWho() {
		return addWho;
	}

	public void setAddWho(String addWho) {
		this.addWho = addWho;
	}

	public String getEditWho() {
		return editWho;
	}

	public void setEditWho(String editWho) {
		this.editWho = editWho;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(LocalDateTimeRus editDate) {
		this.editDate = editDate.toString();
	}
}
