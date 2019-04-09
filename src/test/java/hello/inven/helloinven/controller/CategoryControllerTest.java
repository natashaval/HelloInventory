package hello.inven.helloinven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.inven.helloinven.exceptionhandler.CustomRestExceptionHandler;
import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryServiceMock;

    @InjectMocks
    private CategoryController categoryController;

    private JacksonTester<Category> jsonCategory;
//    https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(CustomRestExceptionHandler.class)
                .build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void category2All_Found() throws Exception{
        List<Category> categories = Arrays.asList(
                new Category("Category Test", "category for unit test"),
                new Category("Category Test1", "category1 for unit test")
        );
        when(categoryServiceMock.getAllCategories()).thenReturn(categories);
        mockMvc.perform(get("/clerk/category2/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("Done")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name", is("Category Test")))
                .andExpect(jsonPath("$.data[0].description", is("category for unit test")))
                .andExpect(jsonPath("$.data[1].name", is("Category Test1")))
                .andExpect(jsonPath("$.data[1].description", is("category1 for unit test")));
        verify(categoryServiceMock, times(1)).getAllCategories();
    }

    @Test
    public void category2All_Empty() throws Exception{
        List<Category> categories = new ArrayList<>();
        when(categoryServiceMock.getAllCategories()).thenReturn(categories);
        mockMvc.perform(get("/clerk/category2/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("Done")))
                .andExpect(jsonPath("$.data", hasSize(0)));
        verify(categoryServiceMock, times(1)).getAllCategories();
    }

    @Test
    public void category() throws Exception {
//        https://stackoverflow.com/questions/48461165/spring-boot-controller-test-assert-static-html-response
        /*
        File categoryPage = new ClassPathResource("clerk/category.html").getFile();
        String categoryhtml = new String(Files.readAllBytes(categoryPage.toPath()));

        this.mockMvc.perform(get("/clerk/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(categoryhtml))
                .andDo(print());

        this.mockMvc.perform(get("/clerk/category")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("clerk/category")));
                */
        // https://blog.trifork.com/2012/12/11/properly-testing-spring-mvc-controllers/
        this.mockMvc.perform(get("/clerk/category"))
                .andExpect(status().isOk())
                .andExpect(view().name("clerk/category"));
    }

    @Test
    public void category2Details_Found() throws Exception{
        Category category = new Category("Category Test", "category for unit test");
        when(categoryServiceMock.getOneCategory(1)).thenReturn(category);
        this.mockMvc.perform(get("/clerk/category2/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status", is("Done")))
                .andExpect(jsonPath("$.data.name", is("Category Test")))
                .andExpect(jsonPath("$.data.description", is("category for unit test")));
        verify(categoryServiceMock, times(1)).getOneCategory(1);
    }

//    https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-rest-api/
    @Test
    public void category2Details_NotFound() throws Exception{
        when(categoryServiceMock.getOneCategory(1)).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(get("/clerk/category2/{id}",1)).andDo(print())
                .andExpect(status().isNotFound());
//                .andExpect(jsonPath("$.message",is("Category not found!")));
        verify(categoryServiceMock, times(1)).getOneCategory(1);
    }

//    https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
    @Test
    public void category2Add_Success() throws Exception{
        this.mockMvc.perform(post("/clerk/category2/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonCategory.write(new Category("Category Test", "category for unit test")).getJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("Saved")));
    }

    @Test
    public void category2Add_Failed() throws Exception{
        this.mockMvc.perform(post("/clerk/category2/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCategory.write(new Category(null, null)).getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void category2Delete_Success() throws Exception {
        Category category = new Category("Category Test", "category for unit test");
        when(categoryServiceMock.deleteCategory(1)).thenReturn(category);
//        doNothing().when(categoryServiceMock).deleteCategory(1);

        this.mockMvc.perform(delete("/clerk/category2/{id}",1))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("Deleted")))
        .andExpect(jsonPath("$.data", is("Category has been successfully deleted")));

        verify(categoryServiceMock, times(1)).deleteCategory(1);
    }

    @Test
    public void category2Delete_NotFound() throws Exception {
//        Category category = new Category("Category Test", "category for unit test");
        when(categoryServiceMock.deleteCategory(2)).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(delete("/clerk/category2/{id}", 2))
                .andExpect(status().isNotFound());

        verify(categoryServiceMock, times(1)).deleteCategory(2);
    }

    @Test
    public void category2Edit_Success()throws Exception {
        Category category = new Category("Category Test", "category for unit test");
//        when(categoryServiceMock.editCategory(category, 1)).thenReturn(category);
        this.mockMvc.perform(put("/clerk/category2/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonCategory.write(category).getJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Updated")))
                .andExpect(jsonPath("$.data.name",is("Category Test")))
                .andExpect(jsonPath("$.data.description", is("category for unit test")));
    }
}
