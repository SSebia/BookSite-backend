package lt.almantas.booksite.repositories;

import lt.almantas.booksite.model.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitleContainingIgnoreCase(String search);
}