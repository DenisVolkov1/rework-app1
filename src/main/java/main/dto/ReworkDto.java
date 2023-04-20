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
	private RedmainTask[] redmainTasks;
	private String tasks;
	private String taskMonetka;
	private MonetkaTask[] monetkaTasks;
	private Timestamp addDate;
	private String addWho;
	private String editWho;
	private Timestamp editDate;
	
	public ReworkDto() {}
	
	public ReworkDto(Rework rework) {
		
		this.rework = rework;
		
		this.reworkNumber = rework.getReworkNumber();
		this.description = rework.getDescription();
		this.tasks = rework.getTasks();
		this.taskMonetka = rework.getTaskMonetka();	
		this.addDate = rework.getReworkAddDate();
		this.addWho = rework.getAddWho();
		this.editWho = rework.getEditWho();
		this.editDate = rework.getReworkEditDate();
	}

	public RedmainTask[] getRedmainTasks() {
		if(redmainTasks == null) {
			this.redmain_issue = MySpringConfig.redmain_issue;
			String[] arrTask = (rework.getTasks() == null) ? new String[] {} : rework.getTasks().split(",");
			RedmainTask[] res = new RedmainTask[arrTask.length];
			for (int i = 0; i < arrTask.length; i++) {
				res[i] = new RedmainTask(arrTask[i], redmain_issue + arrTask[i]);
			}
			redmainTasks = res;
		}
		return redmainTasks;
	}
	
	public MonetkaTask[] getMonetkaTasks() {
		if(monetkaTasks == null) {
			this.redmain_search_dev = MySpringConfig.redmain_search_dev;
			String[] arrTask = (rework.getTaskMonetka() == null) ? new String[] {} : rework.getTaskMonetka().split(",");
			MonetkaTask[] res = new MonetkaTask[arrTask.length];
			for (int i = 0; i < arrTask.length; i++) {
				res[i] = new MonetkaTask(arrTask[i], redmain_search_dev + arrTask[i]);
			}
			monetkaTasks = res;
		}
		return monetkaTasks;
	}
	

	public void setTasks(String tasks) {
		this.tasks = tasks;
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

	public String getTasks() {
		return tasks;
	}

	public void setRedmainTasks(RedmainTask[] tasks) {
		this.redmainTasks = tasks;
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
