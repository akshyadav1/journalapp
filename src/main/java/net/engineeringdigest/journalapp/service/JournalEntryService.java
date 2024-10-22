package net.engineeringdigest.journalapp.service;

import lombok.extern.log4j.Log4j2;
import net.engineeringdigest.journalapp.enteties.JournalEntry;
import net.engineeringdigest.journalapp.enteties.User;
import net.engineeringdigest.journalapp.repo.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    public List<JournalEntry> findAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public JournalEntry saveNewEntry(String userName, JournalEntry journalEntry) {

        journalEntry.setDate(journalEntry.getDate() == null ? new Date() : journalEntry.getDate());
        log.info("Save JE - {} for User - {}", journalEntry, userName);

        JournalEntry je = journalEntryRepository.save(journalEntry);

        User user = userService.findByUserName(userName);
        log.info("User - {}", user);
        if (user.getJournalEntries() == null) {
            user.setJournalEntries(new ArrayList<>());
        }
        user.getJournalEntries().add(je);
        userService.saveUser(user);
        return journalEntry;
    }

    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public boolean deleteByIdAndUserName(ObjectId id, String userName) {

        log.info("Delete JE By id - {} and userName - {}", id, userName);
        User user = userService.findByUserName(userName);
        boolean removed = user.getJournalEntries().removeIf(e -> e.getId().equals(id));
        if (removed) {
            journalEntryRepository.deleteById(id);
            userService.saveUser(user);
        }
        return removed;
    }


    public List<JournalEntry> findByUserName(String userName) {

        User user = userService.findByUserName(userName);
        return user.getJournalEntries();

    }
}
