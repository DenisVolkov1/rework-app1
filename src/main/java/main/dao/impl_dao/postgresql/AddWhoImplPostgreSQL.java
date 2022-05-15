package main.dao.impl_dao.postgresql;

import java.util.List;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import main.dao.interface_dao.AddWhoDao;
import main.dao.model.AddWho;


@Component
@Primary /// Current Base PostgreSQL ///
public class AddWhoImplPostgreSQL implements AddWhoDao{

	private JdbcTemplate jdbcTemplate;
	
	public AddWhoImplPostgreSQL(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<AddWho> findAll() {
		 final RowMapper<AddWho> rowMapper =
			        JdbcTemplateMapperFactory.newInstance().newRowMapper(AddWho.class);
			String query = "SELECT DISTINCT ADDWHO FROM REWORK ORDER BY 1";
			List<AddWho> res = jdbcTemplate.query(query, rowMapper);
			return res;
	}

}
