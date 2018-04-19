package com.example.demo.dao.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "student_id")
	private Long student_id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "`full_name`")
	private String fullName;
	
	@Column(name = "`group`")
	private int group;
	
	@Column(name = "hobby")
	private String hobby;
	
	@Column(name = "token")
	private String token;
	
	//@OneToMany(mappedBy = "studentReff", fetch = FetchType.LAZY)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "attendance", joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "student_id")}, 
									inverseJoinColumns = {@JoinColumn(name = "laboratory_id", referencedColumnName = "idlaboratory_id")})
	private Set<Laboratory> studentAttendance = new HashSet<>();
	
	@OneToMany(mappedBy = "studentIdentifier", fetch = FetchType.EAGER)
	private Set<Grade> grades = new HashSet<>();
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "submission", joinColumns = {@JoinColumn(name = "submission_student_id", referencedColumnName = "student_id")}, 
									inverseJoinColumns = {@JoinColumn(name = "submission_assignment_id", referencedColumnName = "assignment_id")})
	private Set<Assignment> studentSubmissions = new HashSet<>();
	
	
	public Student() {
	
	}
	
	public Student(String email, int group, String hobby, String fullName, String password, String token) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.group = group;
		this.hobby = hobby;
		this.token = token;
	}
	

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}


	public Long getStudentId() {
		return student_id;
	}

	public void setStudentId(Long student_id) {
		this.student_id = student_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		
		this.token = token;
	}
	
	public Set<Laboratory> getStudentAttendance() {
		return studentAttendance;
	}

	public void setStudentAttendance(Set<Laboratory> studentAttendance) {
		this.studentAttendance = studentAttendance;
	}
	
	
	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}


	@Override
	public String toString() {
		return "Student{" +
				"email = " + email +
				", password = " + password +
				", name = " + fullName +
				", group = " + group +
				", hobby = " + hobby + 
				", token = " + token + '\'' +
				'}';
	}
}
