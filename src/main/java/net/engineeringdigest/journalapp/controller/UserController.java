package net.engineeringdigest.journalapp.controller;

import lombok.extern.log4j.Log4j2;
import net.engineeringdigest.journalapp.enteties.User;
import net.engineeringdigest.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping
    public ResponseEntity<String> deleteByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUserNameAndPassword(@RequestBody User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User old = userService.findByUserName(authentication.getName());
        if (old != null) {
            old.setUserName(user.getUserName());
            old.setPassword(user.getPassword());
            old.setJournalEntries(user.getJournalEntries());
            userService.saveNewUser(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}