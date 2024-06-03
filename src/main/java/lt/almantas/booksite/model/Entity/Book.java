package lt.almantas.booksite.model.Entity;

import jakarta.persistence.*;
import lombok.Data;

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
    private int pages;

}
