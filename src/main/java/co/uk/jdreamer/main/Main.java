package co.uk.jdreamer.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import co.uk.jdreamer.impl.MovieServiceImpl;
import co.uk.jdreamer.impl.ParentalControlLevels;
import co.uk.jdreamer.impl.ParentalControlServiceImpl;
import co.uk.jdreamer.parental_control_service.ParentalControlService;
import co.uk.jdreamer.thirdparty.TechnicalFailureException;
import co.uk.jdreamer.thirdparty.TitleNotFoundException;

public class Main {

	public static void main(String[] args) {

		ParentalControlService parentalControlService = new ParentalControlServiceImpl(new MovieServiceImpl());
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		/* Insert Command Line for the below input:
		 * - Parental Control Level preference
		 * - Movie id you would like to view
		 */        
		try {
			System.out.println("Please enter your Parental Control Level:");
			String customerParentalControlLevel = input.readLine();

			if (customerParentalControlLevel == null || customerParentalControlLevel.length() == 0)
				System.out.println("You must to enter a Parental Control Level value");
			else if (ParentalControlLevels.getByString(customerParentalControlLevel) == null)
				System.out.println("The Parental Control Level you entered does not existent\n Please enter one of these values as your Parental Control Level:  U, PG, 12, 15, 18\\n");
			else {
				boolean canWatchMovie;
				System.out.println("Please enter the movie id you would like to watch:");
				String movieId = input.readLine();
				try {
					canWatchMovie = parentalControlService.canWatchMovie(customerParentalControlLevel, movieId);
					System.out.println(canWatchMovie ? "You can watch this movie\n" : "You are not allowed to watch this movie\n");
				} catch (TechnicalFailureException | TitleNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred. Please start again.");
		}

	}

}
