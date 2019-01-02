package hello.inven.helloinven.ServiceTest;

import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.repository.CategoryRepository;
import hello.inven.helloinven.service.CategoryService;
import hello.inven.helloinven.serviceimpl.CategoryServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepositoryMock;

    @InjectMocks
    CategoryService categoryServiceMock = new CategoryServiceImpl();

    private static final String CATEGORY_NAME = "Category Test";
    private static final String CATEGORY_NAME_UPDATED = "Category Test Updated";
    private static final String CATEGORY_DESCRIPTION = "Category for unit test";
    private static final String CATEGORY_DESCRIPTION_UPDATED = "Category for unit test Updated";
    private static final Integer CATEGORY_ID = 1;

    private Optional<Category> categoryOpt;

    @Before
    public void setUp(){
        System.out.println("Before Category Test");
        categoryOpt = Optional.of(new Category(CATEGORY_NAME, CATEGORY_DESCRIPTION));
//        when(categoryRepositoryMock.findById(1)).thenReturn(categoryOpt);
    }

//    @After
//    public void after(){
//        verifyNoMoreInteractions(categoryRepositoryMock);
//    }

//    https://www.baeldung.com/spring-boot-testing
    @Test
    public void getOneCategory_Found(){
        when(categoryRepositoryMock.findById(CATEGORY_ID)).thenReturn(categoryOpt);
        Category categoryFound = categoryServiceMock.getOneCategory(1);
//        Assert.assertEquals("Kategori", categoryServiceMock.getOneCategory(1).getName());
        Assert.assertEquals("Category Test", categoryFound.getName());
    }

    @Test(expected = NotFoundException.class)
    public void getOneCategory_NotFound(){
//        when(categoryRepositoryMock.findById(1)).thenReturn(categoryOpt);
        Category categoryNotFound = categoryServiceMock.getOneCategory(2);
//        Assert.assertNotEquals(categoryNotFound, categoryOpt.get());
    }

//    https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/test/java/net/petrikainulainen/spring/datajpa/service/RepositoryPersonServiceTest.java
    @Test
    public void getAllCategories_Found(){
        Category category = new Category("Category Test", "category for test all categories");
        Category category1 = new Category("Category Test2", "category for test all categories2");
        List<Category> categoryList = Arrays.asList(category, category1);
        when(categoryRepositoryMock.findAll()).thenReturn(categoryList);
        List<Category> returned = categoryServiceMock.getAllCategories();
        Assert.assertEquals(categoryList, returned);
        Assert.assertEquals(2, returned.size());
    }

//    https://www.developer.com/java/other/article.php/10936_3882311_2/Mockito-Java-Unit-Testing-with-Mock-Objects.htm
    @Test
    public void getAllCategories_NotFound(){
        List<Category> categoryList = new ArrayList<>();
        when(categoryRepositoryMock.findAll()).thenReturn(categoryList);
        List<Category> returned = categoryServiceMock.getAllCategories();
        Assert.assertEquals(returned.size(), 0);
    }

    @Test
    public void createCategory(){
        when(categoryRepositoryMock.save(any(Category.class))).thenReturn(categoryOpt.get());
        Category persisted = new Category(CATEGORY_NAME, CATEGORY_DESCRIPTION);
        Category returned = categoryServiceMock.createCategory(persisted);
        Assert.assertEquals(categoryOpt.get(), returned);
    }

//    https://github.com/kmb385/ToThoughtDataLayer/blob/master/src/test/java/org/tothought/repositories/PostRepositoryTest.java
    @Test
    public void editCategory(){
        /*
        Category updated = new Category(CATEGORY_NAME_UPDATED, CATEGORY_DESCRIPTION_UPDATED);
        when(categoryRepositoryMock.findById(anyInt())).thenReturn(categoryOpt);

        Category returned = categoryServiceMock.editCategory(updated, CATEGORY_ID);
        System.out.println(categoryRepositoryMock.findById(CATEGORY_ID));
        System.out.println(returned);
        */

//        Assert.assertEquals(updated, returned);
//        verify(categoryRepositoryMock, times(1)).findById(CATEGORY_ID);
//        Assert.assertEquals(updated.getName(), returned.getName());
        when(categoryRepositoryMock.findById(CATEGORY_ID)).thenReturn(categoryOpt);

        Category categoryBefore = categoryRepositoryMock.findById(CATEGORY_ID).get();
        Category updated = new Category(CATEGORY_NAME_UPDATED, CATEGORY_DESCRIPTION_UPDATED);
        categoryServiceMock.editCategory(updated, CATEGORY_ID);
        Category categoryAfter = categoryRepositoryMock.findById(CATEGORY_ID).get();
        Assert.assertNotNull(categoryAfter);
        Assert.assertEquals(CATEGORY_NAME_UPDATED, categoryAfter.getName());
    }

//    https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/test/java/net/petrikainulainen/spring/datajpa/service/RepositoryPersonServiceTest.java
    @Test
    public void deleteCategory_Found(){
        when(categoryRepositoryMock.findById(CATEGORY_ID)).thenReturn(categoryOpt);
        Category categoryAfter = categoryServiceMock.deleteCategory(CATEGORY_ID);
        verify(categoryRepositoryMock, times(1)).findById(CATEGORY_ID);
        verify(categoryRepositoryMock, times(1)).deleteById(CATEGORY_ID);
        Assert.assertEquals(categoryOpt.get(), categoryAfter);
    }

    @Test(expected = NotFoundException.class)
    public void deleteCategory_NotFound(){
        when(categoryRepositoryMock.findById(CATEGORY_ID)).thenReturn(categoryOpt);
        Category categoryAfter = categoryServiceMock.deleteCategory(2);
    }





























}
