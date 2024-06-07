package lt.almantas.booksite.model.dto;

import lombok.Data;
import lt.almantas.booksite.model.Entity.BookCategory;

@Data
public class BookCreateDTO {
    private String title;
    private String description;
    private String isbn;
    private String base64;
    private int pages;
    private BookCategory category;
}