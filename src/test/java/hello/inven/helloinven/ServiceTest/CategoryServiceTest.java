package hello.inven.helloinven.ServiceTest;

import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.repository.CategoryRepository;
import hello.inven.helloinven.serviceimpl.CategoryServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepositoryMock;

    @InjectMocks
    CategoryServiceImpl categoryServiceMock;

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
    public void testFindById_Found(){
        when(categoryRepositoryMock.findById(CATEGORY_ID)).thenReturn(categoryOpt);
        Category categoryFound = categoryServiceMock.getOneCategory(1);
//        Assert.assertEquals("Kategori", categoryServiceMock.getOneCategory(1).getName());
        Assert.assertEquals("Category Test", categoryFound.getName());
    }

    @Test(expected = NotFoundException.class)
    public void testFindById_NotFound(){
        when(categoryRepositoryMock.findById(1)).thenReturn(categoryOpt);
        Category categoryNotFound = categoryServiceMock.getOneCategory(2);
    }

//    https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/tutorial-part-seven/src/test/java/net/petrikainulainen/spring/datajpa/service/RepositoryPersonServiceTest.java
    @Test
    public void testGetAllCategories_Found(){
        Category category = new Category("Category Test", "category for test all categories");
        List<Category> categoryList = Collections.singletonList(category);
        when(categoryRepositoryMock.findAll()).thenReturn(categoryList);

        List<Category> returned = categoryServiceMock.getAllCategories();
        Assert.assertEquals(returned, categoryList);
        Assert.assertEquals(returned.size(), 1);
    }

//    https://www.developer.com/java/other/article.php/10936_3882311_2/Mockito-Java-Unit-Testing-with-Mock-Objects.htm
    @Test
    public void testGetAllCategories_NotFound(){
        List<Category> categoryList = new ArrayList<>();
        when(categoryRepositoryMock.findAll()).thenReturn(categoryList);
        List<Category> returned = categoryServiceMock.getAllCategories();
        Assert.assertEquals(returned.size(), 0);
    }

    @Test
    public void create(){
        when(categoryRepositoryMock.save(any(Category.class))).thenReturn(categoryOpt.get());
        Category persisted = new Category(CATEGORY_NAME, CATEGORY_DESCRIPTION);
        Category returned = categoryServiceMock.createCategory(persisted);
        Assert.assertEquals(categoryOpt.get(), returned);
    }

    @Test
    public void update(){
        Category updated = new Category(CATEGORY_NAME_UPDATED, CATEGORY_DESCRIPTION_UPDATED);
        when(categoryRepositoryMock.findById(CATEGORY_ID)).thenReturn(categoryOpt);

        Category returned = categoryServiceMock.editCategory(updated, CATEGORY_ID);
//        Assert.assertEquals(updated, returned);
        verify(categoryRepositoryMock, times(1)).findById(CATEGORY_ID);
        Assert.assertEquals(updated.getName(), returned.getName());

    }




























}
