package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
}
