package com.ragul.book.controller;

import com.ragul.book.model.entity.Author;
import com.ragul.book.model.entity.Book;
import com.ragul.book.model.payload.BookRequest;
import com.ragul.book.repository.AuthorRepository;
import com.ragul.book.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/book")
public class BookController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Book>> get() {
        List<Book> books = this.bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Optional<Book> books = this.bookRepository.findById(id);
        return new ResponseEntity<>(books.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> add(@RequestBody BookRequest bookReq) {
        Optional<Author> author = authorRepository.findById(bookReq.getAuthorId());
        if (author.isPresent()) {
            Book book = new Book();
            BeanUtils.copyProperties(bookReq, book);
            book.setAuthor(author.get());
            bookRepository.save(book);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Book(), HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Book> edit(@RequestBody Book bookReq) {
        Optional<Author> author = authorRepository.findById(bookReq.getAuthor().getId());
        if (author.isPresent()) {
            bookRepository.save(bookReq);
            return new ResponseEntity<>(bookReq, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Book(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> edit(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
