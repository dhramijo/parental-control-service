package co.uk.jdreamer.impl;

import co.uk.jdreamer.thirdparty.MovieService;
import co.uk.jdreamer.thirdparty.TechnicalFailureException;
import co.uk.jdreamer.thirdparty.TitleNotFoundException;

public class MovieServiceImpl implements MovieService {

	@Override
	public String getParentalControlLevel(String titleId) throws TitleNotFoundException, TechnicalFailureException {
		switch (titleId) {
		case "1":
			return "U";
		case "2":
			return "PG";
		case "3":
			return "12";
		case "4":
			return "15";
		case "5":
			return "18";
		case "service failure":
			throw new TechnicalFailureException("Sorry, we have experience a technical failure.\n");
		default:
			throw new TitleNotFoundException("Sorry, the movie " + titleId + " you are looking for, is not available.");
		}
	}

}