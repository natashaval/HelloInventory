package hello.inven.helloinven.ServiceTest;

import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.service.ItemService;
import hello.inven.helloinven.serviceimpl.ItemServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {
    @Mock
    ItemRepository itemRepositoryMock;

    @InjectMocks
    ItemService itemServiceMock = new ItemServiceImpl();

    private static final Long ITEM_ID = 1L;
    private static final String ITEM_NAME = "Item Test";
    private static final String ITEM_IMAGE_PATH = "";
    private static final Item.ItemType ITEM_TYPE = Item.ItemType.ASSET;
    private static final Integer ITEM_QUANTITY = 1;
    private static final Double ITEM_PRICE = 1.0;
    private static final Double ITEM_WEIGHT = 1.0;
    private static final Double ITEM_HEIGHT = 1.0;
    private static final Double ITEM_WIDTH = 1.0;
    private static final Double ITEM_DEPTH = 1.0;
    private static final Category ITEM_CATEGORY = new Category("Category Test", "Category for unit test");

    private Optional<Item> itemOpt;

    @Before
    public void setUp(){
        System.out.println("Before Item Test");
        itemOpt = Optional.of(new Item(ITEM_ID, ITEM_NAME, ITEM_IMAGE_PATH, ITEM_TYPE, ITEM_QUANTITY, ITEM_PRICE, ITEM_WEIGHT, ITEM_HEIGHT, ITEM_WIDTH, ITEM_DEPTH, ITEM_CATEGORY));
    }

    @Test
    public void getAllItems_Found(){
        Item itemBefore = itemOpt.get();
        List<Item> itemList = Collections.singletonList(itemBefore);
        when(itemRepositoryMock.findAll()).thenReturn(itemList);
        List<Item> returned = itemServiceMock.getAllItems();
        Assert.assertEquals(returned.size(), 1);
        Assert.assertEquals(returned, itemList);
    }

    @Test
    public void getAllItems_NotFound(){
        List<Item> itemList = new ArrayList<>();
        when(itemRepositoryMock.findAll()).thenReturn(itemList);
        List<Item> returned = itemServiceMock.getAllItems();
        Assert.assertEquals(returned.size(), 0);
        Assert.assertEquals(returned, itemList);
    }

    @Test
    public void detailItem_Found(){
        when(itemRepositoryMock.findById(ITEM_ID)).thenReturn(itemOpt);
        Item item = itemServiceMock.detailItem(ITEM_ID);
        Assert.assertEquals(item, itemOpt.get());
    }

    @Test(expected = NotFoundException.class)
    public void detailItem_NotFound(){
        when(itemRepositoryMock.findById(ITEM_ID)).thenReturn(itemOpt);
        Item itemNotFound = itemServiceMock.detailItem(2L);
        Assert.assertNotEquals(itemNotFound, itemOpt.get());
    }

    @Test
    public void deleteItem_Found(){
        when(itemRepositoryMock.findById(ITEM_ID)).thenReturn(itemOpt);
        Item itemAfter = itemServiceMock.deleteItem(ITEM_ID);
        verify(itemRepositoryMock, times(1)).findById(ITEM_ID);
        verify(itemRepositoryMock, times(1)).delete(itemAfter);
        Assert.assertEquals(itemAfter, itemOpt.get());
    }

    @Test(expected = NotFoundException.class)
    public void deleteItem_NotFound(){
        when(itemRepositoryMock.findById(ITEM_ID)).thenReturn(itemOpt);
        Item itemDelete = itemServiceMock.deleteItem(2L);
        Assert.assertNotEquals(itemDelete, itemOpt.get());
    }

}
