package lt.almantas.booksite.model.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"book_ratings\"")
@Data
public class BookRating {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int rating;
}