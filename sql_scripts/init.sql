CREATE DATABASE IF NOT EXISTS student;
USE student;
create table student
(
  school_year int not null,
  campus int not null,
  student_id int auto_increment
    primary key,
  entry_date datetime not null,
  grade_level int not null,
  name varchar(200) not null
);