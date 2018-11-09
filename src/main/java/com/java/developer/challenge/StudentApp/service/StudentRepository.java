package com.java.developer.challenge.StudentApp.service;

import com.java.developer.challenge.StudentApp.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    List<Student> findAll();
    List<Student> findStudentsByNameOrCampusOrEntryDateOrGradeLevelOrSchoolYear(String name, Integer campus,
                                                                                Date entryDate, Integer gradeLevel,
                                                                               Integer schoolYear);
}
