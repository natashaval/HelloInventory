package hello.inven.helloinven.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.inven.helloinven.controller.ItemController;
import hello.inven.helloinven.exceptionhandler.CustomRestExceptionHandler;
import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.service.CategoryService;
import hello.inven.helloinven.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemControllerTest {
    @Mock
    CategoryService categoryServiceMock;

    @Mock
    ItemService itemServiceMock;

    @InjectMocks
    ItemController itemControllerMock;

    private JacksonTester<Item> jsonItem;
    private MockMvc mockMvc;

    private static final Category category = new Category("Category Test", "category for unit test");
    private static final Item item1 = new Item(1L, "Item test1", "", Item.ItemType.ASSET, 1, 1.0, 1.0, 1.0, 1.0, 1.0, category);
    private static final Item item2 = new Item(2L, "Item test2", "", Item.ItemType.ASSET, 2, 2.0, 2.0, 2.0, 2.0, 2.0, category);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemControllerMock)
                .setControllerAdvice(CustomRestExceptionHandler.class)
                .build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void itemList() throws Exception{
        this.mockMvc.perform(get("/clerk/item/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("clerk/item-list"))
                .andExpect(forwardedUrl("clerk/item-list"));
    }
// https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
    @Test
    public void itemAdd() throws Exception{
        List<Category> categoryList = Arrays.asList(
                new Category("Category Test", "category for unit test"),
                new Category("Category1 Test", "category1 for unit test")
        );
        when(categoryServiceMock.getAllCategories()).thenReturn(categoryList);
        this.mockMvc.perform(get("/clerk/item/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("clerk/item-add"))
                .andExpect(forwardedUrl("clerk/item-add"))
                .andExpect(model().attribute("categoryList", hasSize(2)))
                .andExpect(model().attribute("categoryList", is(categoryList)));
    }

    @Test
    public void itemAddPost() throws Exception {
        this.mockMvc.perform(post("/clerk/item/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonItem.write(item1).getJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("Created")))
                .andExpect(jsonPath("$.data", is("Item has been saved successfully!")));
    }

    @Test
    public void itemShow_Found() throws Exception {
        when(itemServiceMock.getAllItems()).thenReturn(Arrays.asList(item1, item2));
        this.mockMvc.perform(get("/clerk/item/show"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status",is("Found")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name", is("Item test1")))
                .andExpect(jsonPath("$.data[1].name", is("Item test2")));
    }

    @Test
    public void itemShow_NotFound() throws Exception {
        List<Item> itemList = new ArrayList<>();
        when(itemServiceMock.getAllItems()).thenReturn(itemList);
        this.mockMvc.perform(get("/clerk/item/show"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status",is("Not Found")))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    public void itemDelete_Found() throws Exception {
        when(itemServiceMock.deleteItem(1L)).thenReturn(item1);
        this.mockMvc.perform(delete("/clerk/item/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status",is("Deleted")))
                .andExpect(jsonPath("$.data",is("Item has been deleted!")));
    }

    @Test
    public void itemDelete_NotFound() throws Exception {
        when(itemServiceMock.deleteItem(2L)).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(delete("/clerk/item/{id}", 2))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status",is("NOT_FOUND")));
    }

    @Test
    public void itemDetails() throws Exception {
        when(itemServiceMock.detailItem(1L)).thenReturn(item1);
        this.mockMvc.perform(get("/clerk/item/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("clerk/item-view"))
                .andExpect(model().attribute("item", is(item1)));

    }

    @Test
    public void itemPrint()  throws Exception{
        when(itemServiceMock.detailItem(1L)).thenReturn(item1);
        this.mockMvc.perform(get("/clerk/item/{id}/print", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("clerk/item-print"))
                .andExpect(model().attribute("item", is(item1)));

    }
}