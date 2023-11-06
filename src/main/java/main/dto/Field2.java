package main.dto;

public class Field2 {
	
	private String task;
	private String taskUrl;
	
	public Field2(String task, String taskUrl) {
		super();
		this.task = task;
		this.taskUrl = taskUrl;
	}
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTaskUrl() {
		return taskUrl;
	}
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	
}
