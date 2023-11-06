package main.dto;

import java.sql.Timestamp;

import main.config.MySpringConfig;
import main.dao.model.Archive;
import main.util.LocalDateTimeRus;
import main.util.Util;
import main.util.LocalDateTimeRus.Pattern;

public class ArchiveDto {

	private Integer reworkNumber;
	private String description;
	private String project;
	private String field1;
	private String field2;
	private Timestamp reworkAddDate;
	private Timestamp reworkEditDate;
	private Field1[] field1_list;
	private String redmain_issue;
	private String redmain_search_dev;
	private Field2[] field2_list;
	
	public ArchiveDto(Archive archive) {
		super();
		this.reworkNumber = archive.getReworkNumber();
		this.description = archive.getDescription();
		this.project = archive.getProject();	
		this.field1 = archive.getField1();
		this.field2 = archive.getField2();
		this.reworkAddDate = archive.getReworkAddDate();
		this.reworkEditDate = archive.getReworkEditDate();
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
	
	public String getAddDateD() {	
		return new LocalDateTimeRus(reworkAddDate.toLocalDateTime(), Pattern.DATE).toString();
	}
	
	public String getEditDateD() {
		return  new LocalDateTimeRus(reworkEditDate.toLocalDateTime(), Pattern.DATE).toString();
	}
	
	public Field1[] getRedmainTasks() {
		if(field1_list == null) {
			this.redmain_issue = MySpringConfig.redmain_issue;
			String[] arrTask = (field1 == null) ? new String[] {} : field1.split(",");
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
			String[] arrTask = (field2 == null) ? new String[] {} : field2.split(",");
			Field2[] res = new Field2[arrTask.length];
			for (int i = 0; i < arrTask.length; i++) {
				res[i] = new Field2(arrTask[i], redmain_search_dev + arrTask[i]);
			}
			field2_list = res;
		}
		return field2_list;
	}
	
}
