package main.dao.impl_dao;

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
public class ReworkImpl implements ReworkDao {
	
	private JdbcTemplate jdbcTemplate;  
	private final static String MAIN_FILTER_QUERY = 
			"SELECT "
					+ "	r.SERIALKEY, "
					+ " r.WMS, "
					+ "	r.REWORKNUMBER, "
					+ " r.RESOURCE, " 
					+ "	r.WMS AS rd_WMS, "
			        + "	r.REWORKNUMBER AS rd_REWORKNUMBER, "
			        + "	ISNULL(r.WIKILINK,'') AS WIKILINK, "
			        + "	ISNULL(r.DESCRIPTION,'') AS DESCRIPTION, "					
					+ "	PROJECT AS rd_PROJECT, "
					+ "	ISNULL(rd.STATUS,'') AS rd_STATUS "
				+ " FROM REWORK AS r"
					+ " JOIN REWORKDETAIL AS rd "
					+ "		ON rd.WMS = r.WMS AND rd.REWORKNUMBER = r.REWORKNUMBER ";
	
	private final static String WHERE = " WHERE "; 
	private final static String AND = " AND "; 
	private final static String OR = " OR "; 
	
	public ReworkImpl(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	private boolean isContainsProject(List<ReworkDetail> rdList, String project) {
		for(ReworkDetail fr : rdList) {
			if(fr.getProject().equals(project)) {
				//System.out.println(fr.getProject() +" == "+project);
				return true;
			}
		}
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
		String sqlUpdate = "UPDATE dbo.REWORK set DESCRIPTION = ?, RESOURCE = ?, WIKILINK = ?,EDITDATE = GETUTCDATE() WHERE SERIALKEY = ?; ";
		String descr = rework.getDescription();
		String wikilink = rework.getWikiLink();
		String resource = rework.getResource();
		
		jdbcTemplate.update(
				sqlUpdate, 
				resource, descr, wikilink, serialkey);
		
	}

	@Override
	@Transactional()
	public void deleteRework(String wms, String reworkNumber) {
		String sqlDelete = "DELETE FROM dbo.REWORKDETAIL WHERE WMS = ? AND REWORKNUMBER = ?; ";
		jdbcTemplate.update(sqlDelete, wms, reworkNumber);
			sqlDelete = "DELETE FROM dbo.REWORK WHERE WMS = ? AND REWORKNUMBER = ?; ";
				jdbcTemplate.update(sqlDelete, wms, reworkNumber);
		
	}

	@Override
	public boolean isAlreadyExistsRework(String wms, String reworkNumber) {
		 String sqlisExists = "SELECT REWORKNUMBER FROM dbo.REWORK WHERE WMS = ? AND REWORKNUMBER = ? ";
		 List<Map<String, Object>> reworks = jdbcTemplate.queryForList(sqlisExists, wms,reworkNumber);
		return !reworks.isEmpty();
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
		
		String sqlInsertRework = "INSERT INTO dbo.REWORK (WMS,REWORKNUMBER,RESOURCE,WIKILINK,DESCRIPTION,ADDWHO,EDITWHO) "
			    				+ "VALUES(?,?,?,?,?,?,?) ";
		
		jdbcTemplate.update(
				sqlInsertRework,
				wms, reworkNumber,resource, wlkiLink, description, addwho, addwho
		);
		String sqlInsertReworkDetail = "INSERT INTO dbo.REWORKDETAIL (WMS,REWORKNUMBER,PROJECT,STATUS,ADDWHO, EDITWHO) "
									 + "VALUES(?,?,?,?,?,?) ";
		jdbcTemplate.update(
				sqlInsertReworkDetail,
				wms, reworkNumber, project, status, addwho, addwho
		);
		
	}

	@Override
	public List<Tuple2<Rework, List<ReworkDetail>>> findOnWmsAndSearchParams(String wms, String search) {
		
		ResultSetExtractor<List<Tuple2<Rework, List<ReworkDetail>>>> resultSetExtractor = 
		        JdbcTemplateMapperFactory
		            .newInstance()
		            .addKeys("REWORKNUMBER","WMS") // the column name you expect the user id to be on
		            .newResultSetExtractor(new TypeReference<Tuple2<Rework, List<ReworkDetail>>>(){});

		String sqlSelectFilter = MAIN_FILTER_QUERY;
		Object[] paramsSqlSelectFilter = null;
		
		if(!wms.equals("Все")) { 
			sqlSelectFilter += WHERE+ " r.WMS = ? ";
				sqlSelectFilter += AND;
				sqlSelectFilter += "(r.REWORKNUMBER LIKE ? OR r.DESCRIPTION LIKE ?)";
			paramsSqlSelectFilter = new Object[] { wms ,"%"+search+"%", "%"+search+"%"};
		} else if (wms.equals("Все")) {
			sqlSelectFilter += WHERE;
				sqlSelectFilter += " r.REWORKNUMBER LIKE ? OR r.DESCRIPTION LIKE ? ";
			paramsSqlSelectFilter = new Object[] { "%"+search+"%", "%"+search+"%" };
		}
		
		List<Tuple2<Rework, List<ReworkDetail>>> result = jdbcTemplate.query(sqlSelectFilter, paramsSqlSelectFilter, resultSetExtractor);
		
		List<Tuple2<Rework, List<ReworkDetail>>> afterAddingMisProject = addMissingProjects(result);
		return afterAddingMisProject;
	}

	@Override
	public List<Tuple2<Rework, List<ReworkDetail>>> findOnWms(String wms) {
		
		ResultSetExtractor<List<Tuple2<Rework, List<ReworkDetail>>>> resultSetExtractor = 
		        JdbcTemplateMapperFactory
		            .newInstance()
		            .addKeys("REWORKNUMBER","WMS") // the column name you expect the user id to be on
		            .newResultSetExtractor(new TypeReference<Tuple2<Rework, List<ReworkDetail>>>(){});
		
		List<Tuple2<Rework, List<ReworkDetail>>> result = null;
		String sqlSelectFilter = MAIN_FILTER_QUERY;

		if(!wms.equals("Все")) {
			sqlSelectFilter += WHERE+ " r.WMS = ? ";
		}
		if(!wms.equals("Все")) result = jdbcTemplate.query(sqlSelectFilter, new Object[] { wms }, resultSetExtractor);
		else result = jdbcTemplate.query(MAIN_FILTER_QUERY, resultSetExtractor);		
		
		List<Tuple2<Rework, List<ReworkDetail>>> afterAddingMisProject = addMissingProjects(result);
		return afterAddingMisProject;
	}

	private List<Tuple2<Rework, List<ReworkDetail>>> addMissingProjects(
			List<Tuple2<Rework, List<ReworkDetail>>> result) {
		
		 String sqlALL_PROJECT = "SELECT DISTINCT PROJECT FROM dbo.REWORKDETAIL";
		 List<String> dataAllProject = jdbcTemplate.queryForList(sqlALL_PROJECT, String.class);
		
		
		Comparator<ReworkDetail> comparator = new Comparator<ReworkDetail>() {
			@Override
			public int compare(ReworkDetail o1, ReworkDetail o2) {
				return o1.getProject().compareTo(o2.getProject());
			}
		};

		for (Tuple2<Rework, List<ReworkDetail>> tuple : result) {
			List<ReworkDetail> rdList = tuple.v2;
			for(String project : dataAllProject) {
				
				boolean isContProj = isContainsProject(rdList,project);
				if(!isContProj) {
					rdList.add(new ReworkDetail(rdList.get(0).getWms(), rdList.get(0).getReworkNumber(), project, ""));
				}
			}
			Collections.sort(rdList, comparator);
		}
		return result;
	}

	@Override
	public List<Tuple2<Rework, List<ReworkDetail>>> findAll() {
		return null;//unused!
	}

	@Override
	public List<String> findAllWhoUpdate() {
		String query = "SELECT DISTINCT EDITWHO FROM REWORKDETAIL";
		List<String> data = jdbcTemplate.queryForList(query, String.class);
		return data;
	}

}


