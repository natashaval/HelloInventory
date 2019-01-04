package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.*;
import hello.inven.helloinven.repository.ActionItemRepository;
import hello.inven.helloinven.repository.ItemSerialRepository;
import hello.inven.helloinven.repository.MyUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClerkServiceImplTest {

    @Mock
    MyUserRepository myUserRepositoryMock;

    @Mock
    ItemSerialRepository itemSerialRepositoryMock;

    @Mock
    ActionItemRepository actionItemRepositoryMock;

    @InjectMocks
    ClerkServiceImpl clerkServiceMock;

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
    private static final Item ITEM = new Item(1L, "Item Test", "", Item.ItemType.ASSET, 1, 1.0, 1.0, 1.0, 1.0, 1.0, new Category("Category Test", "category for unit test"));
    private static final ActionTransaction ACTION_TRANSACTION_REQUEST = new ActionTransaction(ActionTransaction.ActionType.PendingInventory, MY_USER, new Date(), 2222L, new Date(), null, null);
    private static final ActionTransaction ACTION_TRANSACTION_RETURN = new ActionTransaction(ActionTransaction.ActionType.ReturnInventory, MY_USER, new Date(), 2222L, new Date(), null, null);

    private static final ActionItem ACTION_ITEM_REQUEST_1 = new ActionItem(new ActionItemId(ITEM, ACTION_TRANSACTION_REQUEST), ActionItem.ItemStatus.Pending, 3333L, new Date(), null, null);
    private static final ActionItem ACTION_ITEM_RETURN_1 = new ActionItem(new ActionItemId(ITEM, ACTION_TRANSACTION_RETURN), ActionItem.ItemStatus.Pending, 3333L, new Date(), null, null);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findManagerAndEmployee() {
        List<MyUser> userList = Collections.singletonList(MY_USER);
        when(myUserRepositoryMock.findManagerAndEmployee()).thenReturn(userList);
        List<MyUser> userAfter = clerkServiceMock.findManagerAndEmployee();
        Assert.assertEquals(userList, userAfter);
    }

    @Test
    public void receiveItemRequest_Request() {
        List<ActionItem> actionItemList = Collections.singletonList(ACTION_ITEM_REQUEST_1);
        when(actionItemRepositoryMock.findActionItemsByReceivedByAndItemStatusAndActionItemIdActionTransactionActionTypeAndActionItemIdActionTransactionApprovedTimeNotNull(MY_USER.getId(), ActionItem.ItemStatus.Pending, ActionTransaction.ActionType.PendingInventory)).thenReturn(actionItemList);
        List<ActionItem> actionItemAfter = clerkServiceMock.receiveItemRequest(MY_USER, Boolean.TRUE);
        Assert.assertEquals(actionItemList, actionItemAfter);
    }

    @Test
    public void receiveItemRequest_Return() {
        List<ActionItem> actionItemList = Collections.singletonList(ACTION_ITEM_RETURN_1);
        when(actionItemRepositoryMock.findActionItemsByReceivedByAndItemStatusAndActionItemIdActionTransactionActionTypeAndActionItemIdActionTransactionApprovedTimeNotNull(MY_USER.getId(), ActionItem.ItemStatus.Pending, ActionTransaction.ActionType.ReturnInventory)).thenReturn(actionItemList);
        List<ActionItem> actionItemAfter = clerkServiceMock.receiveItemRequest(MY_USER, Boolean.FALSE);
        Assert.assertEquals(actionItemList, actionItemAfter);
    }

    @Test
    public void itemRequestActions_Accepted() {
        when(actionItemRepositoryMock.findActionItemByItemStatusAndActionItemIdActionTransactionActionIdAndActionItemIdItemId(ActionItem.ItemStatus.Pending, ACTION_TRANSACTION_REQUEST.getActionId(), ITEM.getId())).thenReturn(ACTION_ITEM_REQUEST_1);
        ActionItem actionItemAfter = clerkServiceMock.itemRequestActions(ACTION_TRANSACTION_REQUEST.getActionId(), ITEM.getId(), 1111L, Boolean.TRUE);
        Assert.assertEquals(ActionItem.ItemStatus.Sent, actionItemAfter.getItemStatus());
        Assert.assertNotNull(actionItemAfter.getReceivedTime());
        Assert.assertNotNull(actionItemAfter.getItemSerialId());
    }

    @Ignore
    public void itemRequestActions_Rejected() {
        when(actionItemRepositoryMock.findActionItemByItemStatusAndActionItemIdActionTransactionActionIdAndActionItemIdItemId(ActionItem.ItemStatus.Pending, ACTION_TRANSACTION_REQUEST.getActionId(), ITEM.getId()))
                .thenReturn(ACTION_ITEM_REQUEST_1);
        ActionItem actionItemAfter = clerkServiceMock.itemRequestActions(ACTION_TRANSACTION_REQUEST.getActionId(), ITEM.getId(), 1111L, Boolean.FALSE);
        Assert.assertEquals(ActionItem.ItemStatus.Rejected, actionItemAfter.getItemStatus());
        Assert.assertNotNull(actionItemAfter.getReceivedTime());
        Assert.assertNull(actionItemAfter.getItemSerialId());
    }

    @Test
    public void itemReturnActions_Accepted() {
        when(actionItemRepositoryMock.findActionItemByItemStatusAndActionItemIdActionTransactionActionIdAndActionItemIdItemId(ActionItem.ItemStatus.Pending, ACTION_TRANSACTION_RETURN.getActionId(), ITEM.getId())).thenReturn(ACTION_ITEM_REQUEST_1);
        ActionItem actionItemAfter = clerkServiceMock.itemReturnActions(ACTION_TRANSACTION_REQUEST.getActionId(), ITEM.getId());
        Assert.assertEquals(ActionItem.ItemStatus.Received, actionItemAfter.getItemStatus());
        Assert.assertNotNull(actionItemAfter.getReceivedTime());
    }

    @Test(expected = NotFoundException.class)
    public void itemReturnActions_NotFound(){
        clerkServiceMock.itemReturnActions(ACTION_TRANSACTION_REQUEST.getActionId(), ITEM.getId());
    }
}