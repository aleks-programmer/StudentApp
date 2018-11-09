package com.java.developer.challenge.StudentApp.service;

import com.java.developer.challenge.StudentApp.domain.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudent(Integer id);
    List<Student> findStudents(StudentSearchCriteria criteria);
    Iterable<Student> saveStudents(List<Student> students);
    Student saveStudent(Student student);
}
