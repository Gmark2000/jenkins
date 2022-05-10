package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.mockito.Mockito.times;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(MockitoJUnitRunner.class)
public class ServiceMockTest {
    @Mock
    Service mockedService;

    @BeforeAll
    void createService() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository homeworkXMLRepository = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(gradeValidator, "grades.xml");
        mockedService = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }

    @Test
    public void saveStudent() {
        Mockito.when(mockedService.saveStudent(anyString(), anyString(), anyInt())).thenReturn(1);

        int result = mockedService.saveStudent("7", "Mark", 532);
        assertEquals(1, result);

        Mockito.verify(mockedService).saveStudent(anyString(), anyString(), anyInt());
    }

    @Test
    public void saveHomework() {
        Mockito.when(mockedService.saveHomework(anyString(),anyString(),anyInt(),anyInt())).thenReturn(1);
        Homework hw = new Homework("2", "This is my test homework.", 10, 0);
        int result = mockedService.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        assertEquals(1, result);

        Mockito.verify(mockedService).saveHomework(anyString(),anyString(),anyInt(),anyInt());
    }

    @Test
    public void deleteStudent() {
        Mockito.when(mockedService.deleteStudent(anyString())).thenReturn(1);
        int result = mockedService.deleteStudent("2");
        assertEquals(1, result);
        Mockito.verify(mockedService, times(1)).deleteStudent(anyString());
    }
}
