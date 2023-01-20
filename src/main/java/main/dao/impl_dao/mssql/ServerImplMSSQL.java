package main.dao.impl_dao.mssql;

import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import main.dao.interface_dao.ServerDao;
import main.dao.model.Server;

@Component
public class ServerImplMSSQL implements ServerDao {
	
	private JdbcTemplate jdbcTemplate; 
	
	public ServerImplMSSQL(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Server> findAll() {
		 final RowMapper<Server> rowMapper =
			        JdbcTemplateMapperFactory.newInstance().newRowMapper(Server.class);
			String query = "SELECT DISTINCT SERVER FROM REWORKDETAIL";
			List<Server> res = jdbcTemplate.query(query, rowMapper);
		return res;
	}

}
