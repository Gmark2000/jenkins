package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;
import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceTest {
    Service service;

    @BeforeAll
    void createService() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @org.junit.jupiter.api.Test
    void saveStudent() {
        int result = service.saveStudent("78", "Mark", 532);
        assertEquals(1, result);
        service.deleteStudent("78");
    }

    @org.junit.jupiter.api.Test
    void saveHomework() {
        int result = service.saveHomework("1", "This is my test homework.", 10, 0);
        assertNotEquals(0, result);
        service.deleteHomework("1");
    }

    @org.junit.jupiter.api.Test
    void updateStudent() {
        service.saveStudent("8", "Mark", 532);
        int result = service.updateStudent("8", "Maaaark", 532);
        assertTrue(result == 1);
        service.deleteStudent("8");
    }

    @org.junit.jupiter.api.Test
    void updateHomework() {
        service.saveHomework("2", "This is my test homework.", 10, 9);
        int result = service.updateHomework("2", "Semmi", 12, 9);
        assertTrue(result == 1);
        service.deleteHomework("2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "8", "9"})
    void deleteStudent(String studentId) {
        service.saveStudent(studentId, "Mark", 532);
        assertEquals(1, service.deleteStudent(studentId));
        //service.deleteStudent(studentId);
    }
}