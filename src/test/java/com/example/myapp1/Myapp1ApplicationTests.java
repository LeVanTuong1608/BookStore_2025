package com.example.myapp1;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@EntityScan("com.example.myapp1.model")
@ActiveProfiles("test") // Sử dụng profile test
class Myapp1ApplicationTests {

	// @Test
	// void contextLoads() {
	// }
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);

	}

}
