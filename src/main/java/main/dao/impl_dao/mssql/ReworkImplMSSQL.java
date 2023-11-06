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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import main.dao.impl_dao.rowmapper.ReworkRowMapper;
import main.dao.interface_dao.ReworkDao;
import main.dao.model.Archive;
import main.dao.model.NewRework;
import main.dao.model.Project;
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
		String sqlUpdate = "UPDATE REWORK set DESCRIPTION = ?, FIELD1 = ?, FIELD2 = ?,EDITDATE = GETDATE() WHERE REWORKNUMBER = ? ";
		String descr = rework.getDescription();
		String field1 = rework.getField1();
		String field2 = rework.getField2();
		
		jdbcTemplate.update(
				sqlUpdate, 
				descr, field1,field2,reworknumber);	
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
	public void addNewRework(NewRework newRework, String project) {
		String description = newRework.getDescription();
		String field1 = newRework.getField1();
		String field2 = newRework.getField2();
		String addwho = newRework.getAddWho();
		
		String sqlSelectProjectName = "SELECT NAME FROM PROJECT WHERE PARTURL = ?";
		String projectName = jdbcTemplate.queryForObject(sqlSelectProjectName, new Object[] {project}, String.class);
		
		String sqlInsertRework = "INSERT INTO REWORK (PROJECT,DESCRIPTION,FIELD1,FIELD2,ADDWHO,EDITWHO) "
			    				+ "VALUES(?,?,?,?,?,?) ";
		
		jdbcTemplate.update(
				sqlInsertRework,
				projectName, description, field1, field2, addwho, addwho);
		
		String sqlSelectMaxReworkNumber = "SELECT MAX(REWORKNUMBER) FROM REWORK";
		Integer reworkNumber = jdbcTemplate.queryForObject(sqlSelectMaxReworkNumber, new Object[] {}, Integer.class);
		List<Server> servers = serverService.findAllByProject(projectName);

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
	public List<Tuple2<Rework, List<ReworkDetail>>> findOnSearchParam(String search, String project) {
		
		ResultSetExtractor<List<Tuple2<Rework, List<ReworkDetail>>>> resultSetExtractor = 
		        JdbcTemplateMapperFactory
		            .newInstance()
		            .addKeys("REWORKNUMBER") // the column name you expect the user id to be on
		            .newResultSetExtractor(new TypeReference<Tuple2<Rework, List<ReworkDetail>>>(){});

		String sqlSelectFilter = 
				"SELECT "
				+ " r.REWORKNUMBER, "
				+ " r.DESCRIPTION AS r_DESCRIPTION, "
				+ " r.FIELD1 AS r_FIELD1, "
				+ " r.FIELD2 AS r_FIELD2, "
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
		
		Object[] returnObj = mainFilterConditionReworkSearching(search,project);
		
		List<String> paramsSqlSelectFilter = (List<String>) returnObj[1];
		sqlSelectFilter                 += (String) returnObj[0];
				
		List<Tuple2<Rework, List<ReworkDetail>>> result = jdbcTemplate.query(sqlSelectFilter, paramsSqlSelectFilter.toArray(), resultSetExtractor);
		
		
		
		return result;
	}
	
	@Override
	public List<Archive> findOnSearchParamInArchive(String search) {
		
		 final RowMapper<Archive> rowMapper =
			        JdbcTemplateMapperFactory.newInstance().newRowMapper(Archive.class);

		String sqlSelectFilter = 
				"	SELECT DISTINCT "
				+ "	 r.REWORKNUMBER, "
				+ "	 r.DESCRIPTION AS DESCRIPTION, "
				+ "	 r.PROJECT AS PROJECT, "
				+ "	 r.FIELD1 AS FIELD1, "
				+ "	 r.FIELD2 AS FIELD2, "
				+ "	 r.ADDDATE AS REWORKADDDATE, "
				+ "	 r.EDITDATE AS REWORKEDITDATE "
				+ "				"
				+ "	FROM dbo.REWORK AS r "
				+ "	   JOIN dbo.PROJECT AS rd "
				+ "		   ON r.PROJECT = rd.NAME "
				+ "	WHERE r.ISDELETED = 0 ";
		
		Object[] returnObj = mainFilterConditionReworkSearching(search,"archive");
		
		List<String> paramsSqlSelectFilter = (List<String>) returnObj[1];
		sqlSelectFilter                 += (String) returnObj[0];
		
		List<Archive> result = jdbcTemplate.query(sqlSelectFilter, paramsSqlSelectFilter.toArray(), rowMapper);
		
		return result;
	}
	
	private Object[] mainFilterConditionReworkSearching(String search, String project) {
		
		Object[] returnObj = new Object[2];
		List<String> paramsSqlSelectFilter = new ArrayList<String>();
		String sqlSelectFilter = "";
		String isArchive = (project.equals("archive")) ? " OR r.PROJECT LIKE ?" : " ";
		
		if(!project.equals("archive")) {
			sqlSelectFilter += " AND r.PROJECT = (SELECT TOP 1 NAME FROM PROJECT WHERE PARTURL = ?) ";
			paramsSqlSelectFilter.add(project);
		}

		if(search != null) {
			
			if (Pattern.matches("(.+ (or|OR) .+)+",search)) {
				
				String arraySearch[] = search.split(" (or|OR) ");
				
				for (int i = 0; i < arraySearch.length; i++) {
					
					String predicate = (i == 0) ? "AND (" : "OR";
					sqlSelectFilter += predicate + " (r.DESCRIPTION LIKE ? OR r.FIELD1 LIKE ? OR r.FIELD2 LIKE ? "+isArchive+") ";
						for (int j = 1; j <= 3; j++) {
							paramsSqlSelectFilter.add("%"+arraySearch[i]+"%");
						}
						if(project.equals("archive")) paramsSqlSelectFilter.add("%"+arraySearch[i]+"%");
				}
				sqlSelectFilter += " ) ";
				
			} else {
					sqlSelectFilter += " AND (r.DESCRIPTION LIKE ? OR r.FIELD1 LIKE ? OR r.FIELD2 LIKE ? "+isArchive+") ";
						
				paramsSqlSelectFilter.add("%"+search+"%");
				paramsSqlSelectFilter.add("%"+search+"%");
				paramsSqlSelectFilter.add("%"+search+"%");
				if(project.equals("archive")) paramsSqlSelectFilter.add("%"+search+"%");
			}
		}
		sqlSelectFilter += " ORDER BY r.REWORKNUMBER DESC ";
		returnObj[0] = sqlSelectFilter;
		returnObj[1] = paramsSqlSelectFilter;
		
		return returnObj;
	}
	
	@Override
	public boolean isAlreadyExistsRework(String description, String project) {
		 String sqlisExists = "SELECT REWORKNUMBER FROM REWORK WHERE DESCRIPTION = ? AND PROJECT = (SELECT NAME FROM PROJECT WHERE PARTURL=?)";
		 List<Map<String, Object>> reworks = jdbcTemplate.queryForList(sqlisExists, description, project);
		return !reworks.isEmpty();
	}

	@Override
	public List<String> findAllWhoUpdate() {
		String query = "SELECT DISTINCT EDITWHO FROM REWORKDETAIL";
		List<String> data = jdbcTemplate.queryForList(query, String.class);
		return data;
	}

}


