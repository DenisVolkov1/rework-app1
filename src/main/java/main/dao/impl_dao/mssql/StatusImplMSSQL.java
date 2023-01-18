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

@Component
public class StatusImplMSSQL implements StatusDao {
	
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
	public void updateStatus(String wms, String reworkNumber, String project, String status, String whoUpdate) {
		String sqlSelectCountRows = "SELECT COUNT(*) FROM REWORKDETAIL WHERE WMS = ? AND REWORKNUMBER = ? AND PROJECT = ?; ";
		Integer countRows = jdbcTemplate.queryForObject(sqlSelectCountRows, new Object[] {wms,reworkNumber,project}, Integer.class);
		
		if(countRows == 1) {
			if(!status.equals("")) {
				String sqlUpdateStatus = "UPDATE REWORKDETAIL "
										 + "set STATUS = ?,"
											+ " EDITDATE = GETUTCDATE(),"
											+ " EDITWHO = ? "
										+ "WHERE WMS = ? AND REWORKNUMBER = ? AND PROJECT = ?; ";
				jdbcTemplate.update(
						sqlUpdateStatus, 
						status, whoUpdate, wms, reworkNumber, project);
			} else {
				String sqlDelete = "DELETE FROM REWORKDETAIL WHERE WMS = ? AND REWORKNUMBER = ? AND PROJECT = ?; ";
				jdbcTemplate.update(sqlDelete, wms, reworkNumber, project);
			}
		} else if (countRows == 0) {
			if(!status.equals("")) {
				String sqlInsertReworkDetail = "INSERT INTO REWORKDETAIL (WMS,REWORKNUMBER,PROJECT,STATUS,ADDWHO,EDITWHO) "
						 + "VALUES(?,?,?,?,?,?) ";
				jdbcTemplate.update(
						sqlInsertReworkDetail,
						wms, reworkNumber, project, status, whoUpdate, whoUpdate);
			}
		}
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


}
