package com.ragul.book.controller;

import com.ragul.book.model.entity.Author;
import com.ragul.book.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/author")
public class AuthorController {
    @Autowired
    AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Author>> get() {
        List<Author> authors = this.authorRepository.findAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> add(@RequestBody Author authorRes) {
        Author author = this.authorRepository.save(authorRes);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        return new ResponseEntity<>(author.get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Author> edit(@RequestBody Author author) {
        authorRepository.save(author);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> edit(@PathVariable Long id) {
        Optional<Author> book = authorRepository.findById(id);
        if (book.isPresent()) {
            authorRepository.delete(book.get());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
