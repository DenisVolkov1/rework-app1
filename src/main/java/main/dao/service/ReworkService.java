package main.dao.service;

import java.util.List;

import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.dao.interface_dao.ReworkDao;
import main.dao.model.NewRework;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;


@Service
public class ReworkService {
	
	@Autowired
	private ReworkDao reworkDao;
	
	public Rework getRework(Integer serialkey) {
		return reworkDao.getRework(serialkey);
	}
	
	public void updateRework(int serialkey, Rework rework) {
		reworkDao.updateRework(serialkey,rework);
	}

	public void deleteRework(String wms, String reworkNumber) {
		reworkDao.deleteRework(wms, reworkNumber);
	}

	public void addNewRework(NewRework newRework) {
		reworkDao.addNewRework(newRework);
	}

	public List<Tuple2<Rework, List<ReworkDetail>>> findOnWmsAndSearchParams(String search) {
		return reworkDao.findOnSearchParam(search);
	}
	
	
}
