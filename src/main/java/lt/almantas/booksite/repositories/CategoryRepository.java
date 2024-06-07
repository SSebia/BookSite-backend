package lt.almantas.booksite.repositories;

import lt.almantas.booksite.model.Entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<BookCategory, Long> {
}
