package com.onlinemovie.Enum;

public enum GenreEnum {
	SCIFI("SCIFI"), HORROR("HORROR"), COMEDY("COMEDY");

	private String name;

	private GenreEnum(String name) {
		this.name = name;
	}

	public String getGenreEnum() {
		return this.name;
	}
}
