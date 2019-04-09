package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.RoleRepository;
import hello.inven.helloinven.service.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    RoleRepository roleRepositoryMock;

    @InjectMocks
    RoleService roleServiceMock = new RoleServiceImpl();

    @Test
    public void findAll() {
        List<Role> roleList = Arrays.asList(
                new Role(1, "Admin"),
                new Role(2, "Clerk")
        );

        when(roleRepositoryMock.findAll()).thenReturn(roleList);
        List<Role>roleAfter = roleServiceMock.findAll();
        Assert.assertEquals(roleList, roleAfter);
    }
}