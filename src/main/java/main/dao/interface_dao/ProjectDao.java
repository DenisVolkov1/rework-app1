package main.dao.interface_dao;

import java.util.List;

import main.dao.model.Project;

public interface ProjectDao {

	List<Project> findAll();

	Project getProjectByPartURL(String partUrl);
}
