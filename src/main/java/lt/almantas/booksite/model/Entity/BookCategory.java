package lt.almantas.booksite.model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lt.almantas.booksite.model.dto.BookCategoryCreateDTO;

import java.util.List;

@Entity
@Table(name = "\"book_category\"")
@Data
public class BookCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Book> books;

    public BookCategory(BookCategoryCreateDTO bookCategoryCreateDTO) {
        this.name = bookCategoryCreateDTO.getName();
    }

    public BookCategory() {

    }
}