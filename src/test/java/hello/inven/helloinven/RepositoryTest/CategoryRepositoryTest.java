package hello.inven.helloinven.RepositoryTest;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

//    private Category category;
//
//    @Before
//    public void setUp(){
//        categoryRepository.deleteAll();
//        category = new Category("Stationery" ,"Peralatan tulis", null);
//    }
//
//    @Test
//    public void categorySave(){
//        categoryRepository.save(category);
//        Assert.assertEquals(1, categoryRepository.count());
//    }


}
