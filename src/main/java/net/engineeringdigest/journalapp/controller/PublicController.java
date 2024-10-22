package net.engineeringdigest.journalapp.controller;

import lombok.extern.log4j.Log4j2;
import net.engineeringdigest.journalapp.enteties.User;
import net.engineeringdigest.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<User> create(@RequestBody User user) {
        log.info("create-user");
        user = userService.saveNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
