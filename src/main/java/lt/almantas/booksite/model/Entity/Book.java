package lt.almantas.booksite.model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lt.almantas.booksite.model.dto.BookCreateDTO;

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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookComment> comments;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteBook> favoritedByUsers;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookRating> bookRatings;

    public Book(BookCreateDTO bookCreateDTO) {
        this.title = bookCreateDTO.getTitle();
        this.description = bookCreateDTO.getDescription();
        this.isbn = bookCreateDTO.getIsbn();
        this.base64 = bookCreateDTO.getBase64();
        this.pages = bookCreateDTO.getPages();
        this.category = bookCreateDTO.getCategory();
    }

    public Book() {

    }
}
