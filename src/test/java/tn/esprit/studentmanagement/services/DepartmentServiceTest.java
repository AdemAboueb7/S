package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        department1 = new Department();
        department1.setIdDepartment(1L);
        department1.setNameDepartment("Computer Science");
        department1.setResponsible("Dr. Smith");

        department2 = new Department();
        department2.setIdDepartment(2L);
        department2.setNameDepartment("Engineering");
        department2.setResponsible("Dr. Johnson");
    }

    @Test
    void testGetAllDepartments() {
        // Arrange
        List<Department> departmentList = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(departmentList);

        // Act
        List<Department> result = departmentService.getAllDepartments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Computer Science", result.get(0).getNameDepartment());
        assertEquals("Engineering", result.get(1).getNameDepartment());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDepartmentsEmpty() {
        // Arrange
        when(departmentRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Department> result = departmentService.getAllDepartments();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentById() {
        // Arrange
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department1));

        // Act
        Department result = departmentService.getDepartmentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdDepartment());
        assertEquals("Computer Science", result.getNameDepartment());
        assertEquals("Dr. Smith", result.getResponsible());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDepartmentByIdNotFound() {
        // Arrange
        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Department result = departmentService.getDepartmentById(999L);

        // Assert
        assertNull(result);
        verify(departmentRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveDepartment() {
        // Arrange
        when(departmentRepository.save(department1)).thenReturn(department1);

        // Act
        Department result = departmentService.saveDepartment(department1);

        // Assert
        assertNotNull(result);
        assertEquals("Computer Science", result.getNameDepartment());
        assertEquals("Dr. Smith", result.getResponsible());
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    void testSaveNewDepartment() {
        // Arrange
        Department newDepartment = new Department();
        newDepartment.setNameDepartment("Mathematics");
        newDepartment.setResponsible("Dr. Brown");

        Department savedDepartment = new Department();
        savedDepartment.setIdDepartment(3L);
        savedDepartment.setNameDepartment("Mathematics");
        savedDepartment.setResponsible("Dr. Brown");

        when(departmentRepository.save(newDepartment)).thenReturn(savedDepartment);

        // Act
        Department result = departmentService.saveDepartment(newDepartment);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getIdDepartment());
        assertEquals("Mathematics", result.getNameDepartment());
        verify(departmentRepository, times(1)).save(newDepartment);
    }

    @Test
    void testDeleteDepartment() {
        // Arrange
        Long departmentId = 1L;
        doNothing().when(departmentRepository).deleteById(departmentId);

        // Act
        departmentService.deleteDepartment(departmentId);

        // Assert
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

    @Test
    void testDeleteMultipleDepartments() {
        // Arrange
        doNothing().when(departmentRepository).deleteById(anyLong());

        // Act
        departmentService.deleteDepartment(1L);
        departmentService.deleteDepartment(2L);

        // Assert
        verify(departmentRepository, times(1)).deleteById(1L);
        verify(departmentRepository, times(1)).deleteById(2L);
        verify(departmentRepository, times(2)).deleteById(anyLong());
    }
}
