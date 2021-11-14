package main.dao.model;

public class Wms {

	private String wms;

	public Wms(String wms) {
		super();
		this.wms = wms;
	}

	public String getWms() {
		return wms;
	}

	public void setWms(String wms) {
		this.wms = wms;
	}
	
	
	@Override
	public String toString() {
		return "WMS [wms=" + wms + "]";
	}
}
