package hello.inven.helloinven.ServiceTest;

import hello.inven.helloinven.repository.ItemSerialRepository;
import hello.inven.helloinven.service.ItemSerialService;
import hello.inven.helloinven.serviceimpl.ItemSerialServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemSerialTest {
    @Mock
    ItemSerialRepository itemSerialRepositoryMock;

    @InjectMocks
    ItemSerialService itemSerialServiceMock = new ItemSerialServiceImpl();



}
