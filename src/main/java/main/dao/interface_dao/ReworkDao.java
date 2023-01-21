package main.dao.interface_dao;

import java.util.List;

import org.jooq.lambda.tuple.Tuple2;

import main.dao.model.NewRework;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;

public interface ReworkDao {
	
	public List<String> findAllWhoUpdate();
	
	public Rework getRework(Integer serialkey);

    public void updateRework(int serialkey, Rework rework);

	public void deleteRework(String reworkNumber);
	
	public void addNewRework(NewRework newRework);

	public List<Tuple2<Rework, List<ReworkDetail>>> findOnSearchParam(String search);

	boolean isAlreadyExistsRework(String description);


}
