package lt.almantas.booksite.model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lt.almantas.booksite.model.dto.BookCreateDTO;

import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private BookCategory category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookComment> comments;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<FavoriteBook> favoritedByUsers;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookRating> bookRatings;

    public Book(BookCreateDTO bookCreateDTO) {
        this.title = bookCreateDTO.getTitle();
        this.description = bookCreateDTO.getDescription();
        this.isbn = bookCreateDTO.getIsbn();
        this.base64 = bookCreateDTO.getBase64();
        this.pages = bookCreateDTO.getPages();
        this.comments = new ArrayList<>();
        this.favoritedByUsers = new ArrayList<>();
        this.bookRatings = new ArrayList<>();
    }

    public Book() {

    }
}
