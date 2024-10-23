package net.engineeringdigest.journalapp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JournalAppApplicationTests {

	@ParameterizedTest
	@CsvSource({"AKSHAY"})
	void contextLoads(String a) {
		assertEquals("AKSHAY", a);
	}

}
