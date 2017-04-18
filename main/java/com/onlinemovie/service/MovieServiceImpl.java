package com.onlinemovie.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinemovie.Enum.UserEnum;
import com.onlinemovie.dao.MovieDao;
import com.onlinemovie.model.Movie;
import com.onlinemovie.model.User;
import com.onlinemovie.model.UserMovieMap;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private MovieDao movieDao;

	@Override
	public List<Movie> getAllMovies() {
		return movieDao.getAllMovies();

	}

	@Override
	public User getUser(String email) {
		return movieDao.getUser(email);
	}

	@Override
	public List<UserMovieMap> getMovieHistoryForUser(String emailId) {
		List<UserMovieMap> filteredResult = movieDao.getExpiredMoviesForUser(emailId,
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return filteredResult;
	}

	@Override
	public List<Movie> getMoviesByRent(float rentVal) {
		return movieDao.getMoviesByRent(rentVal);

	}

	@Override
	public List<Movie> getMoviesByYear(int yr) {
		return movieDao.getMoviesByYear(yr);
	}

	@Override
	public List<Movie> getMoviesByGenre(String genre) {
		return movieDao.getMoviesByGenre(genre);

	}

	public Movie getMoviesById(int muvId) {
		return movieDao.getMoviesById(muvId);

	}

	@Override
	public void rentMovie(String email, String muvId) {
		Calendar cal = Calendar.getInstance();

		User user = getUser(email);
		Movie muv = getMoviesById(Integer.parseInt(muvId));
		Float rentForMuv = (muv == null ? 0 : muv.getRent());
		if (user.getType().equalsIgnoreCase(UserEnum.REGISTERED.name())) {
			rentForMuv = rentForMuv - ((20 * rentForMuv) / 100);
			cal.add(Calendar.DATE, 7);
		} else {
			cal.add(Calendar.DATE, 3);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(Calendar.getInstance().getTime());
		String expireTime = sdf.format(cal.getTime());
		movieDao.saveMovieRent(user.getUserId(), muv.getMovieId(), currentTime, expireTime, rentForMuv);

	}

	@Override
	public List<UserMovieMap> getRentedMovieListForUser(String email) {

		User user = getUser(email);

		List<UserMovieMap> filteredResult = movieDao.getRentedMoviesForUser(user.getUserId(),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return filteredResult;
	}

	public MovieDao getMovieDao() {
		return movieDao;
	}

	public void setMovieDao(MovieDao movieDao) {
		this.movieDao = movieDao;
	}

}
