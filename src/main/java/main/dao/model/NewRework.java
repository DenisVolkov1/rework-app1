package main.dao.model;


public class NewRework {
	
	private String description;
	private String field1;
	private String field2;
	private String project;
	private String addWho;
	
	public NewRework(String description, String field1, String field2, String project, String addWho) {
		super();
		this.description = description;
		this.field1 = field1;
		this.field2 = field2;
		this.project = project;
		this.addWho = addWho;
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

	public String getProgect() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getAddWho() {
		return addWho;
	}

	public void setAddWho(String addWho) {
		this.addWho = addWho;
	}

	@Override
	public String toString() {
		return "NewRework [description=" + description + ", field1=" + field1 + ", field2=" + field2 + ", project="
				+ project + ", addWho=" + addWho + "]";
	}

}
