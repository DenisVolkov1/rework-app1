package main.dao.model;

import java.time.LocalDateTime;

import main.util.LocalDateTimeRus;

public class Rework {
	
	private long serialKey;
	private String wms;
	private String reworkNumber;
	private String wikiLink;
	private String resource;
	private String description;
	private LocalDateTimeRus addDate;
	private String addWho;
	private String editWho;
	private LocalDateTimeRus editDate;
	
	
	public Rework(long serialKey, String wms, String reworkNumber,String resource, String wikiLink, String description,
			LocalDateTimeRus addDate, String addWho, String editWho, LocalDateTimeRus editDate) {
		super();
		this.serialKey = serialKey;
		this.wms = wms;
		this.reworkNumber = reworkNumber;
		this.resource = resource;
		this.wikiLink = wikiLink;
		this.description = description;
		this.addDate = addDate;
		this.addWho = addWho;
		this.editWho = editWho;
		this.editDate = editDate;
	}
	
	public Rework() {}

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
	public LocalDateTimeRus getAddDate() {
		return addDate;
	}
	public void setAddDate(LocalDateTimeRus addDate) {
		this.addDate = addDate;
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
	public LocalDateTimeRus getEditDate() {
		return editDate;
	}
	public void setEditDate(LocalDateTimeRus editDate) {
		this.editDate = editDate;
	}
	public long getSerialKey() {
		return serialKey;
	}

	public void setSerialKey(long serialKey) {
		this.serialKey = serialKey;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getResource() {
		return resource;
	}
	@Override
	public String toString() {
		return "Rework ["+reworkNumber+ " " + wms +"] "+getAddWho();
	}

	public String toStringAll() {
		return "Rework [serialKey=" + serialKey + ", wms=" + wms + ", reworkNumber=" + reworkNumber + ", wikiLink="
				+ wikiLink + ", resource=" + resource + ", description=" + description + ", addDate=" + addDate
				+ ", addWho=" + addWho + ", editWho=" + editWho + ", editDate=" + editDate + "]";
	}


	
	
	
	

}
