package com.onlinemovie.model;

import java.util.Date;

public class UserMovieMap {
	private int movieId;
	private int userId;
	private float userRent;
	private Date rentedOn;
	private Date expiringOn;

	public Date getRentedOn() {
		return rentedOn;
	}

	public void setRentedOn(Date rentedOn) {
		this.rentedOn = rentedOn;
	}

	public Date getExpiringOn() {
		return expiringOn;
	}

	public void setExpiringOn(Date expiringOn) {
		this.expiringOn = expiringOn;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getUserRent() {
		return userRent;
	}

	public void setUserRent(float userRent) {
		this.userRent = userRent;
	}

}
