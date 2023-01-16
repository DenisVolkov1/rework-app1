package main.dao.model;

public class SearchFilter {

	private String year;
	private String search;
	
	
	public SearchFilter(String year, String search) {
		super();
		this.year = year;
		this.search = search;
	}

	public SearchFilter() {}
	
	public String getYear() {
		return year;
	}
	public void setYear(String wms) {
		this.year = wms;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "SearchFilter [year=" + year + ", search=" + search + "]";
	}
	
}
