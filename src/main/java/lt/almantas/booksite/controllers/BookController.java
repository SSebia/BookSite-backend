package lt.almantas.booksite.controllers;

import lt.almantas.booksite.model.Entity.Book;
import lt.almantas.booksite.model.dto.BookCreateDTO;
import lt.almantas.booksite.model.dto.BookEditDTO;
import lt.almantas.booksite.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false, name = "catID") Integer catID) {
        List<Book> books = bookService.getBooks(catID);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title, @RequestParam(required = false, name = "page") Integer page) {
        List<Book> books = bookService.searchBooks(title);
        if (page != null && page > 0) {
            int booksPerPage = 8;
            int maxPages = (int)Math.ceil((double)books.size() / booksPerPage);
            int startIndex = (page - 1) * booksPerPage;
            int endIndex = Math.min(startIndex + booksPerPage, books.size());
            books = books.subList(startIndex, endIndex);
            return new ResponseEntity<>(books, (page == maxPages ? HttpStatus.ACCEPTED : HttpStatus.OK));
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        Book createdBook = bookService.createBook(bookCreateDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody BookEditDTO bookEditDTO) {
        Book editedBook = bookService.editBook(id, bookEditDTO);
        return (editedBook != null ? new ResponseEntity<>(editedBook, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
