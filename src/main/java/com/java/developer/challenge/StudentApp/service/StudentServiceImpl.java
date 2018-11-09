package com.java.developer.challenge.StudentApp.service;

import com.java.developer.challenge.StudentApp.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service("cityService")
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student getStudent(Integer id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> findStudents(StudentSearchCriteria criteria) {
        String value;

        if (criteria == null || ((value = criteria.getValue())) == null || value.isEmpty()) {
            return this.studentRepository.findAll();
        }

        try {
            Integer integer = Integer.valueOf(value);
            return this.studentRepository.findStudentsByNameOrCampusOrEntryDateOrGradeLevelOrSchoolYear(null,
                    integer, null, integer, integer);
        } catch (NumberFormatException nfe) {
        }

        try {
            Date date = Date.valueOf(value);
            return this.studentRepository.findStudentsByNameOrCampusOrEntryDateOrGradeLevelOrSchoolYear(null,
                    null, date, null, null);
        } catch (IllegalArgumentException iae) {
        }

        return this.studentRepository.findStudentsByNameOrCampusOrEntryDateOrGradeLevelOrSchoolYear(value,
                null, null, null, null);
    }

    @Override
    public Iterable<Student> saveStudents(List<Student> students) {
        return this.studentRepository.saveAll(students);
    }

    @Override
    public Student saveStudent(Student student) {
        return this.studentRepository.save(student);
    }
}
