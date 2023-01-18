package main.dto;

import main.dao.model.Rework;
import main.util.LocalDateTimeRus;

public class ReworkDto {
	
	private Rework rework;
	
	private int reworkNumber;
	private String description;
	private String task;
	private String taskMonetka;
	private String addDate;
	private String addWho;
	private String editWho;
	private String editDate;
	
	public ReworkDto(Rework rework) {
		this.rework = rework;
		
		this.reworkNumber = rework.getReworkNumber();
		this.description = rework.getDescription();
		this.task = rework.getTask();
		this.taskMonetka = rework.getTaskMonetka();	
		this.addDate =  new LocalDateTimeRus(rework.getReworkAddDate().toLocalDateTime()).toString();
		this.addWho = rework.getAddWho();
		this.editWho = rework.getEditWho();
		this.editDate = new LocalDateTimeRus(rework.getReworkEditDate().toLocalDateTime()).toString();
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

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getTaskMonetka() {
		return taskMonetka;
	}

	public void setTaskMonetka(String taskMonetka) {
		this.taskMonetka = taskMonetka;
	}

	public String getAddDate() {
		return addDate.toString();
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

	public Rework getRework() {
		return rework;
	}
}
