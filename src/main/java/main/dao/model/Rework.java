package main.dao.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import main.util.LocalDateTimeRus;

public class Rework {
	
	private Integer reworkNumber;
	private String description;
	private String field1;
	private String field2;
	private String project;
	private Timestamp reworkAddDate;
	private String addWho;
	private String editWho;
	private Timestamp reworkEditDate;
	
	
	
	public Rework(int reworkNumber, String description,String field1,String field2,String project, Timestamp addDate, String addWho, String editWho,
			Timestamp editDate) {
		super();
		this.reworkNumber = reworkNumber;
		this.description = description;
		this.field1 = field1;
		this.field2 = field2;
		this.project = project;
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

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Rework [reworkNumber=" + reworkNumber + ", description=" + description + ", field1=" + field1
				+ ", field2=" + field2 + ", project=" + project +", addDate=" + reworkAddDate + ", addWho=" + addWho + ", editWho="
				+ editWho + ", editDate=" + reworkEditDate + "]";
	}


}
