package main.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.interface_dao.AddWhoDao;
import main.dao.model.AddWho;

@Service
public class AddWhoService {
	
	@Autowired
	private AddWhoDao addWhoDao;

	public List<AddWho> findAll() {
		return addWhoDao.findAll();
	}

}
