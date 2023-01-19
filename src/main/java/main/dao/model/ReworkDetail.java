package main.dao.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import main.util.LocalDateTimeRus;

public class ReworkDetail {
	
	private Long serialKey;
	private Integer reworkNumber;
	private String server;
	private String status;
	private Timestamp addDate;
	private String addWho;
	private String editWho;
	private Timestamp editDate;
	
	public ReworkDetail(long serialKey, int reworkNumber, String server, String status, Timestamp addDate,
			String addWho, String editWho, Timestamp editDate) {
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
		//
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

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
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

	public Timestamp getEditDate() {
		return editDate;
	}

	public void setEditDate(Timestamp editDate) {
		this.editDate = editDate;
	}

	@Override
	public String toString() {
		return "ReworkDetail [serialKey=" + serialKey + ", reworkNumber=" + reworkNumber + ", server=" + server
				+ ", status=" + status + ", addDate=" + addDate + ", addWho=" + addWho + ", editWho=" + editWho
				+ ", editDate=" + editDate + "]";
	}	
}	
	
	

