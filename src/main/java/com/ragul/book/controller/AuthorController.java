package com.ragul.book.controller;

import com.ragul.book.model.entity.Author;
import com.ragul.book.model.payload.ApiResponse;
import com.ragul.book.repository.AuthorRepository;
import io.swagger.annotations.Api;
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
    public ResponseEntity<ApiResponse<List<Author>>> get() {
        List<Author> authors = this.authorRepository.findAll();
        return new ResponseEntity<>(new ApiResponse<>(authors), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Author>> add(@RequestBody Author authorRes) {
        Author author = this.authorRepository.save(authorRes);
        return new ResponseEntity<>(new ApiResponse<>(author), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Author>> getById(@PathVariable Long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        if (author.isPresent()) {
            return new ResponseEntity<>(new ApiResponse<>(author.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Author id: " + id + " not found"), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Author>> edit(@RequestBody Author author) {
        Optional<Author> authorOptional = this.authorRepository.findById(author.getId());
        if (!authorOptional.isPresent()) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Author id: " + author.getId() + " not found"), HttpStatus.OK);
        }
        authorRepository.save(author);
        return new ResponseEntity<>(new ApiResponse<>(author), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> edit(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.delete(author.get());
            return new ResponseEntity<>(new ApiResponse<>(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Author id: " + id + " not found"), HttpStatus.OK);
    }
}
