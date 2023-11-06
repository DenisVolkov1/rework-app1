package main.dao.interface_dao;

import java.util.List;

import main.dao.model.Server;

public interface ServerDao {
	
	List<Server> findAll();

	List<Server> findAllByProject(String project);

}
