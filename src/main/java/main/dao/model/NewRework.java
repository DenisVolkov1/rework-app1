package main.dao.model;


public class NewRework {
	
	private String description;
	private String task;
	private String taskMonetka;
	private String addWho;
	
	public NewRework(String description, String task, String taskMonetka, String addWho) {
		super();
		this.description = description;
		this.task = task;
		this.taskMonetka = taskMonetka;
		this.addWho = addWho;
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

	public String getAddWho() {
		return addWho;
	}

	public void setAddWho(String addWho) {
		this.addWho = addWho;
	}

	@Override
	public String toString() {
		return "NewRework [description=" + description + ", task=" + task + ", taskMonetka=" + taskMonetka + ", addWho="
				+ addWho + "]";
	}
}
