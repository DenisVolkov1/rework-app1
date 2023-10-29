package main.dao.model;

public class Project {

	private String name;
	private String partUrl;
	
	public Project(String name, String partUrl) {
		super();
		this.name = name;
		this.partUrl = partUrl;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartUrl() {
		return partUrl;
	}
	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", partUrl=" + partUrl + "]";
	}

}
