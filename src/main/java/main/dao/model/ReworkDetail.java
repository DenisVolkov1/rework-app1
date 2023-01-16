package main.dao.model;

import java.time.LocalDateTime;

import main.util.LocalDateTimeRus;

public class ReworkDetail {
	
	private long serialKey;
	private int reworkNumber;
	private String server;
	private String status;
	private LocalDateTimeRus addDate;
	private String addWho;
	private String editWho;
	private LocalDateTimeRus editDate;
	
	public ReworkDetail(long serialKey, int reworkNumber, String server, String status, LocalDateTimeRus addDate,
			String addWho, String editWho, LocalDateTimeRus editDate) {
		super();
		this.serialKey = serialKey;
		this.reworkNumber = reworkNumber;
		this.server = server;
		this.status = status;
		this.addDate = addDate;
		this.addWho = addWho;
		this.editWho = editWho;
		this.editDate = editDate;
	}

	public ReworkDetail() {
		// TODO Auto-generated constructor stub
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

	public LocalDateTimeRus getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDateTimeRus addDate) {
		this.addDate = addDate;
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

	public LocalDateTimeRus getEditDate() {
		return editDate;
	}

	public void setEditDate(LocalDateTimeRus editDate) {
		this.editDate = editDate;
	}
}	
	
	

