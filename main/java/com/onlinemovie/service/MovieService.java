package com.onlinemovie.service;

import java.util.List;

import com.onlinemovie.model.Movie;
import com.onlinemovie.model.User;
import com.onlinemovie.model.UserMovieMap;

public interface MovieService {

	List<Movie> getAllMovies();

	User getUser(String email);

	List<UserMovieMap> getMovieHistoryForUser(String emailId);

	List<Movie> getMoviesByRent(float rentVal);

	List<Movie> getMoviesByYear(int yr);

	List<Movie> getMoviesByGenre(String genre);

	void rentMovie(String email, String muvId);

	List<UserMovieMap> getRentedMovieListForUser(String email);
}
