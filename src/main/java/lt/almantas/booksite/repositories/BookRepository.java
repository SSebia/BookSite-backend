package lt.almantas.booksite.repositories;

import lt.almantas.booksite.model.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}