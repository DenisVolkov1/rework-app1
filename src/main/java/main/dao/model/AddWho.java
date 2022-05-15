package main.dao.model;

public class AddWho {
	
	private String addWho;

	public AddWho(String addWho) {
		super();
		this.addWho = addWho;
	}

	public String getAddWho() {
		return addWho;
	}

	public void setAddWho(String addWho) {
		this.addWho = addWho;
	}

	@Override
	public String toString() {
		return "AddWho [addWho=" + addWho + "]";
	}
	

}
