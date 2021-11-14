package main.dao.impl_dao;

import java.util.List;



import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import main.dao.interface_dao.WmsDao;
import main.dao.model.Wms;


@Component
public class WmsImpl implements WmsDao{

private JdbcTemplate jdbcTemplate;  
	
	public WmsImpl(@Autowired JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Wms> findAll() {
		 final RowMapper<Wms> rowMapper =
		        JdbcTemplateMapperFactory.newInstance().newRowMapper(Wms.class);
		String query = "SELECT DISTINCT WMS FROM REWORK";
		List<Wms> res = jdbcTemplate.query(query, rowMapper);
		return res;
	}

}
