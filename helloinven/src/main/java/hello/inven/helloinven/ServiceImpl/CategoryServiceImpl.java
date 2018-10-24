package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.repository.CategoryRepository;
import hello.inven.helloinven.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(){ return categoryRepository.findAll(); }

    @Override
    public Category save(Category category){
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        return categoryRepository.save(newCategory);
    }
}
