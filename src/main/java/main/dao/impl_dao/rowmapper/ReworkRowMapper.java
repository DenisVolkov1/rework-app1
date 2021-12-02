package main.dao.impl_dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;

import main.dao.model.Rework;
import main.util.LocalDateTimeRus;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.tree.TreePath;

public class ReworkRowMapper implements RowMapper<Rework> {

	@Override
	public Rework mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Rework resultRework = new Rework();
			resultRework.setSerialKey(rs.getLong("SERIALKEY"));
			resultRework.setWms(rs.getString("WMS"));
			resultRework.setReworkNumber(rs.getString("REWORKNUMBER"));
			resultRework.setWikiLink(rs.getString("WIKILINK"));
			resultRework.setResource(rs.getString("RESOURCE"));
			resultRework.setDescription(rs.getString("DESCRIPTION"));
			resultRework.setAddDate(new LocalDateTimeRus(rs.getTimestamp("ADDDATE").toLocalDateTime()));
			resultRework.setAddWho(rs.getString("ADDWHO"));
			resultRework.setEditWho(rs.getString("EDITWHO"));
			resultRework.setEditDate(new LocalDateTimeRus(rs.getTimestamp("EDITDATE").toLocalDateTime()));		
		return resultRework;
	}



}
