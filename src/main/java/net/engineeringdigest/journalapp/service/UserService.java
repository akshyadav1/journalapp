package net.engineeringdigest.journalapp.service;

import lombok.extern.log4j.Log4j2;
import net.engineeringdigest.journalapp.enteties.User;
import net.engineeringdigest.journalapp.repo.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        log.info("Find user By Id - {}", id.toString());
        return userRepository.findById(id);
    }

    public User saveNewUser(User user) {

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singletonList("USER"));
        }
        if (user.getJournalEntries() == null || user.getJournalEntries().isEmpty()) {
            user.setJournalEntries(new ArrayList<>());
        }
        log.info("Save User - {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User saveUser(User user) {
        log.info("Save User - {}", user);
        return userRepository.save(user);
    }

    public void delete(User user) {
        log.info("Delete User - {}", user);
        userRepository.delete(user);
    }

    public void deleteById(ObjectId id) {
        log.info("Delete user By Id - {}", id.toString());
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        log.info("Find By UserName - {}", userName);
        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }
}
