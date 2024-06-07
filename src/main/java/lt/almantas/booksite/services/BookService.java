package lt.almantas.booksite.services;

import lombok.RequiredArgsConstructor;
import lt.almantas.booksite.model.Entity.Book;
import lt.almantas.booksite.model.dto.BookCreateDTO;
import lt.almantas.booksite.model.dto.BookEditDTO;
import lt.almantas.booksite.repositories.BookRepository;
import lt.almantas.booksite.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public Iterable<Book> getBooks(Integer catID) {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String search) {
        return bookRepository.findAllByTitleContainingIgnoreCase(search);
    }

    public Book createBook(BookCreateDTO bookCreateDTO) {
        Book book = new Book(bookCreateDTO);
        categoryRepository.findById(bookCreateDTO.getCatID()).ifPresent(book::setCategory);
        return bookRepository.save(book);
    }

    public Book editBook(Long id, BookEditDTO bookEditDTO) {
        Optional<Book> findBook = bookRepository.findById(id);
        if (findBook.isEmpty()) {
            return null;
        }
        Book currentBook = findBook.get();
        currentBook.setTitle(bookEditDTO.getTitle());
        currentBook.setDescription(bookEditDTO.getDescription());
        currentBook.setIsbn(bookEditDTO.getIsbn());
        currentBook.setBase64(bookEditDTO.getBase64());
        currentBook.setPages(bookEditDTO.getPages());
        categoryRepository.findById(bookEditDTO.getCatID()).ifPresent(currentBook::setCategory);
        return bookRepository.save(currentBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
