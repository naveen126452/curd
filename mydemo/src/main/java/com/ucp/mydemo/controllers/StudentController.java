package com.ucp.mydemo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ucp.mydemo.models.Student;
import com.ucp.mydemo.services.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired(required=true)
	private StudentService studentService;
	
	@GetMapping("/getAll")
	public String getAll(Model model) {
		List<Student> stlist = studentService.getAll();
		model.addAttribute("students", stlist);
		return "students";
	}
	
	@GetMapping("/addNew")
	public String newStudent(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "add-student";
    }
	
	@GetMapping("/edit/{id}")
	public String editStudent(@PathVariable("id") int id, Model model, HttpServletResponse response) 
			throws IOException {
		Student student = studentService.getStudent(id);
		//student.setUpdatedOn(student.getUpdatedOn());
		model.addAttribute("student", student);
		return "edit-student";
    }
	
	@PostMapping("/delete/{id}")
	public String deleteStudent(@PathVariable("id") int id) {
		studentService.delete(id);
        return "redirect:/students/getAll";
    }
	
	@PostMapping("/saveNew")
	public String insertStudent(
			@ModelAttribute(value="student") Student student,
			@RequestParam("photo") MultipartFile file) throws IOException {
		if(file != null) student.setContent(file.getBytes());
		studentService.insert(student);
        return "redirect:/students/getAll";
    }
	
	@PostMapping("/update/{id}")
	public String updateStudent(
			@PathVariable("id") int id, 
			@ModelAttribute(value="student") Student student,
			@RequestParam("photo") MultipartFile file) throws IOException
	{
		if(file != null) student.setContent(file.getBytes());
		studentService.update(id, student);
        return "redirect:/students/getAll";
    }
	
	  
	 @GetMapping("/getPhoto")
	 public void getStudentPhoto(HttpServletResponse response, @Param("id") int id) 
			 throws ServletException, IOException 
	 {
		 response.setContentType("jpeg, jpg, png");
		 Student student = studentService.getStudent(id);
		 response.getOutputStream().write(student.getContent());
		 response.getOutputStream().close();
	 }	
}

