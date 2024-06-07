package lt.almantas.booksite.model.dto;

import lombok.Data;

@Data
public class BookEditDTO {
    private String title;
    private String description;
    private String isbn;
    private String base64;
    private int pages;
    private long catID;
}