package com.onlinemovie.Enum;

public enum UserEnum {
	REGULAR("REGULAR"), REGISTERED("REGISTERED");

	private String name;

	private UserEnum(String name) {
		this.name = name;
	}

	public String getGenreEnum() {
		return this.name;
	}
}
