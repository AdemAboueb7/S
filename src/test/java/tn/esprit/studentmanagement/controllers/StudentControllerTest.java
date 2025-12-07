package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.services.IStudentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private IStudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setIdStudent(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john@example.com");
        student1.setPhone("123456789");
        student1.setDateOfBirth(LocalDate.of(2000, 1, 1));
        student1.setAddress("123 Main St");

        student2 = new Student();
        student2.setIdStudent(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setEmail("jane@example.com");
        student2.setPhone("987654321");
        student2.setDateOfBirth(LocalDate.of(2001, 5, 15));
        student2.setAddress("456 Oak Ave");
    }

    @Test
    void testGetAllStudents() {
        // Arrange
        List<Student> studentList = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(studentList);

        // Act
        List<Student> result = studentController.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudent() {
        // Arrange
        when(studentService.getStudentById(1L)).thenReturn(student1);

        // Act
        Student result = studentController.getStudent(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdStudent());
        assertEquals("John", result.getFirstName());
        assertEquals("john@example.com", result.getEmail());
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testGetStudentNotFound() {
        // Arrange
        when(studentService.getStudentById(999L)).thenReturn(null);

        // Act
        Student result = studentController.getStudent(999L);

        // Assert
        assertNull(result);
        verify(studentService, times(1)).getStudentById(999L);
    }

    @Test
    void testCreateStudent() {
        // Arrange
        Student newStudent = new Student();
        newStudent.setFirstName("Bob");
        newStudent.setLastName("Johnson");
        newStudent.setEmail("bob@example.com");

        Student savedStudent = new Student();
        savedStudent.setIdStudent(3L);
        savedStudent.setFirstName("Bob");
        savedStudent.setLastName("Johnson");
        savedStudent.setEmail("bob@example.com");

        when(studentService.saveStudent(newStudent)).thenReturn(savedStudent);

        // Act
        Student result = studentController.createStudent(newStudent);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getIdStudent());
        assertEquals("Bob", result.getFirstName());
        verify(studentService, times(1)).saveStudent(newStudent);
    }

    @Test
    void testUpdateStudent() {
        // Arrange
        student1.setFirstName("John Updated");
        when(studentService.saveStudent(student1)).thenReturn(student1);

        // Act
        Student result = studentController.updateStudent(student1);

        // Assert
        assertNotNull(result);
        assertEquals("John Updated", result.getFirstName());
        verify(studentService, times(1)).saveStudent(student1);
    }

    @Test
    void testDeleteStudent() {
        // Arrange
        Long studentId = 1L;
        doNothing().when(studentService).deleteStudent(studentId);

        // Act
        studentController.deleteStudent(studentId);

        // Assert
        verify(studentService, times(1)).deleteStudent(studentId);
    }

    @Test
    void testDeleteMultipleStudents() {
        // Arrange
        doNothing().when(studentService).deleteStudent(anyLong());

        // Act
        studentController.deleteStudent(1L);
        studentController.deleteStudent(2L);

        // Assert
        verify(studentService, times(1)).deleteStudent(1L);
        verify(studentService, times(1)).deleteStudent(2L);
        verify(studentService, times(2)).deleteStudent(anyLong());
    }
}
