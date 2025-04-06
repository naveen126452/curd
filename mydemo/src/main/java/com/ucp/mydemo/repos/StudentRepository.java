package com.ucp.mydemo.repos;

import org.springframework.data.repository.CrudRepository;

//import org.springframework.stereotype.Repository;

import com.ucp.mydemo.models.Student;

public interface StudentRepository extends CrudRepository<Student, Integer>{
	
} 

