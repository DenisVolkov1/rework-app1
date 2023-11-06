package main.dao.model;

import java.sql.Timestamp;

import main.util.LocalDateTimeRus;
import main.util.LocalDateTimeRus.Pattern;

public class Archive {
	
	private Integer reworkNumber;
	private String description;
	private String project;
	private String field1;
	private String field2;
	private Timestamp reworkAddDate;
	private Timestamp reworkEditDate;
	
	public Archive(Integer reworkNumber, String description, String project, String field1, String field2,
			Timestamp reworkAddDate, Timestamp reworkEditDate) {
		super();
		this.reworkNumber = reworkNumber;
		this.description = description;
		this.project = project;
		this.field1 = field1;
		this.field2 = field2;
		this.reworkAddDate = reworkAddDate;
		this.reworkEditDate = reworkEditDate;
	}

	public Integer getReworkNumber() {
		return reworkNumber;
	}

	public void setReworkNumber(Integer reworkNumber) {
		this.reworkNumber = reworkNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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

	public void setReworkAddDate(Timestamp reworkAddDate) {
		this.reworkAddDate = reworkAddDate;
	}

	public Timestamp getReworkEditDate() {
		return reworkEditDate;
	}

	public void setReworkEditDate(Timestamp reworkEditDate) {
		this.reworkEditDate = reworkEditDate;
	}

	@Override
	public String toString() {
		return "Archive [reworkNumber=" + reworkNumber + ", description=" + description + ", project=" + project
				+ ", field1=" + field1 + ", field2=" + field2 + ", reworkAddDate=" + reworkAddDate + ", reworkEditDate="
				+ reworkEditDate + "]";
	}

	
}
