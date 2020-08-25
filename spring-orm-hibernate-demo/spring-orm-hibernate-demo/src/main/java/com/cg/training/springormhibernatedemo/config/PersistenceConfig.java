package com.cg.training.springormhibernatedemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PersistenceConfig {

	@Autowired
	DataSource dataSource;
	
	@Bean
	public JdbcTemplate personJdbcTemplate() {
		JdbcTemplate personJdbcTemplate = new JdbcTemplate(dataSource);
		return personJdbcTemplate;
	}
	
}
