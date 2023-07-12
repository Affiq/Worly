package com.example.worly.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.worly.user.User;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

	@Autowired
	UserRepository repo;


	@Test
	public void testFindByEmail() {
		repo.save(new User("Jojo@jojo.com", "Jojo","Admin", "Jojo", "Jojo"));
		User user= repo.findByEmail("Jojo@jojo.com").get();
		String test = user.getEmail();


		assertEquals(test,"Jojo@jojo.com");
		repo.delete(user);
	}

}

