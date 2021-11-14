package main.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.interface_dao.StatusDao;
import main.dao.interface_dao.WmsDao;
import main.dao.model.Status;
import main.dao.model.Wms;

@Service
public class WmsService {
	
	@Autowired
	private WmsDao wmsDao;

	public List<Wms> findAll() {
		return wmsDao.findAll();
	}

}
