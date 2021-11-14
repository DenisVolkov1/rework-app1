package main.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.interface_dao.ProjectDao;
import main.dao.model.Project;

@Service
public class ProjectService {

	@Autowired
	private ProjectDao projectDao;

	public List<Project> findAll() {
		return projectDao.findAll();
	}
}
