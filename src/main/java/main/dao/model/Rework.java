package main.dao.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import main.util.LocalDateTimeRus;

public class Rework {
	
	private Integer reworkNumber;
	private String description;
	private String task;
	private String taskMonetka;
	private Timestamp reworkAddDate;
	private String addWho;
	private String editWho;
	private Timestamp reworkEditDate;
	
	
	
	public Rework(int reworkNumber, String description,String task,String taskMonetka, Timestamp addDate, String addWho, String editWho,
			Timestamp editDate) {
		super();
		this.reworkNumber = reworkNumber;
		this.description = description;
		this.task = task;
		this.taskMonetka = taskMonetka;
		this.reworkAddDate = addDate;
		this.addWho = addWho;
		this.editWho = editWho;
		this.reworkEditDate = editDate;
	}
	
	public Rework() {
		// TODO Auto-generated constructor stub
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

	public Timestamp getReworkAddDate() {
		return reworkAddDate;
	}

	public void setReworkAddDate(Timestamp addDate) {
		this.reworkAddDate = addDate;
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

	public Timestamp getReworkEditDate() {
		return reworkEditDate;
	}

	public void setReworkEditDate(Timestamp editDate) {
		this.reworkEditDate = editDate;
	}




	@Override
	public String toString() {
		return "Rework [reworkNumber=" + reworkNumber + ", description=" + description + ", task=" + task
				+ ", taskMonetka=" + taskMonetka + ", addDate=" + reworkAddDate + ", addWho=" + addWho + ", editWho="
				+ editWho + ", editDate=" + reworkEditDate + "]";
	}
}
