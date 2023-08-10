package main.dao.impl_dao.mssql;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.simpleflatmapper.util.TypeReference;
import org.jooq.lambda.tuple.Tuple2;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import main.dao.impl_dao.rowmapper.ReworkRowMapper;
import main.dao.interface_dao.ReworkDao;
import main.dao.model.NewRework;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;
import main.dao.model.Server;
import main.dao.service.ServerService;

@Component
public class ReworkImplMSSQL implements ReworkDao {
	
	private JdbcTemplate jdbcTemplate;  
	
	
	public ReworkImplMSSQL(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Rework getRework(Integer reworkNumber) {
		
        String sql = "SELECT * FROM REWORK WHERE REWORKNUMBER = ?";

        return (Rework) jdbcTemplate.queryForObject(
			sql, 
			new Object[]{reworkNumber}, 
			new ReworkRowMapper());
	}

	@Override
	public void updateRework(int reworknumber, Rework rework) {
		String sqlUpdate = "UPDATE REWORK set DESCRIPTION = ?, TASK = ?, TASKMONETKA = ?,EDITDATE = GETDATE() WHERE REWORKNUMBER = ? ";
		String descr = rework.getDescription();
		String task = rework.getTasks();
		String taskMonetka = rework.getTaskMonetka();
		
		jdbcTemplate.update(
				sqlUpdate, 
				descr, task,taskMonetka,reworknumber);	
	}

	@Override
	public void hideRework(String reworkNumber) {
		String sqlDelete = "UPDATE REWORK SET ISDELETED = 1 WHERE REWORKNUMBER = ?; ";
		jdbcTemplate.update(sqlDelete, reworkNumber);
	}

	@Autowired
	ServerService serverService;
	@Transactional()
	@Override
	public void addNewRework(NewRework newRework) {
		String description = newRework.getDescription();
		String task = newRework.getTask();
		String taskMonetka = newRework.getTaskMonetka();
		String addwho = newRework.getAddWho();
		
		String sqlInsertRework = "INSERT INTO REWORK (DESCRIPTION,TASK,TASKMONETKA,ADDWHO,EDITWHO) "
			    				+ "VALUES(?,?,?,?,?) ";
		
		jdbcTemplate.update(
				sqlInsertRework,
				description, task, taskMonetka, addwho, addwho);
		
		String sqlSelectMaxReworkNumber = "SELECT MAX(REWORKNUMBER) FROM REWORK";
		Integer reworkNumber = jdbcTemplate.queryForObject(sqlSelectMaxReworkNumber, new Object[] {}, Integer.class);
		List<Server> servers = serverService.findAll();

		String sqlInsertReworkDetail = "INSERT INTO REWORKDETAIL (REWORKNUMBER,SERVER,ADDWHO,EDITWHO) "		
									 + "VALUES (?,?,?,?)";
		
		List<Object[]> argsList = new ArrayList<>();
		
		for (int i = 0; i < servers.size(); i++) {
			Object[] args = new Object[4];
			args[0] = (int)reworkNumber;
			args[1] = servers.get(i).getServer();
			args[2] = addwho;
			args[3] = addwho;
			argsList.add(args);
		}

		int[] types = new int[4];
			types[0]=Types.INTEGER;
			types[1]=Types.NVARCHAR;
			types[2]=Types.NVARCHAR;
			types[3]=Types.NVARCHAR;
		
		jdbcTemplate.batchUpdate(
				sqlInsertReworkDetail,argsList, types);
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
				+ " r.DESCRIPTION AS r_DESCRIPTION, "
				+ " r.TASK AS r_TASKS, "
				+ " r.TASKMONETKA AS r_TASKMONETKA, "
				+ " r.ADDDATE AS REWORKADDDATE, "
				+ " r.EDITDATE AS REWORKEDITDATE, "
				
				+ " r.REWORKNUMBER AS rd_REWORKNUMBER, "
				+ " rd.SERVER AS rd_SERVER, "
				+ " ISNULL(rd.STATUS,'') AS rd_STATUS, "
				+ " rd.ADDDATE AS rd_ADDDATE, "
				+ " rd.ADDWHO AS rd_ADDWHO, "
				+ " rd.EDITWHO AS rd_EDITWHO, "
				+ " rd.EDITDATE AS rd_EDITDATE"
			+ " FROM REWORK AS r "
				+ " JOIN REWORKDETAIL AS rd "
					+ " ON r.REWORKNUMBER = rd.REWORKNUMBER "
			+ " WHERE r.ISDELETED = 0 ";
		
		Object[] paramsSqlSelectFilter = null;
		
		if(search == null) { 
			sqlSelectFilter += " ORDER BY r.REWORKNUMBER DESC ";
	
		} else if (Pattern.matches("(.+ (or|OR) .+)+",search)) {
			
			String arraySearch[] = search.split(" (or|OR) ");
			paramsSqlSelectFilter = new Object[arraySearch.length * 3];
			
			for (int i = 0, i2 = 0; i < arraySearch.length; i++) {
				
				String predicate = (i == 0) ? "AND" : "OR";
				sqlSelectFilter += predicate + " (r.DESCRIPTION LIKE ? OR r.TASK LIKE ? OR r.TASKMONETKA LIKE ?) ";
					for (int j = 1; j <= 3; j++) {
						paramsSqlSelectFilter[i2]= "%"+arraySearch[i]+"%";
						i2++;
					}
			}
			sqlSelectFilter += " ORDER BY r.REWORKNUMBER DESC ";
			
		} else {
				sqlSelectFilter += " AND (r.DESCRIPTION LIKE ? OR r.TASK LIKE ? OR r.TASKMONETKA LIKE ?) ";
					
			paramsSqlSelectFilter = new Object[] { "%"+search+"%", "%"+search+"%", "%"+search+"%" };
		}
		
		List<Tuple2<Rework, List<ReworkDetail>>> result = jdbcTemplate.query(sqlSelectFilter, paramsSqlSelectFilter, resultSetExtractor);
		
		return result;
	}
	
	@Override
	public List<Tuple2<Rework, List<ReworkDetail>>> findOnSearchParamInArchive(String search) {
		
		ResultSetExtractor<List<Tuple2<Rework, List<ReworkDetail>>>> resultSetExtractor = 
		        JdbcTemplateMapperFactory
		            .newInstance()
		            .addKeys("REWORKNUMBER") // the column name you expect the user id to be on
		            .newResultSetExtractor(new TypeReference<Tuple2<Rework, List<ReworkDetail>>>(){});

		String sqlSelectFilter = 
				"SELECT "
				+ " r.REWORKNUMBER, "
				+ " r.DESCRIPTION AS r_DESCRIPTION, "
				+ " r.TASK AS r_TASKS, "
				+ " r.TASKMONETKA AS r_TASKMONETKA, "
				+ " r.ADDDATE AS REWORKADDDATE, "
				+ " r.EDITDATE AS REWORKEDITDATE, "
				
				+ " r.REWORKNUMBER AS rd_REWORKNUMBER, "
				+ " rd.SERVER AS rd_SERVER, "
				+ " ISNULL(rd.STATUS,'') AS rd_STATUS, "
				+ " rd.ADDDATE AS rd_ADDDATE, "
				+ " rd.ADDWHO AS rd_ADDWHO, "
				+ " rd.EDITWHO AS rd_EDITWHO, "
				+ " rd.EDITDATE AS rd_EDITDATE"
			+ " FROM REWORK AS r "
				+ " JOIN REWORKDETAIL AS rd "
					+ " ON r.REWORKNUMBER = rd.REWORKNUMBER "
			+ " WHERE r.ISDELETED = 1 ";
		
		Object[] paramsSqlSelectFilter = null;
		
		if(search == null) { 
			sqlSelectFilter += " ORDER BY r.REWORKNUMBER DESC ";
	
		} else {
				sqlSelectFilter += " AND (r.DESCRIPTION LIKE ? OR r.TASK LIKE ? OR r.TASKMONETKA LIKE ?) ";
					sqlSelectFilter += " ORDER BY r.REWORKNUMBER DESC ";
			paramsSqlSelectFilter = new Object[] { "%"+search+"%", "%"+search+"%", "%"+search+"%" };
		}
		
		List<Tuple2<Rework, List<ReworkDetail>>> result = jdbcTemplate.query(sqlSelectFilter, paramsSqlSelectFilter, resultSetExtractor);
		
		return result;
	}
	
	@Override
	public boolean isAlreadyExistsRework(String description) {
		 String sqlisExists = "SELECT REWORKNUMBER FROM REWORK WHERE DESCRIPTION = ? ";
		 List<Map<String, Object>> reworks = jdbcTemplate.queryForList(sqlisExists, description );
		return !reworks.isEmpty();
	}

	@Override
	public List<String> findAllWhoUpdate() {
		String query = "SELECT DISTINCT EDITWHO FROM REWORKDETAIL";
		List<String> data = jdbcTemplate.queryForList(query, String.class);
		return data;
	}

}


