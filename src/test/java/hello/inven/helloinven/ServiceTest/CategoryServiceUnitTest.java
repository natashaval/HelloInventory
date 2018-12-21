package hello.inven.helloinven.ServiceTest;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.repository.CategoryRepository;

import static org.assertj.core.api.Assertions.*;

import hello.inven.helloinven.service.CategoryService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// AutoConfigureTestDatabase digunakan agar tidak menyambung ke MySQL
// Jika tidak ada maka keluar: 'Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase'
public class CategoryServiceUnitTest {
//    https://ajayiyengar.com/2017/07/08/testing-jpa-entities-in-a-spring-boot-application/
//    https://www.baeldung.com/spring-boot-testing

    @InjectMocks
    private CategoryService categoryService;

    @Autowired
    private TestEntityManager entityManager;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(categoryRepository);
    }

    @Before
    public void setUp() {
        category = new Category("Stationery", "Peralatan tulis", null);
    }

    @Test
    public void createCategory() {
        Category savedCategoryData = this.entityManager.persistAndFlush(category);
        assertThat(savedCategoryData.getName()).isEqualTo("Stationery");
    }

    @Test
    public void saveCategoryAndFindById() {
        Category categorySave = categoryRepository.save(category);
//        assertThat(categoryRepository.findOne((Category) category)).isEqualTo(category);

    }

    @Test
    public void getById_Found() {
        Category parent = new Category("haha", "asdfa", null);
        when(categoryRepository.findById(1)).thenReturn(null);
        categoryService.getOneCategory(1);
    }

//    @Test(expected = None)
//    public void getById_NotFound(){
//    }


}
