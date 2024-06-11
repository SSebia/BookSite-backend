package lt.almantas.booksite.controllers;

import lt.almantas.booksite.model.Entity.BookCategory;
import lt.almantas.booksite.model.dto.BookCategoryCreateDTO;
import lt.almantas.booksite.model.dto.BookCategoryEditDTO;
import lt.almantas.booksite.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<BookCategory>> getCategories() {
        Iterable<BookCategory> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BookCategory> createCategory(@RequestBody BookCategoryCreateDTO bookCategoryCreateDTO) {
        BookCategory createdBookCategory = categoryService.createCategory(bookCategoryCreateDTO);
        return new ResponseEntity<>(createdBookCategory, HttpStatus.CREATED);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<BookCategory> editCategory(@PathVariable Long id, @RequestBody BookCategoryEditDTO bookCategoryEditDTO) {
        BookCategory editedBookCategory = categoryService.editCategory(id, bookCategoryEditDTO);
        return (editedBookCategory != null ? new ResponseEntity<>(editedBookCategory, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
