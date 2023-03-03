package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
  private final StudentRepository studentRepository;

  @Autowired
  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getStudents() {
    return studentRepository.findAll();
  }

  public void addNewStudent(Student student) {
    var studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
    if (studentByEmail.isPresent()) {
      throw new IllegalStateException("email taken");
    }
    studentRepository.save(student);
  }

  public void deleteStudent(Long studentId) {
    boolean exists = studentRepository.existsById(studentId);
    if (!exists) {
      throw new IllegalStateException("student with id " + studentId + " does not exist");
    }
    studentRepository.deleteById(studentId);
  }

  @Transactional
  public void updateStudent(Long studentId, String name, String email) {
    Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException());

    if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
      student.setName(name);
    }
  }
}
