package main.dao.model;

public class Project {

	private String name;
	private String partUrl;
	private String field1;
	private String field2;	
	
	public Project(String name, String partUrl, String field1, String field2) {
		super();
		this.name = name;
		this.partUrl = partUrl;
		this.field1 = field1;
		this.field2 = field2;
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

	@Override
	public String toString() {
		return "Project [name=" + name + ", partUrl=" + partUrl + ", field1=" + field1 + ", field2=" + field2 + "]";
	}
	
	
}
