package ua.epam.final_project.dao.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.dao.IGenreDao;
import ua.epam.final_project.dao.IRoleDao;
import ua.epam.final_project.dao.TestSQLConnectionProvider;
import ua.epam.final_project.exception.DataNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Integration] Dao layer")
@Feature("Dao layer")
class GenreDaoImplTest {

    private static Connection connection;
    private static IGenreDao genreDao;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = TestSQLConnectionProvider.getConnectionToTestDB();
        genreDao = new GenreDaoImpl(connection);
    }

    @AfterAll
    static void resetAll() throws SQLException {
        TestSQLConnectionProvider.resetDb(connection);
    }

    @Test
    @DisplayName("Get number of genres from DB")
    @Story("GenreDao")
    @Description("Method execute SQL query to get total number of roles in DB")
    void getNumberOfGenres() throws DataNotFoundException {
        assertTrue(genreDao.getNumberOfGenres() >= 2);
    }

    @Test
    @DisplayName("Find all genres from DB")
    @Story("GenreDao")
    void findAllGenres() throws DataNotFoundException {
        assertTrue(genreDao.findAllGenres().size() >= 2);
    }

    @Test
    @DisplayName("Find single genre in DB")
    @Story("GenreDao")
    void findGenreById() throws DataNotFoundException {
        assertEquals("genre1", genreDao.findGenreById(1).getGenreEn());
        assertEquals("genre2", genreDao.findGenreById(2).getGenreEn());
        assertNull(genreDao.findGenreById(99));
    }
}