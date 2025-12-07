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
        department1.setName("Computer Science");
        department1.setHead("Dr. Smith");
        department1.setLocation("Building A");
        department1.setPhone("123-456-7890");

        department2 = new Department();
        department2.setIdDepartment(2L);
        department2.setName("Engineering");
        department2.setHead("Dr. Johnson");
        department2.setLocation("Building B");
        department2.setPhone("098-765-4321");
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
        assertEquals("Computer Science", result.get(0).getName());
        assertEquals("Engineering", result.get(1).getName());
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
        assertEquals("Computer Science", result.getName());
        assertEquals("Dr. Smith", result.getHead());
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
        assertEquals("Computer Science", result.getName());
        assertEquals("Dr. Smith", result.getHead());
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    void testSaveNewDepartment() {
        // Arrange
        Department newDepartment = new Department();
        newDepartment.setName("Mathematics");
        newDepartment.setHead("Dr. Brown");

        Department savedDepartment = new Department();
        savedDepartment.setIdDepartment(3L);
        savedDepartment.setName("Mathematics");
        savedDepartment.setHead("Dr. Brown");

        when(departmentRepository.save(newDepartment)).thenReturn(savedDepartment);

        // Act
        Department result = departmentService.saveDepartment(newDepartment);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getIdDepartment());
        assertEquals("Mathematics", result.getName());
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
