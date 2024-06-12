package lt.almantas.booksite.repositories;

import lt.almantas.booksite.model.Entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRatingRepository extends JpaRepository<BookRating, Long> {
}