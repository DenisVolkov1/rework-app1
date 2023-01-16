package main.dao.impl_dao.mssql;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.simpleflatmapper.util.TypeReference;
import org.jooq.lambda.tuple.Tuple2;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import main.dao.impl_dao.rowmapper.ReworkRowMapper;
import main.dao.interface_dao.ReworkDao;
import main.dao.model.NewRework;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;

@Component
public class ReworkImplMSSQL implements ReworkDao {
	
	private JdbcTemplate jdbcTemplate;  
	
	
	public ReworkImplMSSQL(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private boolean isContainsProject(List<ReworkDetail> rdList, String project) {
//		for(ReworkDetail fr : rdList) {
//			if(fr.getProject().equals(project)) {
//				//System.out.println(fr.getProject() +" == "+project);
//				return true;
//			}
//		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Rework getRework(Integer serialkey) {
		
        String sql = "SELECT * FROM REWORK WHERE SERIALKEY = ?";

        return (Rework) jdbcTemplate.queryForObject(
			sql, 
			new Object[]{serialkey}, 
			new ReworkRowMapper());
	}

	@Override
	public void updateRework(int serialkey, Rework rework) {
//		String sqlUpdate = "UPDATE REWORK set DESCRIPTION = ?, RESOURCE = ?, WIKILINK = ?,EDITDATE = GETUTCDATE() WHERE SERIALKEY = ?; ";
//		String descr = rework.getDescription();
//		String wikilink = rework.getWikiLink();
//		String resource = rework.getResource();
//		
//		jdbcTemplate.update(
//				sqlUpdate, 
//				resource, descr, wikilink, serialkey);
		
	}

	@Override
	@Transactional()
	public void deleteRework(String wms, String reworkNumber) {
		String sqlDelete = "DELETE FROM REWORKDETAIL WHERE WMS = ? AND REWORKNUMBER = ?; ";
		jdbcTemplate.update(sqlDelete, wms, reworkNumber);
			sqlDelete = "DELETE FROM REWORK WHERE WMS = ? AND REWORKNUMBER = ?; ";
				jdbcTemplate.update(sqlDelete, wms, reworkNumber);
		
	}


	@Transactional()
	@Override
	public void addNewRework(NewRework newRework) {
		String wms = newRework.getWms().replace(",", "");
		String reworkNumber = newRework.getReworkNumber();
		String resource = newRework.getResource();
		String wlkiLink = newRework.getWikiLink();
		String description = newRework.getDescription();
		String addwho = newRework.getAddWho();
		
		String status = newRework.getStatus();
		String project = newRework.getProject().replace(",", "");
		
		String sqlInsertRework = "INSERT INTO REWORK (WMS,REWORKNUMBER,RESOURCE,WIKILINK,DESCRIPTION,ADDWHO,EDITWHO) "
			    				+ "VALUES(?,?,?,?,?,?,?) ";
		
		jdbcTemplate.update(
				sqlInsertRework,
				wms, reworkNumber,resource, wlkiLink, description, addwho, addwho
		);
		String sqlInsertReworkDetail = "INSERT INTO REWORKDETAIL (WMS,REWORKNUMBER,PROJECT,STATUS,ADDWHO, EDITWHO) "
									 + "VALUES(?,?,?,?,?,?) ";
		jdbcTemplate.update(
				sqlInsertReworkDetail,
				wms, reworkNumber, project, status, addwho, addwho
		);
		
	}

	@Override
	public List<Tuple2<Rework, List<ReworkDetail>>> findOnSearchParam(String search) {
		
		ResultSetExtractor<List<Tuple2<Rework, List<ReworkDetail>>>> resultSetExtractor = 
		        JdbcTemplateMapperFactory
		            .newInstance()
		            .addKeys("REWORKNUMBER") // the column name you expect the user id to be on
		            .newResultSetExtractor(new TypeReference<Tuple2<Rework, List<ReworkDetail>>>(){});

		String sqlSelectFilter = 
				"SELECT "
				+ " r.REWORKNUMBER, "
				+ " r.DESCRIPTION, "
				+ " r.TASK, "
				+ " r.TASKMONETKA, "
				+ " rd.REWORKNUMBER AS rd_REWORKNUMBER, "
				+ " rd.SERVER, "
				+ " rd.STATUS, "
				+ " rd.ADDDATE, "
				+ " rd.ADDWHO, "
				+ " rd.EDITWHO, "
				+ " rd.EDITDATE "
			+ " FROM REWORK AS r "
				+ " JOIN REWORKDETAIL AS rd "
					+ " ON r.REWORKNUMBER = rd.REWORKNUMBER "
			+ " WHERE r.ISDELETED = 0 ";
		
		Object[] paramsSqlSelectFilter = null;
		
		if(search == null) { 
			sqlSelectFilter += " ORDER BY r.REWORKNUMBER DESC ";
	
		} else {
				sqlSelectFilter += " AND (r.DESCRIPTION LIKE ? OR r.TASK LIKE ? OR r.TASKMONETKA LIKE ?) ";
					sqlSelectFilter += " ORDER BY r.REWORKNUMBER DESC ";
			paramsSqlSelectFilter = new Object[] { "%"+search+"%", "%"+search+"%", "%"+search+"%" };
		}
		
		List<Tuple2<Rework, List<ReworkDetail>>> result = jdbcTemplate.query(sqlSelectFilter, paramsSqlSelectFilter, resultSetExtractor);
		
		//List<Tuple2<Rework, List<ReworkDetail>>> afterAddingMisProject = addMissingProjects(result);
		return result;
	}

	@Override
	public List<String> findAllWhoUpdate() {
		String query = "SELECT DISTINCT EDITWHO FROM REWORKDETAIL";
		List<String> data = jdbcTemplate.queryForList(query, String.class);
		return data;
	}

}


