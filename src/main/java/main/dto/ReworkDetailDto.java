package main.dto;

import java.sql.Timestamp;

import main.dao.model.ReworkDetail;
import main.util.LocalDateTimeRus;
import main.util.Util;
import main.util.LocalDateTimeRus.Pattern;

public class ReworkDetailDto implements Comparable<ReworkDetailDto>{
	
	private ReworkDetail reworkDetail;
	
	private Long serialKey;
	private Integer reworkNumber;
	private String server;
	private String status;
	private Timestamp addDate;
	private String addWho;
	private String editWho;
	private Timestamp editDate;
	
	private boolean isAllReworksInstalled;
	
	public ReworkDetailDto(ReworkDetail reworkDetail) {
		this.reworkDetail = reworkDetail;
		
		this.serialKey = reworkDetail.getSerialKey();
		this.reworkNumber = reworkDetail.getReworkNumber();
		this.server = reworkDetail.getServer();
		this.status = Util.getUnicodeStatusWebApp(reworkDetail.getStatus());	
		this.addDate = reworkDetail.getAddDate();
		this.addWho = reworkDetail.getAddWho();
		this.editWho = reworkDetail.getEditWho();
		this.editDate = reworkDetail.getEditDate();
	}

	public Long getSerialKey() {
		return serialKey;
	}

	public void setSerialKey(long serialKey) {
		this.serialKey = serialKey;
	}

	public Integer getReworkNumber() {
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

	public String getAddWho() {
		return addWho;
	}

	public void setAddWho(String addWho) {
		this.addWho = addWho;
	}

	public String getEditWho() {
		return editWho;
	}

	public String getAddDateDT() {
		if(addDate == null) return "";
		return new LocalDateTimeRus(addDate.toLocalDateTime(), Pattern.DATE_TIME).toString();
	}
	
	public String getEditDateDT() {
		if(editDate == null) return "";
		else return  new LocalDateTimeRus(editDate.toLocalDateTime(), Pattern.DATE_TIME).toString();
	}
	
	public String getAddDate() {
		if(addDate == null) return "";
		else return new LocalDateTimeRus(addDate.toLocalDateTime(), Pattern.DATE).toString();
	}
	
	public String getEditDate() {
		if(editDate == null) return "";
		else return  new LocalDateTimeRus(editDate.toLocalDateTime(), Pattern.DATE).toString();
	}

	public void setEditDate(Timestamp editDate) {
		this.editDate = editDate;
	}
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

	@Override
	public int compareTo(ReworkDetailDto o) {
		return this.server.compareTo(o.getServer());
	}

	public boolean isAllReworksInstalled() {
		return isAllReworksInstalled;
	}

	public void setAllReworksInstalled(boolean isAllReworksInstalled) {
		this.isAllReworksInstalled = isAllReworksInstalled;
	}
}
