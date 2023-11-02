package main.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.interface_dao.StatusDao;
import main.dao.interface_dao.ProjectDao;
import main.dao.model.Status;
import main.dao.model.Project;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectDao wmsDao;

	public List<Project> findAll() {
		return wmsDao.findAll();
	}
	
	public Project getProjectByPartURL(String partUrl) {
		return wmsDao.getProjectByPartURL(partUrl);
	}

}
