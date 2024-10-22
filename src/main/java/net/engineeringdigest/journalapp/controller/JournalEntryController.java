package net.engineeringdigest.journalapp.controller;

import lombok.extern.log4j.Log4j2;
import net.engineeringdigest.journalapp.enteties.JournalEntry;
import net.engineeringdigest.journalapp.enteties.User;
import net.engineeringdigest.journalapp.service.JournalEntryService;
import net.engineeringdigest.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (!journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(e -> e.getId().equals(id)).collect(Collectors.toList());
        if (!journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries.get(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        journalEntry = journalEntryService.saveNewEntry(authentication.getName(), journalEntry);
        return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(e -> e.getId().equals(id)).collect(Collectors.toList());
        if (!journalEntries.isEmpty()) {
            Optional<JournalEntry> je = journalEntryService.findById(id);
            if (je.isPresent()) {
                JournalEntry old = je.get();
                old.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : old.getTitle());
                old.setContent(journalEntry.getContent() != null && !journalEntry.getContent().equals("") ? journalEntry.getContent() : old.getContent());
                old = journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<HttpStatus> deleteByIDAndUsername(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean removed = journalEntryService.deleteByIdAndUserName(id, authentication.getName());
        if (removed) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
