package ua.epam.final_project.service.implementation;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ua.epam.final_project.dao.IRoleDao;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.entity.Role;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownRoleException;
import ua.epam.final_project.service.IRoleService;
import ua.epam.final_project.service.ServiceFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

@DisplayName("[Unit] Service layer")
@Feature("Service layer")
class RoleServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PostgresDaoFactory daoFactory;
    @InjectMocks
    private RoleService roleService;

    private final Role role;
    private final List<Role> roleList;

    public RoleServiceTest() {
        MockitoAnnotations.openMocks(this);

        role = new Role();
        roleList = Arrays.asList(new Role(), new Role());
    }

    @BeforeEach
    public void setUp() throws DataBaseConnectionException {
        Mockito.doNothing().when(daoFactory).getConnection();
        Mockito.doNothing().when(daoFactory).releaseConnection();
        Mockito.doNothing().when(daoFactory).beginTransaction();
        Mockito.doNothing().when(daoFactory).commitTransaction();
    }

    @Test
    @DisplayName("Get number of roles from DB")
    @Story("Role service")
    void getNumberOfRoles() throws DataNotFoundException, UnknownRoleException {
        Mockito.when(daoFactory.getRoleDao().getNumberOfRoles())
                .thenReturn(33);

        assertEquals(33, roleService.getNumberOfRoles());
    }

    @Test
    @DisplayName("[UnknownRoleException]Get number of roles in DB")
    @Story("Role service")
    void getNumberOfRoles_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getRoleDao().getNumberOfRoles())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownRoleException.class, roleService::getNumberOfRoles);
    }

    @Test
    @DisplayName("Get all roles from DB")
    @Story("Role service")
    void findAllRoles() throws DataNotFoundException, UnknownRoleException {
        Mockito.when(daoFactory.getRoleDao().findAllRoles())
                .thenReturn(roleList);

        assertEquals(2, roleService.findAllRoles().size());
    }

    @Test
    @DisplayName("[UnknownRoleException] Get all roles from DB")
    @Story("Role service")
    void findAllRoles_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getRoleDao().findAllRoles())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownRoleException.class, roleService::findAllRoles);
    }

    @Test
    @DisplayName("Find role from DB by id")
    @Story("Role service")
    void findRoleById() throws UnknownRoleException, DataNotFoundException {
        Mockito.when(daoFactory.getRoleDao().findRoleById(anyInt()))
                .thenReturn(role);

        Assertions.assertNotNull(roleService.findRoleById(1));
    }

    @Test
    @DisplayName("[UnknownRoleException] Find role from DB by id")
    @Story("Role service")
    void findRoleById_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getRoleDao().findRoleById(anyInt()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownRoleException.class, () -> roleService.findRoleById(1));
    }
}