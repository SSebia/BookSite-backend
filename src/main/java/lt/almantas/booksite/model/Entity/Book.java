package lt.almantas.booksite.model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "\"book\"")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private String isbn;
    private String base64;
    private int pages;
    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookComment> comments;

}
