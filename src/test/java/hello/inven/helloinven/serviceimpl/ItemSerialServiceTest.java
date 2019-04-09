package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.repository.ItemSerialRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemSerialServiceTest {
    @Mock
    ItemSerialRepository itemSerialRepositoryMock;

    @Mock
    ItemRepository itemRepositoryMock;

    @InjectMocks
    ItemSerialServiceImpl itemSerialServiceMock;

    private static final Long ITEM_ID = 1L;
    private static final String ITEM_NAME = "Item Test";
    private static final String ITEM_IMAGE_PATH = "";
    private static final Item.ItemType ITEM_TYPE = Item.ItemType.ITEM;
    private static final Integer ITEM_QUANTITY = 1;
    private static final Double ITEM_PRICE = 1.0;
    private static final Double ITEM_WEIGHT = 1.0;
    private static final Double ITEM_HEIGHT = 1.0;
    private static final Double ITEM_WIDTH = 1.0;
    private static final Double ITEM_DEPTH = 1.0;
    private static final Category ITEM_CATEGORY = new Category("Category Test", "Category for unit test");
    private static final MyUser MY_USER = new MyUser();

    private Optional<Item> itemOpt;
    private Optional<ItemSerial> itemSerialOpt;

    @Before
    public void setUp(){
        System.out.println("Before Item Serial Test");
        Item item = new Item(ITEM_ID, ITEM_NAME, ITEM_IMAGE_PATH, ITEM_TYPE, ITEM_QUANTITY, ITEM_PRICE, ITEM_WEIGHT, ITEM_HEIGHT, ITEM_WIDTH, ITEM_DEPTH, ITEM_CATEGORY);
        itemOpt = Optional.of(item);
        itemSerialOpt = Optional.of(new ItemSerial(1L, item, MY_USER, 1L));
    }

    @Test
    public void createItemSerial_ItemSerialAll(){
        List<Long> ITEM_SERIAL = Arrays.asList(1L, 2L, 3L);
        when(itemRepositoryMock.findById(ITEM_ID)).thenReturn(itemOpt);
        List<ItemSerial> serialList = itemSerialServiceMock.createItemSerial(MY_USER, 1L, ITEM_SERIAL);
        Assert.assertEquals(serialList.size(), 3);
    }

    @Test
    public void createItemSerial_ItemSerialPartial(){
        List<Long> ITEM_SERIAL = Arrays.asList(1L, 2L, null);
        when(itemRepositoryMock.findById(ITEM_ID)).thenReturn(itemOpt);
        List<ItemSerial> serialList = itemSerialServiceMock.createItemSerial(MY_USER, 1L, ITEM_SERIAL);
        Assert.assertEquals(serialList.size(), 2);
    }

    @Test
    public void deleteItemSerial_Found(){
        when(itemSerialRepositoryMock.findById(1L)).thenReturn(itemSerialOpt);
        ItemSerial itemSerialDelete = itemSerialServiceMock.deleteItemSerial(1L);
        Assert.assertEquals(itemSerialDelete, itemSerialOpt.get());
    }

    @Test(expected = NotFoundException.class)
    public void deleteItemSerial_NotFound(){
//        when(itemSerialRepositoryMock.findById(1L)).thenReturn(itemSerialOpt);
        ItemSerial itemSerialDelete = itemSerialServiceMock.deleteItemSerial(2L);
    }

}

