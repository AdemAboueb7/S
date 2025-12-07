package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment enrollment1;
    private Enrollment enrollment2;

    @BeforeEach
    void setUp() {
        enrollment1 = new Enrollment();
        enrollment1.setIdEnrollment(1L);

        enrollment2 = new Enrollment();
        enrollment2.setIdEnrollment(2L);
    }

    @Test
    void testGetAllEnrollments() {
        // Arrange
        List<Enrollment> enrollmentList = Arrays.asList(enrollment1, enrollment2);
        when(enrollmentRepository.findAll()).thenReturn(enrollmentList);

        // Act
        List<Enrollment> result = enrollmentService.getAllEnrollments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEnrollmentsEmpty() {
        // Arrange
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Enrollment> result = enrollmentService.getAllEnrollments();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void testGetEnrollmentById() {
        // Arrange
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment1));

        // Act
        Enrollment result = enrollmentService.getEnrollmentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdEnrollment());
        verify(enrollmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEnrollmentByIdNotFound() {
        // Arrange
        when(enrollmentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Enrollment result = enrollmentService.getEnrollmentById(999L);

        // Assert
        assertNull(result);
        verify(enrollmentRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveEnrollment() {
        // Arrange
        when(enrollmentRepository.save(enrollment1)).thenReturn(enrollment1);

        // Act
        Enrollment result = enrollmentService.saveEnrollment(enrollment1);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdEnrollment());
        verify(enrollmentRepository, times(1)).save(enrollment1);
    }

    @Test
    void testSaveNewEnrollment() {
        // Arrange
        Enrollment newEnrollment = new Enrollment();

        Enrollment savedEnrollment = new Enrollment();
        savedEnrollment.setIdEnrollment(3L);

        when(enrollmentRepository.save(newEnrollment)).thenReturn(savedEnrollment);

        // Act
        Enrollment result = enrollmentService.saveEnrollment(newEnrollment);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getIdEnrollment());
        verify(enrollmentRepository, times(1)).save(newEnrollment);
    }

    @Test
    void testDeleteEnrollment() {
        // Arrange
        Long enrollmentId = 1L;
        doNothing().when(enrollmentRepository).deleteById(enrollmentId);

        // Act
        enrollmentService.deleteEnrollment(enrollmentId);

        // Assert
        verify(enrollmentRepository, times(1)).deleteById(enrollmentId);
    }

    @Test
    void testDeleteMultipleEnrollments() {
        // Arrange
        doNothing().when(enrollmentRepository).deleteById(anyLong());

        // Act
        enrollmentService.deleteEnrollment(1L);
        enrollmentService.deleteEnrollment(2L);

        // Assert
        verify(enrollmentRepository, times(1)).deleteById(1L);
        verify(enrollmentRepository, times(1)).deleteById(2L);
        verify(enrollmentRepository, times(2)).deleteById(anyLong());
    }
}
