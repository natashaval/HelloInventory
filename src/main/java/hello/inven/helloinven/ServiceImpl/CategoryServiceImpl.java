package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.Item;
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
    public Optional<Category> getOneCategory(Integer id){
        return categoryRepository.findById(id);
    }

    @Override
    public ResponseAjax createCategory(Category category){
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        return new ResponseAjax("Created", categoryRepository.save(newCategory));
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
        return new ResponseAjax("Failed", "Category is failed to edit");
    }

    @Override
    public ResponseAjax deleteCategory(Integer id){
        ResponseAjax responseAjax = new ResponseAjax(null, null);
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.deleteById(id);

            List<Item> itemList = category.getItems();
            for (Item item : itemList){
                item.setCategory(null);
            }
            responseAjax.setStatus("Deleted");
            responseAjax.setData("Category has been successfully deleted");
        }
        else {
            responseAjax.setStatus("Failed");
            responseAjax.setData("Category failed to be deleted!");
        }
        return responseAjax;
    }

}
