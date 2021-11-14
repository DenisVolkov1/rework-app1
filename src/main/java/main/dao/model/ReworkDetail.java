package main.dao.model;

import java.time.LocalDateTime;

import main.util.LocalDateTimeRus;

public class ReworkDetail {
	
	private long serialKey;
	private String wms;
	private String reworkNumber;
	private String project;
	private String status;
	private LocalDateTimeRus addDate;
	private String addWho;
	private String editWho;
	private LocalDateTimeRus editDate;
	
	
	public ReworkDetail(long serialKey,String wms, String reworkNumber, String project, String status, LocalDateTimeRus addDate,
			String addWho, String editWho, LocalDateTimeRus editDate) {
		super();
		this.serialKey = serialKey;
		this.wms = wms;
		this.reworkNumber = reworkNumber;
		this.project = project;
		this.status = status;
		this.addDate = addDate;
		this.addWho = addWho;
		this.editWho = editWho;
		this.editDate = editDate;
	}
	public ReworkDetail(String wms, String reworkNumber, String project, String status) {
		this.wms = wms;
		this.reworkNumber = reworkNumber;
		this.project = project;
		this.status = status;
	}

	public ReworkDetail() {}
	
	public String getReworkNumber() {
		return reworkNumber;
	}

	public void setReworkNumber(String reworkNumber) {
		this.reworkNumber = reworkNumber;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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
	public void setSerialKey(long long1) {
		serialKey = long1;
		
	}
	public long getSerialKey() {
		return serialKey;
	}
	public String getWms() {
		return wms;
	}

	public void setWms(String wms) {
		this.wms = wms;
	}
	@Override
	public String toString() {
		return "[" + reworkNumber + " " + wms +" :  proj=" + project + ", status=" + status + "]";
	}



	
	
	
	
	

}
