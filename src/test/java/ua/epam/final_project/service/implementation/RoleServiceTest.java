package ua.epam.final_project.service.implementation;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.dao.IRoleDao;
import ua.epam.final_project.entity.Role;
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

@DisplayName("Service layer")
@Feature("Service layer")
class RoleServiceTest {

    @Mock
    private IRoleDao roleDao;

    private final IRoleService roleService;

    public RoleServiceTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        roleService = ServiceFactory.getRoleService();

        Field roleDaoField = roleService.getClass().getDeclaredField("roleDao");
        roleDaoField.setAccessible(true);
        roleDaoField.set(roleService, roleDao);
    }

    @Test
    @DisplayName("Get number of roles from DB")
    @Story("Role service")
    void getNumberOfRoles() throws DataNotFoundException, UnknownRoleException {
        Mockito.when(roleDao.getNumberOfRoles()).thenReturn(33);

        assertEquals(33, roleService.getNumberOfRoles());
    }

    @Test
    @DisplayName("[Exception]Get number of roles in DB")
    @Story("Role service")
    void getNumberOfRoles_Exception() throws DataNotFoundException {
        Mockito.when(roleDao.getNumberOfRoles()).thenThrow(DataNotFoundException.class);

        assertThrows(UnknownRoleException.class, roleService::getNumberOfRoles);
    }

    @Test
    @DisplayName("Get all roles from DB")
    @Story("Role service")
    void findAllRoles() throws DataNotFoundException, UnknownRoleException {
        List<Role> roleList = Arrays.asList(new Role(), new Role());
        Mockito.when(roleDao.findAllRoles()).thenReturn(roleList);

        assertEquals(2, roleService.findAllRoles().size());
    }

    @Test
    @DisplayName("[Exception] Get all roles from DB")
    @Story("Role service")
    void findAllRoles_Exception() throws DataNotFoundException {
        Mockito.when(roleDao.findAllRoles()).thenThrow(DataNotFoundException.class);

        assertThrows(UnknownRoleException.class, roleService::findAllRoles);
    }

    @Test
    @DisplayName("Find role from DB by id")
    @Story("Role service")
    void findRoleById() throws UnknownRoleException, DataNotFoundException {
        Role role = new Role();
        Mockito.when(roleDao.findRoleById(anyInt())).thenReturn(role);

        Assertions.assertNotNull(roleService.findRoleById(1));
    }

    @Test
    @DisplayName("[Exception] Find role from DB by id")
    @Story("Role service")
    void findRoleById_Exception() throws DataNotFoundException {
        Mockito.when(roleDao.findRoleById(anyInt())).thenThrow(DataNotFoundException.class);

        assertThrows(UnknownRoleException.class, () -> roleService.findRoleById(1));
    }
}