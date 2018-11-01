package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.ResponseAjax;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    ResponseAjax createCategory(Category category);
    ResponseAjax editCategory(Category category, Integer id);
    ResponseAjax deleteCategory(Integer id);

    List<Category> getAllCategories();
    Optional<Category> getOneCategory(Integer id);

}
