package net.engineeringdigest.journalapp.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

//    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 1, 2, 1, 2, 2, 2})
    public void test(int a, int b, int c) {
        assertEquals(4, a + b + c);
    }

    @ParameterizedTest
    @CsvSource({"Akshay", "Madan", "Ram", "Karan"})
    public void testFindByUserName(String name) {
        assertNotNull(userService.findByUserName(name), "Faild for " + name);
    }

}
