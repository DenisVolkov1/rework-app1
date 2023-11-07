package main.dao.impl_dao.mssql;

import java.util.List;


import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import main.dao.impl_dao.rowmapper.ReworkDetailRowMapper;
import main.dao.interface_dao.StatusDao;
import main.dao.model.ReworkDetail;
import main.dao.model.Status;
import main.dao.service.ReworkService;


 
@Component
public class StatusImplMSSQL implements StatusDao {
	
	@Autowired
	private ReworkService reworkService;
	
	private JdbcTemplate jdbcTemplate;  
	
	public StatusImplMSSQL(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Status> findAll() {
		 final RowMapper<Status> rowMapper =
		        JdbcTemplateMapperFactory.newInstance().newRowMapper(Status.class);
		String query = "SELECT DISTINCT STATUS FROM REWORKDETAIL ORDER BY 1;";
		List<Status> res = jdbcTemplate.query(query, rowMapper);
		return res;
	}
	
	@Transactional()
	@Override
	public String updateStatus(String reworkNumber, String server, String status, String whoUpdate) {
		
			String sqlUpdateStatus = "UPDATE REWORKDETAIL "
									 + "set STATUS = ?,"
										+ " EDITDATE = GETDATE(),"
										+ " EDITWHO = ? "
									+ "WHERE REWORKNUMBER = ? AND SERVER = ? ";
			jdbcTemplate.update(
					sqlUpdateStatus, 
					status, whoUpdate, reworkNumber, server);
			//CHECK ALL SEVERS IS DONE
	
			String checkSelectCountRows =
			"SELECT COUNT(*) "+
			"FROM REWORK AS r "+
				"JOIN REWORKDETAIL AS rd "+
					"ON r.REWORKNUMBER = rd.REWORKNUMBER "+
			"WHERE r.REWORKNUMBER = ? AND rd.STATUS != 'OK' ";
			Integer checksCountRows = jdbcTemplate.queryForObject(checkSelectCountRows, new Object[] {reworkNumber}, Integer.class);
			
			if(checksCountRows == 0) { 
				reworkService.hideRework(reworkNumber);
				return "All servers is done!";
			} else { return "" ; }
	}

	@Override
	public ReworkDetail getDateTooltip(String wms, String reworkNumber, String project) {
		String sqlSelectCountRows = "SELECT COUNT(*) FROM REWORKDETAIL WHERE WMS = ? AND REWORKNUMBER = ? AND PROJECT = ?; ";
		Integer countRows = jdbcTemplate.queryForObject(sqlSelectCountRows, new Object[] {wms,reworkNumber,project}, Integer.class);
		if (countRows > 0) {
			String sql = "SELECT  " 
							+"SERIALKEY, "
							+"WMS, "
							+"REWORKNUMBER, "
							+"PROJECT, "
							+"[STATUS], "
							+"dbo.getLocalDateTime(ADDDATE) AS ADDDATE, "
							+"ADDWHO, "
							+"EDITWHO, "
							+"dbo.getLocalDateTime(EDITDATE) AS EDITDATE "
					+ "FROM REWORKDETAIL WHERE WMS = ? AND REWORKNUMBER = ? AND PROJECT = ?; ";
	        return (ReworkDetail) jdbcTemplate.queryForObject(
				sql, 
				new Object[]{wms, reworkNumber, project}, 
				new ReworkDetailRowMapper());
		}
		return new ReworkDetail();
	}

	@Override
	public String updateDate(String reworkNumber, String server, String date) {
		
		String sqlUpdateDate = 
				"UPDATE REWORKDETAIL "
				 + "set EDITDATE = CONVERT(datetime, ?, 104) "
				+ "WHERE REWORKNUMBER = ? AND SERVER = ?; ";
		
		jdbcTemplate.update(
		sqlUpdateDate, 
		date, reworkNumber, server);
		
		   String[] partsDate = date.split("\\.");
		   String dateOnScreen = partsDate[0] + "." + partsDate[1] + "." + partsDate[2].substring(2, 4);
		return dateOnScreen;
	}


}
