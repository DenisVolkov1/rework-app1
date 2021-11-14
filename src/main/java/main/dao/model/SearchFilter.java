package main.dao.model;

public class SearchFilter {

	private String wms;
	private String search;
	
	
	public SearchFilter(String wms, String search) {
		super();
		this.wms = wms;
		this.search = search;
	}

	public SearchFilter() {}
	
	public String getWms() {
		return wms;
	}
	public void setWms(String wms) {
		this.wms = wms;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "SearchFilter [wms=" + wms + ", search=" + search + "]";
	}
	
}
