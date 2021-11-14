package main.dao.impl_dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import main.dao.model.ReworkDetail;
import main.util.LocalDateTimeRus;

public class ReworkDetailRowMapper implements RowMapper<ReworkDetail> {

	@Override
	public ReworkDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReworkDetail resultRework = new ReworkDetail();
		resultRework.setSerialKey(rs.getLong("SERIALKEY"));
		resultRework.setWms(rs.getString("WMS"));
		resultRework.setReworkNumber(rs.getString("REWORKNUMBER"));
		resultRework.setProject(rs.getString("PROJECT"));
		resultRework.setStatus(rs.getString("STATUS"));
		resultRework.setAddDate(new LocalDateTimeRus(rs.getTimestamp("ADDDATE").toLocalDateTime()));
		resultRework.setAddWho(rs.getString("ADDWHO"));
		resultRework.setEditWho(rs.getString("EDITWHO"));
		resultRework.setEditDate(new LocalDateTimeRus(rs.getTimestamp("EDITDATE").toLocalDateTime()));	
	return resultRework;
	}

}
