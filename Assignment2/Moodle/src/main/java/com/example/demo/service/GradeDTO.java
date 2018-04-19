package com.example.demo.service;

public class GradeDTO {
	
	private Long id;
	private int timesSubmitted;
	private int grade;
	private StudentDTO student;
	private AssignmentDTO assignment;
	
	public GradeDTO() {
		
	}
	
	public GradeDTO(int timesSubmitted, int grade) {
		this.timesSubmitted = timesSubmitted;
		this.grade = grade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTimesSumbitted() {
		return timesSubmitted;
	}

	public void setTimesSumbitted(int timesSumbitted) {
		this.timesSubmitted = timesSumbitted;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		if(grade < 0) this.setGrade(1);
		if(grade > 10) this.setGrade(10);
		else
			this.grade = grade;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public AssignmentDTO getAssignment() {
		return assignment;
	}

	public void setAssignment(AssignmentDTO assignment) {
		this.assignment = assignment;
	}
	
	@Override 
	public String toString() {
		return "Grade{" + 
				"id = " + id +
				", student_id = " +  student.getId() +
				", assignment_id = "  + assignment.getId() + 
				", timesSubmitted = " + timesSubmitted +
				", grade = " + grade + '\'' +
				'}' + "\n";
	}
}
