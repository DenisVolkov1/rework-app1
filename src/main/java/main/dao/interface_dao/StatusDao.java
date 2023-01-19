package main.dao.interface_dao;

import java.util.List;

import main.dao.model.ReworkDetail;
import main.dao.model.Status;

public interface StatusDao {
	
	List<Status> findAll();

	ReworkDetail getDateTooltip(String wms, String reworkNumber, String project);

	void updateStatus(String reworkNumber, String server, String status, String whoUpdate);

}
