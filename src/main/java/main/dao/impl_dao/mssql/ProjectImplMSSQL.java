package main.dao.impl_dao.mssql;

import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import main.dao.interface_dao.ProjectDao;
import main.dao.model.Project;

@Component
public class ProjectImplMSSQL implements ProjectDao {
	
	private JdbcTemplate jdbcTemplate; 
	
	public ProjectImplMSSQL(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Project> findAll() {
		 final RowMapper<Project> rowMapper =
			        JdbcTemplateMapperFactory.newInstance().newRowMapper(Project.class);
			String query = "SELECT DISTINCT PROJECT FROM REWORKDETAIL";
			List<Project> res = jdbcTemplate.query(query, rowMapper);
		return res;
	}

}
