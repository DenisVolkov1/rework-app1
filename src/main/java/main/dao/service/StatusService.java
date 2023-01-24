package main.dao.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.interface_dao.StatusDao;
import main.dao.model.ReworkDetail;
import main.dao.model.Status;


@Service
public class StatusService {

	@Autowired
	private StatusDao statusDao;

	public List<Status> findAll() {
		return statusDao.findAll();
	}

	public String updateStatus(String reworkNumber, String server, String valueStatus, String whoUpdate) {
		return statusDao.updateStatus(reworkNumber, server, valueStatus, whoUpdate);	
	}

	public ReworkDetail getDateTooltip(String wms, String reworkNumber, String project) {
		return statusDao.getDateTooltip(wms, reworkNumber, project);	
	}
}
