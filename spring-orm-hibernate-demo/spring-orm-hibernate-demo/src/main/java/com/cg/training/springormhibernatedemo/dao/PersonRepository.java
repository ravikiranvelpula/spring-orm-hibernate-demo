package com.cg.training.springormhibernatedemo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cg.training.springormhibernatedemo.entity.Person;

@Repository
public class PersonRepository {
	

	@Resource(name = "personJdbcTemplate")
	JdbcTemplate personJdbcTemplate;
	
	class PersonRowMapper implements RowMapper<Person>{

		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person person = new Person();
			person.setId(rs.getInt("PERSON_ID"));
			person.setFirstName(rs.getString("FIRSTNAME"));
			person.setLastName(rs.getString("LASTNAME"));
			person.setAddress(rs.getString("ADDRESS"));
			person.setCity(rs.getString("CITY"));
			return person;
		}
		
	}
	
	public List<Person> getItems(){
		return personJdbcTemplate.query("select * from PERSON", new PersonRowMapper());
		
	}
	
	public Person getPersonById(int id){
		return personJdbcTemplate.queryForObject("select * from PERSON where PERSON_ID = ?", new Object[] {id}, new PersonRowMapper());
	}
	
	public void updatePerson(int id, String address) {
		 personJdbcTemplate.update("update PERSON set ADDRESS = ? where PERSON_ID = ?", new Object[] {address, id });
	}
	
	public void deletePerson(int id){
	    personJdbcTemplate.update("delete PERSON where PERSON_ID = ?", new Object[] {id});
	}

}
