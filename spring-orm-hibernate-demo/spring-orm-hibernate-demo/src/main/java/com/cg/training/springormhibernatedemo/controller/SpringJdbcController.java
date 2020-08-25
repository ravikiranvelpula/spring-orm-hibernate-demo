package com.cg.training.springormhibernatedemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.training.springormhibernatedemo.dao.PersonRepository;
import com.cg.training.springormhibernatedemo.entity.Person;

@Controller
public class SpringJdbcController {

	@Autowired
	PersonRepository personRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="/persons")
	@ResponseBody
	public List<Person> getPerson(){
		return personRepository.getItems();
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/person")
	@ResponseBody
	public void updatePerson(@RequestParam(value = "address") String address, @RequestParam(value = "id") String id) {
		personRepository.updatePerson(Integer.valueOf(id), address);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/person/{id}")
	@ResponseBody
	public Person getPersonByID(@PathVariable(value = "id") String id) {
		return personRepository.getPersonById(Integer.valueOf(id));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/person/{id}")
	@ResponseBody
	public void deletePersonByID(@PathVariable(value = "id") String id) {
		 personRepository.deletePerson(Integer.valueOf(id));
	}
}
