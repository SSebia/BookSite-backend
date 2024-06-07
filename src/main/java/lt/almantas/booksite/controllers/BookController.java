package lt.almantas.booksite.controllers;

import lt.almantas.booksite.model.Entity.Book;
import lt.almantas.booksite.model.dto.BookCreateDTO;
import lt.almantas.booksite.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        Book createdBook = bookService.createBook(bookCreateDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

}
