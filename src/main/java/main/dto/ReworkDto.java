package main.dto;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import main.dao.model.Rework;
import main.util.LocalDateTimeRus;
import main.util.LocalDateTimeRus.Pattern;

public class ReworkDto {
	
	private Rework rework;
	
	private Integer reworkNumber;
	private String description;
	private String[] tasks;
	private String taskMonetka;
	private Timestamp addDate;
	private String addWho;
	private String editWho;
	private Timestamp editDate;
	
	public ReworkDto(Rework rework) {
		this.rework = rework;
		
		this.reworkNumber = rework.getReworkNumber();
		this.description = rework.getDescription();
		this.tasks = (rework.getTask() == null) ? new String[] {} : rework.getTask().split(",");
		this.taskMonetka = rework.getTaskMonetka();	
		//this.addDate =  new LocalDateTimeRus(rework.getReworkAddDate().toLocalDateTime()).toString();
		this.addDate = rework.getReworkAddDate();
		this.addWho = rework.getAddWho();
		this.editWho = rework.getEditWho();
		this.editDate = rework.getReworkEditDate();
		//this.editDate = new LocalDateTimeRus(rework.getReworkEditDate().toLocalDateTime()).toString();
	}

	public Integer getReworkNumber() {
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

	public String[] getTasks() {
		for (String string : tasks) {
			System.out.println(string);
		}
		
		return tasks;
	}

	public void setTask(String[] tasks) {
		this.tasks = tasks;
	}

	public String getTaskMonetka() {
		return taskMonetka;
	}

	public void setTaskMonetka(String taskMonetka) {
		this.taskMonetka = taskMonetka;
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

	public String getAddDateDT() {	
		return new LocalDateTimeRus(addDate.toLocalDateTime(), Pattern.DATE_TIME).toString();
	}
	
	public String getEditDateDT() {
		return  new LocalDateTimeRus(editDate.toLocalDateTime(), Pattern.DATE_TIME).toString();
	}
	
	public String getAddDate() {	
		return new LocalDateTimeRus(addDate.toLocalDateTime(), Pattern.DATE).toString();
	}
	
	public String getEditDate() {
		return  new LocalDateTimeRus(editDate.toLocalDateTime(), Pattern.DATE).toString();
	}
	
	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	
	public void setEditDate(Timestamp editDate) {
		this.editDate = editDate;
	}

	public Rework getRework() {
		return rework;
	}
}
