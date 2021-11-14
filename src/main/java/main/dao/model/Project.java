package main.dao.model;

public class Project {
	
	private String project;

	public Project(String project) {
		super();
		this.project = project;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Project [project=" + project + "]";
	}
	

}
