package lt.almantas.booksite.services;

import lombok.RequiredArgsConstructor;
import lt.almantas.booksite.model.Entity.Book;
import lt.almantas.booksite.model.dto.BookCreateDTO;
import lt.almantas.booksite.repositories.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Book createBook(BookCreateDTO bookCreateDTO) {
        Book book = new Book(bookCreateDTO);
        return bookRepository.save(book);
    }
}
