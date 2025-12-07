package tn.esprit.studentmanagement.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
    }

    @Test
    void testDepartmentCreation() {
        // Arrange & Act
        department.setIdDepartment(1L);
        department.setName("Informatique");

        // Assert
        assertNotNull(department);
        assertEquals(1L, department.getIdDepartment());
        assertEquals("Informatique", department.getName());
    }

    @Test
    void testDepartmentNameNotNull() {
        // Arrange
        department.setName("Génie Logiciel");

        // Assert
        assertNotNull(department.getName());
    }

    @Test
    void testDepartmentSettersAndGetters() {
        // Act
        department.setName("Réseaux et Télécommunications");

        // Assert
        assertEquals("Réseaux et Télécommunications", department.getName());
    }
}