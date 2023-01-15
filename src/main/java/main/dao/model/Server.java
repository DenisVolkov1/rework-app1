package main.dao.model;

public class Server {
	
	private String server;
	private Integer OURorder;

	public Server(String server) {
		super();
		this.server = server;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public String toString() {
		return "Server [project=" + server + "]";
	}

	public Integer getOURorder() {
		return OURorder;
	}

	public void setOURorder(Integer oURorder) {
		OURorder = oURorder;
	}
}