package main.dto;

import java.sql.Timestamp;

import main.config.MySpringConfig;
import main.dao.model.Rework;
import main.util.LocalDateTimeRus;
import main.util.LocalDateTimeRus.Pattern;


public class ReworkDto {
	
	String redmain_issue;
	String redmain_search_dev;
	
	private Rework rework;
	private Integer reworkNumber;
	private String description;
	private Field1[] field1_list;
	private String field1;
	private String field2;
	private Field2[] field2_list;
	private Timestamp addDate;
	private String addWho;
	private String editWho;
	private Timestamp editDate;
	
	public ReworkDto() {}
	
	public ReworkDto(Rework rework) {
		
		this.rework = rework;
		
		this.reworkNumber = rework.getReworkNumber();
		this.description = rework.getDescription();
		this.field1 = rework.getField1();
		this.field2 = rework.getField2();	
		this.addDate = rework.getReworkAddDate();
		this.addWho = rework.getAddWho();
		this.editWho = rework.getEditWho();
		this.editDate = rework.getReworkEditDate();
	}

	public Field1[] getRedmainTasks() {
		if(field1_list == null) {
			this.redmain_issue = MySpringConfig.redmain_issue;
			String[] arrTask = (rework.getField1() == null) ? new String[] {} : rework.getField1().split(",");
			Field1[] res = new Field1[arrTask.length];
			for (int i = 0; i < arrTask.length; i++) {
				res[i] = new Field1(arrTask[i], redmain_issue + arrTask[i]);
			}
			field1_list = res;
		}
		return field1_list;
	}
	
	public Field2[] getAnotherTasks() {
		if(field2_list == null) {
			this.redmain_search_dev = MySpringConfig.redmain_search_dev;
			String[] arrTask = (rework.getField2() == null) ? new String[] {} : rework.getField2().split(",");
			Field2[] res = new Field2[arrTask.length];
			for (int i = 0; i < arrTask.length; i++) {
				res[i] = new Field2(arrTask[i], redmain_search_dev + arrTask[i]);
			}
			field2_list = res;
		}
		return field2_list;
	}

	public void setField1(String field1) {
		this.field1 = field1;
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

	public void setField1List(Field1[] tasks) {
		this.field1_list = tasks;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
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
