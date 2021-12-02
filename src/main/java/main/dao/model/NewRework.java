package main.dao.model;


public class NewRework {
	
	private String wms;
	private String reworkNumber;
	private String resource;
	private String wikiLink;
	private String description;
	private String project;
	private String status;
	private String addWho;
	private String editWho;
	
	public NewRework(String wms, String reworkNumber, String resource,String wikiLink, String description, String project,
			String status, String addWho, String editWho) {
		super();
		this.wms = wms;
		this.reworkNumber = reworkNumber;
		this.wikiLink = resource;
		this.wikiLink = wikiLink;
		this.description = description;
		this.project = project;
		this.status = status;
		this.addWho = addWho;
		this.editWho = editWho;
	}
	
	
	public String getWms() {
		return wms;
	}
	public void setWms(String wms) {
		this.wms = wms;
	}
	public String getReworkNumber() {
		return reworkNumber;
	}
	public void setReworkNumber(String reworkNumber) {
		this.reworkNumber = reworkNumber;
	}
	public String getWikiLink() {
		return wikiLink;
	}
	public void setWikiLink(String wikiLink) {
		this.wikiLink = wikiLink;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public void setEditWho(String editWho) {
		this.editWho = editWho;
	}


	@Override
	public String toString() {
		return "NewRework [wms=" + wms + ", reworkNumber=" + reworkNumber + ", resource=" + resource + ", wikiLink=" + wikiLink + ", description="
				+ description + ", project=" + project + ", status=" + status + ", addWho=" + addWho + ", editWho="
				+ editWho + "]";
	}

	

}
