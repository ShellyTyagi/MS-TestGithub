package com.onlinemovie.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.onlinemovie.model.Movie;
import com.onlinemovie.model.User;
import com.onlinemovie.model.UserMovieMap;

@Repository
public class MovieDao extends JdbcTemplateMovieDao {
	@Autowired
	public JdbcTemplate jdbcTemplate;

	private static final String SQL_SELECT_ALL_MOVIES = "Select * from Movie;";
	private static final String SQL_FILTER_MOVIES_BY_RENT = "Select * from Movie where rent =?";
	private static final String SQL_FILTER_MOVIES_BY_YEAR = "Select * from Movie where year(Date)=?";
	private static final String SQL_FILTER_MOVIES_BY_GENRE = "Select * from Movie where genre =?";
	private static final String SQL_FILTER_MOVIES_BY_ID = "Select * from Movie where muvId =?";
	private static final String SQL_GET_USER = "Select * from user where emailId=?";
	private static final String SQL_INSERT_USER_MOVIE = "insert into user_movie_mapping(userId, muvId, RentedOn, ExpireDate,rentApplied)"
			+ " values(:userId,:muvId,:rentedOn,:expireDate,:rent)";
	private static final String SQL_GET_MOVIES_HISTORY_FOR_USER = "Select * from user_movie_mapping where userid =? and expiringOn < ?";

	private static final String SQL_GET_RENTED_MOVIES_FOR_USER = "Select * from user_movie_mapping where userid =? and expiringOn > ?";

	public List<Movie> getAllMovies() {

		List<Movie> movies = jdbcTemplate.query(SQL_SELECT_ALL_MOVIES, new RowMapper<Movie>() {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getMovie(rs);
			}
		}, new Object[] {});
		return movies;

	}

	public User getUser(String email) {

		return jdbcTemplate.queryForObject(SQL_GET_USER, new ParameterizedRowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User usr = new User();
				usr.setUserId(rs.getInt(1));
				usr.setName(rs.getString(2));
				usr.setType(rs.getString(3));
				usr.setCard(rs.getString(4));
				usr.setEmailId(rs.getString(5));
				return usr;
			}
		}, email);
	}

	public void saveMovieRent(int usrId, int muvId, String currentTime, String expireTime, float rent) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", usrId);
		params.put("muvId", muvId);
		params.put("rentedOn", currentTime);
		params.put("expireDate", expireTime);
		params.put("rentApplied", rent);

		jdbcTemplate.update(SQL_INSERT_USER_MOVIE, params);

	}

	public List<UserMovieMap> getExpiredMoviesForUser(String userId, String currentDate) {

		List<UserMovieMap> movies = jdbcTemplate.query(SQL_GET_MOVIES_HISTORY_FOR_USER, new RowMapper<UserMovieMap>() {
			public UserMovieMap mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserMovieMap umm = new UserMovieMap();
				umm.setMovieId(rs.getInt(1));
				umm.setUserId(rs.getInt(2));
				umm.setUserRent(rs.getFloat(3));
				umm.setRentedOn(rs.getDate(4));
				umm.setExpiringOn(rs.getDate(5));
				return umm;
			}
		}, new Object[] { userId, currentDate });

		return movies;

	}

	public List<Movie> getMoviesByRent(float rentVal) {
		List<Movie> movies = jdbcTemplate.query(SQL_FILTER_MOVIES_BY_RENT, new RowMapper<Movie>() {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getMovie(rs);
			}
		}, new Object[] { rentVal });
		return movies;
	}

	public List<Movie> getMoviesByYear(int yr) {
		List<Movie> movies = jdbcTemplate.query(SQL_FILTER_MOVIES_BY_YEAR, new RowMapper<Movie>() {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getMovie(rs);
			}
		}, new Object[] { yr });
		return movies;
	}

	public List<Movie> getMoviesByGenre(String genre) {
		List<Movie> movies = jdbcTemplate.query(SQL_FILTER_MOVIES_BY_GENRE, new RowMapper<Movie>() {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getMovie(rs);
			}
		}, new Object[] { genre });
		return movies;
	}

	private Movie getMovie(ResultSet rs) throws SQLException {
		Movie muv = new Movie();
		muv.setMovieId(rs.getInt(1));
		muv.setMovieName(rs.getString(2));
		muv.setGenre(rs.getString(3));
		muv.setRent(rs.getFloat(4));
		muv.setYear(rs.getDate(5));
		return muv;
	}

	public Movie getMoviesById(int muvId) {

		return jdbcTemplate.queryForObject(SQL_FILTER_MOVIES_BY_ID, new ParameterizedRowMapper<Movie>() {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getMovie(rs);
			}
		}, muvId);

	}

	public List<UserMovieMap> getRentedMoviesForUser(int userId, String currentDate) {
		List<UserMovieMap> movies = jdbcTemplate.query(SQL_GET_RENTED_MOVIES_FOR_USER, new RowMapper<UserMovieMap>() {
			public UserMovieMap mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserMovieMap umm = new UserMovieMap();
				umm.setMovieId(rs.getInt(1));
				umm.setUserId(rs.getInt(2));
				umm.setUserRent(rs.getFloat(3));
				umm.setRentedOn(rs.getDate(4));
				umm.setExpiringOn(rs.getDate(5));
				return umm;
			}
		}, new Object[] { userId, currentDate });
		return movies;
	}

}
