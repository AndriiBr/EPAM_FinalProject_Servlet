package ua.epam.final_project.dao.implementation;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.epam.final_project.dao.IEditionDao;
import ua.epam.final_project.dao.TestSQLConnectionProvider;
import ua.epam.final_project.entity.Edition;
import ua.epam.final_project.entity.User;
import ua.epam.final_project.exception.DataNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Integration] Dao layer")
@Feature("Dao layer")
class EditionDaoImplTest {

    private static Connection connection;
    private static IEditionDao editionDao;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = TestSQLConnectionProvider.getConnectionToTestDB();
        editionDao = new EditionDaoImpl(connection);
    }

    @AfterAll
    static void resetAll() throws SQLException {
        TestSQLConnectionProvider.resetDb(connection);
    }

    @Test
    @DisplayName("Get number of editions from DB: 1 argument")
    @Story("EditionDao")
    @Description("Method execute SQL query to get total number of editions in DB")
    void getNumberOfEditions() throws DataNotFoundException {
        assertTrue(editionDao.getNumberOfEditions(0) >= 7);
        assertTrue(editionDao.getNumberOfEditions(1) >= 4);
    }

    @Test
    @DisplayName("Get number of editions from DB: 3 arguments")
    @Story("EditionDao")
    @Description("Get number of editions in DB by user, availability, genre")
    void getNumberOfEditions_2() throws DataNotFoundException {
        User user = new User();
        user.setId(2);
        assertEquals(3, editionDao.getNumberOfEditions(user, true, 0));
        assertEquals(2, editionDao.getNumberOfEditions(user, true, 1));

        assertTrue(editionDao.getNumberOfEditions(user, false, 0) >= 4);
        assertTrue(editionDao.getNumberOfEditions(user, false, 1) >= 2);
    }

    @Test
    @DisplayName("Find all editions from DB")
    @Story("EditionDao")
    void findAllEditions() throws DataNotFoundException {
        assertTrue(editionDao.findAllEditions().size() > 0);
    }

    @Test
    @DisplayName("Find editions from DB with pagination: 4 parameters")
    @Story("EditionDao")
    @Description("Tests that the correct number of elements per page has been returned")
    void findAllEditionsFromTo_1()
            throws DataNotFoundException {

        assertEquals(4,
                editionDao
                        .findAllEditionsFromTo(4, 1, 0, "")
                        .size());
        assertTrue(
                editionDao
                        .findAllEditionsFromTo(4, 2, 0, "")
                        .size() >= 1);
        assertTrue(
                editionDao
                        .findAllEditionsFromTo(4, 1, 2, "")
                        .size() >= 1);
    }

    @Test
    @DisplayName("Find editions from DB with pagination: 6 parameters")
    @Story("EditionDao")
    void FindAllEditionsFromTo_2() throws DataNotFoundException {
        User user = new User();
        user.setId(2);

        assertEquals(2,
                editionDao
                        .findAllEditionsFromTo(user, true, 2, 1, 0, "")
                        .size());
        assertEquals(1,
                editionDao
                        .findAllEditionsFromTo(user, true, 2, 2, 0, "")
                        .size());
        assertEquals(3,
                editionDao
                        .findAllEditionsFromTo(user, false, 3, 1, 0, "")
                        .size());
    }

    @Test
    @DisplayName("[Success] Find editions from DB by title")
    @Story("EditionDao")
    void findEditionByTitle_Success() throws DataNotFoundException {
        assertNotNull(editionDao.findEditionByTitle("edition1"));
    }

    @Test
    @DisplayName("[Fail] Find editions from DB by title")
    @Story("EditionDao")
    void findEditionByTitle_Fail() throws DataNotFoundException {
        assertNull(editionDao.findEditionByTitle("notExistOne"));
    }

    @Test
    @DisplayName("[Success] Find edition in DB by id")
    @Story("EditionDao")
    @Description("Check that edition with correct id will be found")
    void findEditionById_Success() throws DataNotFoundException {
        assertNotNull(editionDao.findEditionById(1));
    }

    @Test
    @DisplayName("[Fail] Find edition in DB by id")
    @Story("EditionDao")
    @Description("Check that edition with wrong id won`t be found")
    void findEditionById_Fail() throws DataNotFoundException {
        assertNull(editionDao.findEditionById(999));
    }

    @Test
    @DisplayName("[Success] Insert new edition in DB")
    @Story("EditionDao")
    void insertNewEdition_Success() {
        Edition edition = new Edition("testEdition", "тестоваНазва", "textEn1", "textUa1", 1);

        assertTrue(editionDao.insertNewEdition(edition));
    }

    @Test
    @DisplayName("[Fail] Insert new edition in DB")
    @Story("EditionDao")
    @Description("Check that the same edition won`t be added to DB")
    void insertNewEdition_Fail() throws DataNotFoundException {
        Edition edition = editionDao.findEditionById(1);

        assertFalse(editionDao.insertNewEdition(edition));
    }

    @Test
    @DisplayName("Update edition in DB")
    @Story("EditionDao")
    @Description("Check each field after update with provided edition")
    void updateEdition() throws DataNotFoundException {
        Edition originalEdition = new Edition("newEditionEn", "newEditionUa", "textEn1", "textUa1", 1);
        editionDao.insertNewEdition(originalEdition);

        Edition updatedEdition = prepareUpdatedEdition(originalEdition.getTitleEn());
        editionDao.updateEdition(updatedEdition);

        Edition finalEdition = editionDao.findEditionByTitle(updatedEdition.getTitleEn());

        assertEquals(updatedEdition.getTitleEn(), finalEdition.getTitleEn());
        assertEquals(updatedEdition.getTitleUa(), finalEdition.getTitleUa());
        assertEquals(updatedEdition.getImagePath(), finalEdition.getImagePath());
        assertEquals(updatedEdition.getGenreId(), finalEdition.getGenreId());
        assertEquals(updatedEdition.getPrice(), finalEdition.getPrice());
    }

    @Test
    @DisplayName("Delete edition from DB")
    @Story("EditionDao")
    void deleteEdition() throws DataNotFoundException {
        Edition edition = new Edition("EditionToBeDeletedEn", "EditionToBeDeletedUa", "textEn1", "textUa1", 2);
        editionDao.insertNewEdition(edition);

        assertNotNull(editionDao.findEditionByTitle(edition.getTitleEn()));
        assertTrue(editionDao.deleteEdition(edition));
        assertNull(editionDao.findEditionByTitle(edition.getTitleEn()));
    }

    @Step("Modify edition fields before 'updateUser' testing")
    private Edition prepareUpdatedEdition(String titleEn) throws DataNotFoundException {
        Edition updatedEdition = editionDao.findEditionByTitle(titleEn);
        updatedEdition.setTitleEn("EnEn");
        updatedEdition.setTitleUa("UaUa");
        updatedEdition.setTextEn("TeEn");
        updatedEdition.setTextUa("TeUa");
        updatedEdition.setImagePath("ImageTest");
        updatedEdition.setGenreId(2);
        updatedEdition.setPrice(9999);

        return updatedEdition;
    }
}