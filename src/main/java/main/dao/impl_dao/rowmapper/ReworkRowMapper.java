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
			resultRework.setReworkNumber(rs.getInt("REWORKNUMBER"));
			resultRework.setDescription(rs.getString("DESCRIPTION"));
			resultRework.setTasks(rs.getString("TASK"));	
			resultRework.setTaskMonetka(rs.getString("TASKMONETKA"));
			resultRework.setReworkAddDate(rs.getTimestamp("ADDDATE"));
			resultRework.setAddWho(rs.getString("ADDWHO"));
			resultRework.setEditWho(rs.getString("EDITWHO"));
			resultRework.setReworkEditDate(rs.getTimestamp("EDITDATE"));		
		return resultRework;
	}



}
