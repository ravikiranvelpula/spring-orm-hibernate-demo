package com.cg.training.springormhibernatedemo.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.training.springormhibernatedemo.jpa.entity.PersonEntity;
import com.cg.training.springormhibernatedemo.jpa.repository.PersonJPARepository;

@Controller
public class SpringJPAController {

	@Autowired
	PersonJPARepository personRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/person/jpa/{id}")
	@ResponseBody
	public PersonEntity getPerson(@PathVariable(value = "id") String id) {
		return personRepository.get(Integer.valueOf(id));
	}
}
