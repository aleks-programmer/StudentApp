package com.java.developer.challenge.StudentApp.web;

import com.java.developer.challenge.StudentApp.domain.Student;
import com.java.developer.challenge.StudentApp.service.StudentSearchCriteria;
import com.java.developer.challenge.StudentApp.service.StudentService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/getAllStudents", produces = "application/json")
    @Transactional(readOnly = true)
    @ResponseBody
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(this.studentService.getAllStudents());
    }

    @GetMapping(value = "/searchStudents", produces = "application/json")
    @Transactional(readOnly = true)
    @ResponseBody
    public ResponseEntity<List<Student>> searchStudents(@RequestParam("value") String value) {
        StudentSearchCriteria criteria = new StudentSearchCriteria(value);

        return ResponseEntity.ok(this.studentService.findStudents(criteria));
    }

    @GetMapping(value = "/findStudentByID", produces = "application/json")
    @Transactional(readOnly = true)
    @ResponseBody
    public ResponseEntity<Student> findStudentByID(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.studentService.getStudent(id));
    }

    @PostMapping(value = "/importStudentsFromCSV", consumes = "multipart/form-data", produces = "application/json")
    @Transactional
    @ResponseBody
    public ResponseEntity importStudentsFromCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> importStudents = new ArrayList<>();
            CSVReader reader = new CSVReader(
                    new InputStreamReader(
                            new ByteArrayInputStream(file.getBytes())));
            String[] firstLine = reader.readNext();
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                Student student = new Student();
                student.setSchoolYear(Integer.valueOf(nextRecord[0]));
                student.setCampus(Integer.valueOf(nextRecord[1]));
                student.setId(Integer.valueOf(nextRecord[2]));

                DateTimeFormatter formatter =
                        new DateTimeFormatterBuilder().appendPattern("M/d/yyyy")
                                .toFormatter();
                Date date = Date.from(LocalDate.parse(nextRecord[3], formatter)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
                student.setEntryDate(date);
                student.setGradeLevel(Integer.valueOf(nextRecord[4]));
                student.setName(nextRecord[5]);
                importStudents.add(student);
            }

            return ResponseEntity.ok(studentService.saveStudents(importStudents));
        } catch (IOException e) {
            return ResponseEntity.unprocessableEntity().body("Cannot process file");
        }
    }

    @PostMapping(value = "/saveStudent", consumes = "application/json", produces = "application/json")
    @Transactional
    @ResponseBody
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return ResponseEntity.ok(this.studentService.saveStudent(student));
    }
}
