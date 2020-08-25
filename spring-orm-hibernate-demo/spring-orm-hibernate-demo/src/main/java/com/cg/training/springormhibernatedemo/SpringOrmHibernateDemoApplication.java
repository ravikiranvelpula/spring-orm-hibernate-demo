package com.cg.training.springormhibernatedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.training.springormhibernatedemo.dao.PersonRepository;

@SpringBootApplication
public class SpringOrmHibernateDemoApplication implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(SpringOrmHibernateDemoApplication.class);
	
	@Autowired
	PersonRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringOrmHibernateDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("All Persons -> {}",repository.getItems());
	}

	
}
