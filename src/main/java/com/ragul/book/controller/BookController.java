package com.ragul.book.controller;

import com.ragul.book.model.entity.Author;
import com.ragul.book.model.entity.Book;
import com.ragul.book.model.payload.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Book>>> get() {
        List<Book> books = this.bookRepository.findAll();
        return new ResponseEntity<>(new ApiResponse<>(books), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getById(@PathVariable Long id) {
        Optional<Book> books = this.bookRepository.findById(id);
        if (books.isPresent())
            return new ResponseEntity<>(new ApiResponse<>(books.get()), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Book id: " + id + " not found"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> add(@RequestBody BookRequest bookReq) {
        Optional<Author> author = authorRepository.findById(bookReq.getAuthorId());
        if (author.isPresent()) {
            Book book = new Book();
            BeanUtils.copyProperties(bookReq, book);
            book.setAuthor(author.get());
            bookRepository.save(book);
            return new ResponseEntity<>(new ApiResponse<>(book), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Author id: " + bookReq.getAuthorId() + " not found"), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Book>> edit(@RequestBody Book bookReq) {
        Optional<Author> author = authorRepository.findById(bookReq.getAuthor().getId());
        Optional<Book> book = bookRepository.findById(bookReq.getId());
        if (!author.isPresent()) {

            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Book id: " + bookReq.getId() + " not found"), HttpStatus.OK);
        }
        if (!book.isPresent())
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Author id: " + bookReq.getAuthor().getId() + " not found"), HttpStatus.OK);

        Book book1 = bookRepository.save(bookReq);
        return new ResponseEntity<>(new ApiResponse<>(book1), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> edit(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return new ResponseEntity<>(new ApiResponse<>(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND, "Book id: " + id + " not found"), HttpStatus.OK);
    }
}
