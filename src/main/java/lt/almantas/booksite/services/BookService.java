package lt.almantas.booksite.services;

import lombok.RequiredArgsConstructor;
import lt.almantas.booksite.model.Entity.*;
import lt.almantas.booksite.model.dto.BookCommentCreationDTO;
import lt.almantas.booksite.model.dto.BookCreateDTO;
import lt.almantas.booksite.model.dto.BookEditDTO;
import lt.almantas.booksite.model.fdto.BookCommentsFDTO;
import lt.almantas.booksite.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final FavoriteBookRepository favoriteBookRepository;
    private final BookRatingRepository bookRatingRepository;

    public List<Book> getBooks(Integer catID) {
        if (catID != null) {
            return bookRepository.findAllByCategory_Id(catID);
        }
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

    public List<BookCommentsFDTO> getBookComments(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) { return null; }
        List<BookCommentsFDTO> bookCommentsFDTO = new ArrayList<>();
        for (BookComment bookComment : book.getComments()) {
            BookCommentsFDTO bookCommentsFDTO1 = new BookCommentsFDTO();
            bookCommentsFDTO1.setUsername(bookComment.getUser().getUsername());
            bookCommentsFDTO1.setComment(bookComment.getComment());
            bookCommentsFDTO.add(bookCommentsFDTO1);
        }
        return bookCommentsFDTO;
    }

    public BookComment addComment(Long bookID, BookCommentCreationDTO bookCommentCreationDTO, User user) {
        Book book = bookRepository.findById(bookID).orElse(null);
        if (book == null || user == null) { return null; }
        BookComment bookComment = new BookComment();
        bookComment.setBook(book);
        bookComment.setUser(user);
        bookComment.setComment(bookCommentCreationDTO.getComment());
        book.addComment(bookComment);
        bookRepository.save(book);
        return bookComment;
    }

    public void favoriteBook(Long bookID, User user) {
        Book book = bookRepository.findById(bookID).orElse(null);
        if (book == null || user == null) { return; }
        FavoriteBook favoriteBook = new FavoriteBook();
        favoriteBook.setBook(book);
        favoriteBook.setUser(user);
        favoriteBook.setUserid(user.getId());
        book.addFavorite(favoriteBook);
        bookRepository.save(book);
    }

    public void unfavoriteBook(Long bookID, User user) {
        Book book = bookRepository.findById(bookID).orElse(null);
        if (book == null || user == null) { return; }
        FavoriteBook favoriteBook = book.getFavoritedByUsers().stream()
                .filter(favBook -> favBook.getUserid() == user.getId())
                .findFirst()
                .orElse(null);
        if (favoriteBook != null) {
            book.getFavoritedByUsers().remove(favoriteBook);
            favoriteBookRepository.delete(favoriteBook);
        }
        bookRepository.save(book);
    }

    public void rateBook(Long bookID, User user, int rating) {
        Book book = bookRepository.findById(bookID).orElse(null);
        if (book == null || user == null) { return; }
        BookRating bookRating = book.getBookRatings().stream()
                .filter(bookRate -> bookRate.getUserid() == user.getId())
                .findFirst()
                .orElse(null);
        if (rating == 0) {
            if (bookRating != null) {
                book.getBookRatings().remove(bookRating);
                bookRatingRepository.delete(bookRating);
            }
            bookRepository.save(book);
            return;
        }
        if (bookRating != null) {
            bookRating.setRating(rating);
            bookRatingRepository.save(bookRating);
            return;
        }
        BookRating newBookRating = new BookRating();
        newBookRating.setBook(book);
        newBookRating.setUser(user);
        newBookRating.setUserid(user.getId());
        newBookRating.setRating(rating);
        book.addRating(newBookRating);
        bookRepository.save(book);
    }
}
