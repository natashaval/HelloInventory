package hello.inven.helloinven.ServiceTest;

import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.MyUserRepository;
import hello.inven.helloinven.service.AdminService;
import hello.inven.helloinven.serviceimpl.AdminServiceImpl;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {
    @Mock
    MyUserRepository myUserRepositoryMock;

    @InjectMocks
    AdminService adminServiceMock = new AdminServiceImpl();

    private static final Long USER_ID = 1L;
    private static final String USER_PHOTO = "";
    private static final String USER_NAME = "User Test";
    private static final String USER_EMAIL = "user@test.com";
    private static final String USER_PHONE = "";
    private static final Date USER_BIRTHDAY = null;
    private static final String USER_USERNAME = "usertest";
    private static final String USER_PASSWORD = "usertest";
    private static final Long USER_MANAGER_ID = 2L;
    private static final Role USER_ROLE = new Role(1, "EMPLOYEE");
    private static final MyUser MY_USER = new MyUser(USER_ID, USER_PHOTO, USER_NAME, USER_EMAIL, USER_PHONE, USER_BIRTHDAY, USER_USERNAME, USER_PASSWORD, USER_MANAGER_ID, USER_ROLE, null, null);

    private Optional<MyUser> myUserOpt;

    @Before
    public void setUp(){
        myUserOpt = Optional.of(MY_USER);
    }

    @Test
    public void findAll_Found(){
        List<MyUser> myUserList = Collections.singletonList(MY_USER);
        when(myUserRepositoryMock.findAll()).thenReturn(myUserList);
        List<MyUser> returned = adminServiceMock.findAll();
        Assert.assertEquals(myUserList, returned);
        Assert.assertEquals(1, returned.size());
    }

    @Test
    public void findAll_NotFound(){
        List<MyUser> myUserList = new ArrayList<>();
        when(myUserRepositoryMock.findAll()).thenReturn(myUserList);
        List<MyUser> returned = adminServiceMock.findAll();
        Assert.assertEquals(myUserList, returned);
        Assert.assertEquals(0, returned.size());
    }

    @Test
    public void findByUsername_Found(){
        when(myUserRepositoryMock.findByUsername("usertest")).thenReturn(myUserOpt.get());
        MyUser myUserAfter = adminServiceMock.findByUsername("usertest");
        Assert.assertEquals(myUserOpt.get(), myUserAfter);
    }

    @Test
    public void findByUsername_NotFound(){
        when(myUserRepositoryMock.findByUsername("usertest")).thenReturn(null);
        MyUser myUserAfter = adminServiceMock.findByUsername("usertest");
        Assert.assertNull(myUserAfter);
    }

    @Test
    public void findByEmployeeId_Found(){
        when(myUserRepositoryMock.findById(USER_ID)).thenReturn(myUserOpt);
        MyUser myUserAfter = adminServiceMock.findByEmployeeId(USER_ID);
        Assert.assertEquals(myUserOpt.get(), myUserAfter);
    }

    @Test(expected = NotFoundException.class)
    public void findByEmployeeId_NotFound(){
//        when(myUserRepositoryMock.findById(USER_ID)).thenReturn(myUserOpt);
        MyUser myUserAfter = adminServiceMock.findByEmployeeId(2L);
        Assert.assertNull(myUserAfter);
    }

    @Test
    public void deleteEmployee_Found(){
        when(myUserRepositoryMock.findById(USER_ID)).thenReturn(myUserOpt);
        MyUser myUserAfter = adminServiceMock.deleteEmployee(USER_ID);
        Assert.assertEquals(myUserOpt.get(), myUserAfter);
    }

    @Test(expected = NotFoundException.class)
    public void deleteEmployee_NotFound(){
//        when(myUserRepositoryMock.findById(USER_ID)).thenReturn(myUserOpt);
        MyUser myUserAfter = adminServiceMock.deleteEmployee(2L);
        Assert.assertNull(myUserAfter);
    }
}
