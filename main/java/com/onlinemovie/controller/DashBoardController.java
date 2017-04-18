package com.onlinemovie.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlinemovie.exceptions.ResourceNotFoundException;
import com.onlinemovie.model.Movie;
import com.onlinemovie.model.User;
import com.onlinemovie.model.UserMovieMap;
import com.onlinemovie.service.MovieService;

@Controller
public class DashBoardController {

	@Autowired
	private MovieService movieService;

	/**
	 * @return all available movie list
	 */

	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> getMovies() {

		List<Movie> availableMovies = movieService.getAllMovies();

		if (availableMovies == null)
			throw new ResourceNotFoundException("No movie found");

		return new ResponseEntity<List<Movie>>(availableMovies, HttpStatus.OK);
	}

	/**
	 * Renting a selected movie - should apply 20% discounted rate for
	 * registered user and no discount for regular user
	 * 
	 * @param email
	 *            - user identification
	 * @param muvId
	 *            -movie identification
	 * @return user profile and required payment
	 * 
	 */
	@RequestMapping(value = "/users/rents/{email}/{muvId}", method = RequestMethod.POST)
	public ResponseEntity<String> rentMovieForUser(@PathVariable("email") String email,
			@PathVariable("muvId") String muvId) {

		movieService.rentMovie(email, muvId);

		return new ResponseEntity<String>("Movie Rented", HttpStatus.OK);

	}

	@RequestMapping(value = "/users/{emailId}", method = RequestMethod.POST)
	public ResponseEntity<User> getUserDetails(@PathVariable("emailId") String emailId)
			throws ResourceNotFoundException {

		User userInfo = movieService.getUser(emailId);

		if (userInfo == null)
			throw new ResourceNotFoundException("No User for provided emailId present");

		return new ResponseEntity<User>(userInfo, HttpStatus.OK);

	}

	/**
	 * @param genre
	 *            - movie's genre
	 * @param year
	 *            - year of release of movie
	 * @param rent
	 *            - rent for movie
	 * @param resp
	 * @return movie list on the basis of parameters passed
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = "/movies/genres/{genre}", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> moviesByGenre(@PathVariable("genre") String genre, HttpServletResponse resp)
			throws ResourceNotFoundException {

		List<Movie> movieData = movieService.getMoviesByGenre(genre);

		if (movieData == null)
			throw new ResourceNotFoundException("No movie by this genre found");

		return new ResponseEntity<List<Movie>>(movieData, HttpStatus.OK);

	}

	@RequestMapping(value = "/movies/years/{year}", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> moviesByYear(@PathVariable("year") String year, HttpServletResponse resp)
			throws ResourceNotFoundException {

		List<Movie> movieData = null;

		int yr = Calendar.getInstance().get(Calendar.YEAR);
		if (year != null) {
			yr = Integer.parseInt(year);
		}

		movieData = movieService.getMoviesByYear(yr);

		if (movieData == null)
			throw new ResourceNotFoundException("No movie for this year found");

		return new ResponseEntity<List<Movie>>(movieData, HttpStatus.OK);

	}

	@RequestMapping(value = "/movies/rents/{rent}", method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> movies(@PathVariable("rent") String rent, HttpServletResponse resp)
			throws ResourceNotFoundException {

		List<Movie> movieData = null;

		float rentVal = Float.parseFloat(rent);
		movieData = movieService.getMoviesByRent(rentVal);

		if (movieData == null)
			throw new ResourceNotFoundException("No movie for this rent found");

		return new ResponseEntity<List<Movie>>(movieData, HttpStatus.OK);

	}

	/**
	 * displaying all expired movies for user which are having expired date
	 * before today.
	 * 
	 * @param emailId
	 * @return expired movie list
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = "/users/{emailId}", method = RequestMethod.GET)
	public ResponseEntity<List<UserMovieMap>> viewMovieHistoryForUser(@PathVariable("emailId") String emailId)
			throws ResourceNotFoundException {

		List<UserMovieMap> movieData = movieService.getMovieHistoryForUser(emailId);

		return new ResponseEntity<List<UserMovieMap>>(movieData, HttpStatus.OK);

	}

	/**
	 * Listing the rented (and not expired) movies with their expiry date
	 * 
	 * @param emailId
	 * @param resp
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = "/movies/rents/{emailId}", method = RequestMethod.GET)
	public ResponseEntity<List<UserMovieMap>> ListRentedMovies(@PathVariable("emailId") String emailId,
			HttpServletResponse resp) throws ResourceNotFoundException {

		List<UserMovieMap> movieData = movieService.getRentedMovieListForUser(emailId);

		if (movieData == null)
			throw new ResourceNotFoundException("No movie by this genre found");

		return new ResponseEntity<List<UserMovieMap>>(movieData, HttpStatus.OK);

	}

}
