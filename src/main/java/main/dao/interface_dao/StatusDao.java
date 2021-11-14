package main.dao.interface_dao;

import java.util.List;

import main.dao.model.ReworkDetail;
import main.dao.model.Status;

public interface StatusDao {
	
	List<Status> findAll();

	void updateStatus(String wms, String reworkNumber, String project, String valueStatus);

	ReworkDetail getDateTooltip(String wms, String reworkNumber, String project);

}
