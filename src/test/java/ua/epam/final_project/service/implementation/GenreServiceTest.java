package ua.epam.final_project.service.implementation;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ua.epam.final_project.dao.PostgresDaoFactory;
import ua.epam.final_project.entity.Genre;
import ua.epam.final_project.exception.DataBaseConnectionException;
import ua.epam.final_project.exception.DataNotFoundException;
import ua.epam.final_project.exception.UnknownGenreException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

@DisplayName("[Unit] Service layer")
@Feature("Service layer")
class GenreServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PostgresDaoFactory daoFactory;

    @InjectMocks
    private GenreService genreService;

    public GenreServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() throws DataBaseConnectionException {
        Mockito.doNothing().when(daoFactory).getConnection();
        Mockito.doNothing().when(daoFactory).releaseConnection();
        Mockito.doNothing().when(daoFactory).beginTransaction();
        Mockito.doNothing().when(daoFactory).commitTransaction();
    }

    @Test
    @DisplayName("Get number of genres in DB")
    @Story("Genre service")
    void getNumberOfGenres() throws DataNotFoundException, UnknownGenreException {
        Mockito.when(daoFactory.getGenreDao().getNumberOfGenres())
                .thenReturn(33);

        assertEquals(33, genreService.getNumberOfGenres());
    }

    @Test
    @DisplayName("[UnknownGenreException]Get number of genres in DB")
    @Story("Genre service")
    void getNumberOfGenres_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getGenreDao().getNumberOfGenres())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownGenreException.class, genreService::getNumberOfGenres);
    }

    @Test
    @DisplayName("Get all genres from DB")
    @Story("Genre service")
    void findAllGenres() throws DataNotFoundException, UnknownGenreException {
        List<Genre> genreList = Arrays.asList(new Genre(), new Genre());
        Mockito.when(daoFactory.getGenreDao().findAllGenres())
                .thenReturn(genreList);

        assertEquals(2, genreService.findAllGenres().size());
    }

    @Test
    @DisplayName("[UnknownGenreException] Get all genres from DB")
    @Story("Genre service")
    void findAllGenres_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getGenreDao().findAllGenres())
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownGenreException.class, genreService::findAllGenres);
    }

    @Test
    @DisplayName("Find genre from DB by id")
    @Story("Genre service")
    void findGenreById() throws UnknownGenreException, DataNotFoundException {
        Genre genre = new Genre();
        Mockito.when(daoFactory.getGenreDao().findGenreById(anyInt()))
                .thenReturn(genre);

        Assertions.assertNotNull(genreService.findGenreById(1));
    }

    @Test
    @DisplayName("[UnknownGenreException] Find genre from DB by id")
    @Story("Genre service")
    void findGenreById_Exception() throws DataNotFoundException {
        Mockito.when(daoFactory.getGenreDao().findGenreById(anyInt()))
                .thenThrow(DataNotFoundException.class);

        assertThrows(UnknownGenreException.class, () -> genreService.findGenreById(1));
    }
}