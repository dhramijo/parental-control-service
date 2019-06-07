package co.uk.jdreamer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import co.uk.jdreamer.impl.ParentalControlServiceImpl;
import co.uk.jdreamer.parental_control_service.ParentalControlService;
import co.uk.jdreamer.thirdparty.MovieService;
import co.uk.jdreamer.thirdparty.TechnicalFailureException;
import co.uk.jdreamer.thirdparty.TitleNotFoundException;

public class ParentalControlServiceImplTest {

	private MovieService movieService;

	@Before
	public void setup() throws TechnicalFailureException, TitleNotFoundException {
		movieService = mock(MovieService.class);
	}

	@Test
	public void whenUserParentLevelIsULevelAndMovieIsULevelThenReturnTrue() throws Exception {
		final String movieId = "1";
		final String userLevel = "U";
		final String movieLevel = "U";
		when(movieService.getParentalControlLevel(movieId)).thenReturn(movieLevel);
		ParentalControlService pcs = new ParentalControlServiceImpl(movieService);
		assertTrue(pcs.canWatchMovie(userLevel, movieId));
	}

	@Test
	public void whenParentalLevelIsHigherAndMovieLevelIsLowerThenReturnTrue() throws Exception {
		final String movieId = "1";
		final String userLevel = "18";
		final String movieLevel = "15";
		when(movieService.getParentalControlLevel(movieId)).thenReturn(movieLevel);
		ParentalControlService pcs = new ParentalControlServiceImpl(movieService);
		assertTrue(pcs.canWatchMovie(userLevel, movieId));
	}

	@Test
	public void whenParentalLevelIsLowerAndMovieLevelIsHigherThenReturnFalse() throws Exception {
		final String movieId = "1";
		final String userLevel = "PG";
		final String movieLevel = "15";
		when(movieService.getParentalControlLevel(movieId)).thenReturn(movieLevel);
		ParentalControlService pcs = new ParentalControlServiceImpl(movieService);
		assertFalse(pcs.canWatchMovie(userLevel, movieId));
	}

	@Test(expected = TitleNotFoundException.class)
	public void whenMovieNotInListThenThrowException() throws Exception {
		final String movieId = "Movie not in the Movie List";
		final String userLevel = "PG";
		ParentalControlService pcs = new ParentalControlServiceImpl(movieService);
		pcs.canWatchMovie(userLevel, movieId);
	}

	@Test(expected = TitleNotFoundException.class)
	public void shouldReturnFalseAndIfTitleNotFoundFeedback() throws Exception {
		when(movieService.getParentalControlLevel("movie not existent")).thenThrow(new TitleNotFoundException("The movie service could not find the given movie"));
		ParentalControlService pcs = new ParentalControlServiceImpl(movieService);
		Assert.assertFalse(pcs.canWatchMovie("18","movie not existent"));

	}

	@Test(expected = TechnicalFailureException.class)
	public void shouldDetectTechnicalFailureException() throws Exception {
		when(movieService.getParentalControlLevel("service crash")).thenThrow(new TechnicalFailureException("Technical failure"));
		ParentalControlService pcs = new ParentalControlServiceImpl(movieService);
		Assert.assertFalse(pcs.canWatchMovie("18","service crash"));

	}
}
