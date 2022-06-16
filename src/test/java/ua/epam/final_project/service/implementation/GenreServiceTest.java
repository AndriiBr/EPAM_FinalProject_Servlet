package ua.epam.final_project.service.implementation;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.epam.final_project.dao.IGenreDao;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownGenreException;
import ua.epam.final_project.service.IGenreService;
import ua.epam.final_project.service.ServiceFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

@DisplayName("Service layer")
@Feature("Service layer")
class GenreServiceTest {

    @Mock
    private IGenreDao genreDao;

    private final IGenreService genreService;

    public GenreServiceTest() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        genreService = ServiceFactory.getGenreService();

        Field genreDaoField = genreService.getClass().getDeclaredField("genreDao");
        genreDaoField.setAccessible(true);
        genreDaoField.set(genreService, genreDao);
    }

    @Test
    @DisplayName("Get number of genres in DB")
    @Story("Genre service")
    void getNumberOfGenres() throws DataNotFoundException, UnknownGenreException {
        Mockito.when(genreDao.getNumberOfGenres())
                .thenReturn(33);

        assertEquals(33, genreService.getNumberOfGenres());
    }

    @Test
    @DisplayName("[UnknownGenreException]Get number of genres in DB")
    @Story("Genre service")
    void getNumberOfGenres_Exception() throws DataNotFoundException {
        Mockito.when(genreDao.getNumberOfGenres())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownGenreException.class, genreService::getNumberOfGenres);
    }

    @Test
    @DisplayName("Get all genres from DB")
    @Story("Genre service")
    void findAllGenres() throws DataNotFoundException, UnknownGenreException {
        List<Genre> genreList = Arrays.asList(new Genre(), new Genre());
        Mockito.when(genreDao.findAllGenres())
                .thenReturn(genreList);

        assertEquals(2, genreService.findAllGenres().size());
    }

    @Test
    @DisplayName("[UnknownGenreException] Get all genres from DB")
    @Story("Genre service")
    void findAllGenres_Exception() throws DataNotFoundException {
        Mockito.when(genreDao.findAllGenres())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownGenreException.class, genreService::findAllGenres);
    }

    @Test
    @DisplayName("Find genre from DB by id")
    @Story("Genre service")
    void findGenreById() throws UnknownGenreException, DataNotFoundException {
        Genre genre = new Genre();
        Mockito.when(genreDao.findGenreById(anyInt()))
                .thenReturn(genre);

        Assertions.assertNotNull(genreService.findGenreById(1));
    }

    @Test
    @DisplayName("[UnknownGenreException] Find genre from DB by id")
    @Story("Genre service")
    void findGenreById_Exception() throws DataNotFoundException {
        Mockito.when(genreDao.findGenreById(anyInt()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownGenreException.class, () -> genreService.findGenreById(1));
    }
}