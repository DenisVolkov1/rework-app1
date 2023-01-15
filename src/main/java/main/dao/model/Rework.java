package main.dao.model;

import java.time.LocalDateTime;

import main.util.LocalDateTimeRus;

public class Rework {
	
	private int reworkNumber;
	private String description;
	private String task;
	private String taskMonetka;
	private LocalDateTimeRus addDate;
	private String addWho;
	private String editWho;
	private LocalDateTimeRus editDate;
	
	
	
	public Rework(int reworkNumber, String description,String task,String taskMonetka, LocalDateTimeRus addDate, String addWho, String editWho,
			LocalDateTimeRus editDate) {
		super();
		this.reworkNumber = reworkNumber;
		this.description = description;
		this.task = task;
		this.taskMonetka = taskMonetka;
		this.addDate = addDate;
		this.addWho = addWho;
		this.editWho = editWho;
		this.editDate = editDate;
	}
	
	public int getReworkNumber() {
		return reworkNumber;
	}
	public void setReworkNumber(int reworkNumber) {
		this.reworkNumber = reworkNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
