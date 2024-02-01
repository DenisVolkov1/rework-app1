package main.dao.service;

import java.util.List;

import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.dao.interface_dao.ReworkDao;
import main.dao.model.Archive;
import main.dao.model.NewRework;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;


@Service
public class ReworkService {
	
	@Autowired
	private ReworkDao reworkDao;
	
	public Rework getRework(Integer reworkNumber) {
		return reworkDao.getRework(reworkNumber);
	}
	
	public void updateRework(int serialkey, Rework rework) {
		reworkDao.updateRework(serialkey,rework);
	}

	public void hideRework(String reworkNumber) {
		reworkDao.hideRework(reworkNumber);
	}

	public void addNewRework(NewRework newRework,String project) {
		reworkDao.addNewRework(newRework, project);
	}

	public List<Tuple2<Rework, List<ReworkDetail>>> findOnSearchParam(String search, String project) {
		return reworkDao.findOnSearchParam(search, project);
	}

	public boolean isAlreadyExistsRework(String description,String project) {
		return reworkDao.isAlreadyExistsRework(description,project);
	}
	
	public boolean isAlreadyExistsRework(String reworkNumber,String description,String project) {
		return reworkDao.isAlreadyExistsRework(reworkNumber,description,project);
	}

	public List<Archive> findOnSearchParamInArchive(String search) {
		return reworkDao.findOnSearchParamInArchive(search);
	}
	
	
}
