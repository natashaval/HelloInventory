package hello.inven.helloinven.ControllerTest;

import hello.inven.helloinven.configuration.ApplicationConfig;
import hello.inven.helloinven.controller.CategoryController;
import hello.inven.helloinven.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(CategoryController.class)
//@WebAppConfiguration
//@EnableAutoConfiguration(exclude = {JpaRepositoriesAutoConfiguration.class})
//https://stackoverflow.com/questions/49642210/exception-at-least-one-jpa-metamodel-must-be-present-thrown-in-controller-test
//@ContextConfiguration(classes = {ApplicationConfig.class})
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

//    https://spring.io/guides/gs/testing-web/
    /*
    @Test
    public void category() throws Exception{
        this.mvc.perform(get("/clerk/category")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("clerk/category"));
    }
    */


}
