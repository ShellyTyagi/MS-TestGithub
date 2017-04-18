package com.onlinemovie.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.onlinemovie.Enum.GenreEnum;
import com.onlinemovie.dao.MovieDao;
import com.onlinemovie.model.Movie;
import com.onlinemovie.model.User;
import com.onlinemovie.service.MovieServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "classpath:dispatcher-servlet.xml" })
public class OnlineMovieTest {

	@InjectMocks
	MovieServiceImpl movieServiceImpl = new MovieServiceImpl();

	@Mock
	private MovieDao muvDao;

	@Before
	public void doSetup() {
		movieServiceImpl.setMovieDao(muvDao);
	}

	@Test
	public void testGetUser() {

		String email = "ahana@gmail.com";
		User testUser = new User();
		testUser.setUserId(1);
		testUser.setName("Ahana");
		testUser.setType("Registered");
		testUser.setCard("HDFC");
		testUser.setEmailId("ahana@gmail.com");

		when(muvDao.getUser(email)).thenReturn(testUser);
		User user = movieServiceImpl.getUser("ahana@gmail.com");
		assertEquals("User is null", 1, user.getUserId());

	}

	@Test
	public void rentMovie() {
		String email = "ahana@gmail.com";
		User testUser = new User();
		testUser.setUserId(1);
		testUser.setName("Ahana");
		testUser.setType("Registered");
		testUser.setCard("HDFC");
		testUser.setEmailId("ahana@gmail.com");

		int muvId = 1;
		Movie muv = new Movie();
		muv.setMovieId(1);
		muv.setMovieName("Conjuring");
		muv.setGenre(GenreEnum.HORROR.name());
		muv.setRent(50);
		muv.setYear(new Date((Calendar.getInstance().getTimeInMillis())));

		when(muvDao.getUser(email)).thenReturn(testUser);
		when(muvDao.getMoviesById(muvId)).thenReturn(muv);

		movieServiceImpl.rentMovie("ahana@gmail.com", "1");

	}

}
