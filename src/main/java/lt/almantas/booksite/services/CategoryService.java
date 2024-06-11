package lt.almantas.booksite.services;

import lombok.RequiredArgsConstructor;
import lt.almantas.booksite.model.Entity.Book;
import lt.almantas.booksite.model.Entity.BookCategory;
import lt.almantas.booksite.model.dto.BookCategoryCreateDTO;
import lt.almantas.booksite.model.dto.BookCategoryEditDTO;
import lt.almantas.booksite.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Iterable<BookCategory> getCategories() {
        return categoryRepository.findAll();
    }

    public BookCategory createCategory(BookCategoryCreateDTO bookCategoryCreateDTO) {
        BookCategory bookCategory = new BookCategory(bookCategoryCreateDTO);
        return categoryRepository.save(bookCategory);
    }

    public BookCategory editCategory(Long id, BookCategoryEditDTO bookCategoryEditDTO) {
        Optional<BookCategory> findCategory = categoryRepository.findById(id);
        if (findCategory.isEmpty()) {
            return null;
        }
        BookCategory currentBookCategory = findCategory.get();
        currentBookCategory.setName(bookCategoryEditDTO.getName());
        return categoryRepository.save(currentBookCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
