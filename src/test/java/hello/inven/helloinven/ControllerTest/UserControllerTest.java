package hello.inven.helloinven.ControllerTest;

import hello.inven.helloinven.controller.UserController;
import hello.inven.helloinven.exceptionhandler.CustomRestExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(CustomRestExceptionHandler.class)
                .build();
    }

    @Test
    public void home() throws Exception{
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(forwardedUrl("home"));
    }

    @Test
    public void clerkDashboard() throws Exception{
        this.mockMvc.perform(get("/clerk/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("clerk/dashboard"))
                .andExpect(forwardedUrl("clerk/dashboard"));
    }

    @Test
    public void managerDashboard() throws Exception{
        this.mockMvc.perform(get("/manager/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("manager/dashboard"))
                .andExpect(forwardedUrl("manager/dashboard"));
    }
}