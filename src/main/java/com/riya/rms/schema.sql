DROP DATABASE IF EXISTS rmsdb;

CREATE DATABASE rmsdb;

USE rmsdb;

CREATE TABLE users
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50) UNIQUE                   NOT NULL,
    password   VARCHAR(255)                         NOT NULL,
    name       VARCHAR(100)                         NOT NULL,
    role       ENUM ('admin', 'teacher', 'student') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE courses
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    name           VARCHAR(100) NOT NULL, /* e.g., BIT, BCA, BE */
    code           VARCHAR(20) UNIQUE,
    semester_count INT          NOT NULL DEFAULT 8
);

CREATE TABLE semesters
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL, /* e.g., I, II, III */
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES courses (id)
);

CREATE TABLE subjects
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    name                VARCHAR(100) NOT NULL,
    code                VARCHAR(20),
    semester_id         INT,
    assigned_teacher_id INT,
    FOREIGN KEY (semester_id) REFERENCES semesters (id),
    FOREIGN KEY (assigned_teacher_id) REFERENCES users (id)
);

CREATE TABLE student_enrollments
(
    student_id          INT PRIMARY KEY,
    course_id           INT NOT NULL,
    current_semester_id INT NOT NULL,

    CONSTRAINT fk_enrollment_student
        FOREIGN KEY (student_id) REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_enrollment_course
        FOREIGN KEY (course_id) REFERENCES courses (id),

    CONSTRAINT fk_enrollment_semester
        FOREIGN KEY (current_semester_id) REFERENCES semesters (id)
);


CREATE TABLE exams
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(100) NOT NULL, /* Mid Term, Final Board */
    date         DATE,
    is_published BOOLEAN DEFAULT FALSE,
    course_id    INT,
    semester_id  INT,
    FOREIGN KEY (course_id) REFERENCES courses (id),
    FOREIGN KEY (semester_id) REFERENCES semesters (id)
);

CREATE TABLE marks
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    exam_id        INT,
    student_id     INT,
    subject_id     INT,
    marks_obtained DECIMAL(5, 2),
    total_marks    DECIMAL(5, 2) DEFAULT 100.00,
    FOREIGN KEY (exam_id) REFERENCES exams (id),
    FOREIGN KEY (student_id) REFERENCES users (id),
    FOREIGN KEY (subject_id) REFERENCES subjects (id),
    UNIQUE KEY unique_result (exam_id, student_id, subject_id)
);

/* Demo Admin User */
INSERT INTO users (username, password, name, role)
VALUES ('admin', 'admin123', 'System Administrator', 'admin');
