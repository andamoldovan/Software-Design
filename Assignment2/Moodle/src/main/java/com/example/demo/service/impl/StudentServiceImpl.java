package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDAO;
import com.example.demo.dao.model.Student;
import com.example.demo.service.StudentDTO;
import com.example.demo.service.StudentService;


@Service
public class StudentServiceImpl implements StudentService{
	private final StudentDAO studentDAO;

	
    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
    
    
    public List<StudentDTO> getAllStudents() {
        return transform(studentDAO.findAll());
    }
    
    public StudentDTO getStudentById(Long id) {
    	Student s = studentDAO.getOne(id);
    	StudentDTO student = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
    	student.setId(id);
    	return student;
    }

    public StudentDTO getStudentByEmail(String email) {
    	try {
    		Student s = studentDAO.findByEmail(email);
    		StudentDTO student = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
    		student.setId(s.getStudentId());
    		return student;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    @Override
	public StudentDTO getStudentByEmailAndPassword(String email, String password) {
		try {
			Student s = studentDAO.findByEmailAndPassword(email, password);
			StudentDTO student = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
			student.setId(s.getStudentId());
			return student;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Student saveStudent(StudentDTO studentDTO) {
		Student toBeSaved = new Student();
		//toBeSaved.setStudentId(studentDTO.getStudentId());
		toBeSaved.setEmail(studentDTO.getEmail());
		toBeSaved.setPassword(studentDTO.getPassword());
		toBeSaved.setGroup(studentDTO.getGroup());
		toBeSaved.setFullName(studentDTO.getFullName());
		toBeSaved.setHobby(studentDTO.getHobby());
		toBeSaved.setToken(studentDTO.getToken());
		
		try {
			if(studentDAO.findByEmail(toBeSaved.getEmail()) == null) {
				studentDAO.saveAndFlush(toBeSaved);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return toBeSaved;
	}
		

	@Override
	public StudentDTO updateStudent(String email, StudentDTO studentDTO) {
		Student student = studentDAO.findByEmail(email);
		student.setFullName(studentDTO.getEmail());
		student.setGroup(studentDTO.getGroup());
		student.setHobby(studentDTO.getHobby());
		student.setPassword(studentDTO.getPassword());
		
		try {
			return transform(studentDAO.save(student));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteStudent(Long id) {
		try {
			studentDAO.deleteById(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    
	@Override
	public Student firstLogIn(String email, String token, StudentDTO studentDTO) {
		Student student = studentDAO.findByEmail(email);
		student.setFullName(studentDTO.getFullName());
		student.setGroup(studentDTO.getGroup());
		student.setHobby(studentDTO.getHobby());
		student.setPassword(studentDTO.getPassword());
		System.out.println("\n\n");
		System.out.print(student.toString());
		
		try {
			if(student.getToken().equals(token)) {
				return studentDAO.save(student);
			}
			else return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	private List<StudentDTO> transform(List<Student> list){
		List<StudentDTO> students = new ArrayList<StudentDTO>();
		StudentDTO studentDTO;
		for(Student s : list) {
			studentDTO = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
			studentDTO.setId(s.getStudentId());
			students.add(studentDTO);
		}
		return students;
	}
	
	private StudentDTO transform(Student student) {
		StudentDTO s = new StudentDTO(student.getEmail(), student.getGroup(), student.getHobby(), student.getFullName(), student.getPassword());
		s.setId(student.getStudentId());
		
		return s;
	}


	

}
