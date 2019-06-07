package co.uk.jdreamer.impl;

import co.uk.jdreamer.parental_control_service.ParentalControlService;
import co.uk.jdreamer.thirdparty.MovieService;
import co.uk.jdreamer.thirdparty.TechnicalFailureException;
import co.uk.jdreamer.thirdparty.TitleNotFoundException;

public class ParentalControlServiceImpl implements ParentalControlService {

	private MovieService movieService;

	public ParentalControlServiceImpl (MovieService movieService) {
		this.movieService = movieService;
	}

	public boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws TechnicalFailureException, TitleNotFoundException {

		// Check if the customer parental control exists
		if (!ParentalControlLevels.parentalControlLevelsMap.containsKey(customerParentalControlLevel))
			throw new TechnicalFailureException("Sorry, the parental control level you have entered does not exist");

		String movieParentalControlLevel = movieService.getParentalControlLevel(movieId);

		// Check if the customer parental control for the given Movie exists
		if (!ParentalControlLevels.parentalControlLevelsMap.containsKey(movieParentalControlLevel))
			throw new TitleNotFoundException("Sorry, The movie service could not find the given movie");

		/* If the parental control level of the movie is equal to or less than 
		 * the customer’s preference indicate to the caller that the customer can watch the movie
		 * */
		return ParentalControlLevels.getByString(movieParentalControlLevel).getValue() <= ParentalControlLevels.getByString(customerParentalControlLevel).getValue();

	}

}
