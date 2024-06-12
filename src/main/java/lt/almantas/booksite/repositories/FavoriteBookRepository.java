package lt.almantas.booksite.repositories;

import lt.almantas.booksite.model.Entity.FavoriteBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Long> {
}