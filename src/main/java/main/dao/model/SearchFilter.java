package main.dao.model;

public class SearchFilter {

	private String search;
	
	public SearchFilter(String search) {
		this.search = search;
	}

	public SearchFilter() {}
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "SearchFilter [search=" + search + "]";
	}
}
