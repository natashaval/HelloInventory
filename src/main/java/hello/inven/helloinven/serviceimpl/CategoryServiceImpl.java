package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.repository.CategoryRepository;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//https://javabeginnerstutorial.com/spring-boot/making-spring-boot-thymeleaf-crud-application/

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){ return categoryRepository.findAll(); }

    @Override
    public Category getOneCategory(Integer id){
        return categoryRepository.findById(id).get();
    }

    @Override
    public ResponseAjax createCategory(Category category){
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        return new ResponseAjax("Saved", categoryRepository.save(newCategory));
    }

    @Override
    public ResponseAjax editCategory(Category categoryRequest, Integer id){
        // https://stackoverflow.com/questions/49316751/spring-data-jpa-findone-change-to-optional-how-to-use-this
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null){
            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            categoryRepository.save(category);
            return new ResponseAjax("Edited",category);
        }
        return new ResponseAjax("Failed", "Category edit failed!");
    }

    @Override
    public ResponseAjax deleteCategory(Integer id){
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.deleteById(id);
            return new ResponseAjax("Deleted", "Category has been successfully deleted");
        }
        return new ResponseAjax("Failed", "Category failed to be deleted!");
    }

}
