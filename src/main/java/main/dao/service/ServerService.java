package main.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.interface_dao.ServerDao;
import main.dao.model.Server;

@Service
public class ServerService {

	@Autowired
	private ServerDao projectDao;

	public List<Server> findAll() {
		return projectDao.findAll();
	}
}
