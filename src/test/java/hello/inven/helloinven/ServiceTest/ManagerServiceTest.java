package hello.inven.helloinven.ServiceTest;

import hello.inven.helloinven.exceptionhandler.BadRequestException;
import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.ActionTransaction;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.ActionTransactionRepository;
import hello.inven.helloinven.service.ManagerService;
import hello.inven.helloinven.serviceimpl.ManagerServiceImpl;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {
    @Mock
    ActionTransactionRepository transactionRepositoryMock;

    @InjectMocks
    ManagerService managerServiceMock = new ManagerServiceImpl();

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

    private static final ActionTransaction TRANSACTION_REQUEST = new ActionTransaction(ActionTransaction.ActionType.PendingApproval, null, null, 2L, null, null, null);
    private static final ActionTransaction TRANSACTION_RETURN = new ActionTransaction(ActionTransaction.ActionType.ReturnApproval, null, null, 2L, null, null, null);
    private static final Long TRANSACTION_REQUEST_ID = 1L;
    private static final Long TRANSACTION_RETURN_ID = 2L;

    private Optional<MyUser> myUserOpt;
    private Optional<ActionTransaction> requestTransOpt;
    private Optional<ActionTransaction> returnTransOpt;


    @Before
    public void setUp(){
//        myUserOpt = Optional.of(MY_USER);
        requestTransOpt = Optional.of(TRANSACTION_REQUEST);
        returnTransOpt = Optional.of(TRANSACTION_RETURN);
//        when(transactionRepositoryMock.findById(TRANSACTION_REQUEST_ID)).thenReturn(requestTransOpt);
//        when(transactionRepositoryMock.findById(TRANSACTION_RETURN_ID)).thenReturn(returnTransOpt);
    }

    @Test
    public void requestApproval_PendingApproval(){
        ActionTransaction transaction = new ActionTransaction(ActionTransaction.ActionType.PendingApproval, null, null, 1L, null, null, null);
        List<ActionTransaction> transactionList = Collections.singletonList(transaction);
        when(transactionRepositoryMock.findByActionTypeAndApprovedBy(ActionTransaction.ActionType.PendingApproval, 1L)).thenReturn(transactionList);
        List<ActionTransaction> transactionListAfter = managerServiceMock.requestApproval(ActionTransaction.ActionType.PendingApproval, MY_USER);
        Assert.assertEquals(transactionListAfter, transactionList);
    }

    @Test
    public void requestApproval_ReturnApproval(){
        ActionTransaction transaction = new ActionTransaction(ActionTransaction.ActionType.ReturnApproval, null, null, 1L, null, null, null);
        List<ActionTransaction> transactionList = Collections.singletonList(transaction);
        when(transactionRepositoryMock.findByActionTypeAndApprovedBy(ActionTransaction.ActionType.ReturnApproval, 1L)).thenReturn(transactionList);
        List<ActionTransaction> transactionListAfter = managerServiceMock.requestApproval(ActionTransaction.ActionType.ReturnApproval, MY_USER);
        Assert.assertEquals(transactionListAfter, transactionList);
    }

    @Test
    public void approvedApproval_Request(){
//        https://stackoverflow.com/questions/50196260/mocking-method-in-mockito-returns-a-null-pointer-exception
        when(transactionRepositoryMock.findById(TRANSACTION_REQUEST_ID)).thenReturn(requestTransOpt);
        ActionTransaction transactionBefore = transactionRepositoryMock.findById(TRANSACTION_REQUEST_ID).get();
        /*
        Date currentTime = new Date();
        transactionBefore.setApprovedTime(currentTime);
        transactionBefore.setActionType(ActionTransaction.ActionType.PendingInventory);
        transactionRepositoryMock.save(transactionBefore);
        */
        managerServiceMock.approvedApproval(TRANSACTION_REQUEST_ID, Boolean.TRUE);
        ActionTransaction transactionAfter = transactionRepositoryMock.findById(TRANSACTION_REQUEST_ID).get();
        Assert.assertNotNull(transactionAfter.getApprovedTime());
        Assert.assertEquals("PendingInventory", transactionAfter.getActionType().toString());
    }

    @Test
    public void approvedApproval_Return(){
        Optional<ActionTransaction> transactionOpt = Optional.of(new ActionTransaction(ActionTransaction.ActionType.ReturnApproval, null, null, 2L, null, null, null));
        // bisa null pointer exception jika findById(1L), tetapi jika diisi Any bisa jalan
        when(transactionRepositoryMock.findById(TRANSACTION_RETURN_ID)).thenReturn(returnTransOpt);
        ActionTransaction transactionBefore = transactionRepositoryMock.findById(TRANSACTION_RETURN_ID).get();
        managerServiceMock.approvedApproval(TRANSACTION_RETURN_ID, Boolean.FALSE);
        ActionTransaction transactionAfter = transactionRepositoryMock.findById(TRANSACTION_RETURN_ID).get();
        Assert.assertNotNull(transactionAfter.getApprovedTime());
        Assert.assertEquals("ReturnInventory", transactionAfter.getActionType().toString());
    }

    @Test(expected = BadRequestException.class)
    public void approvedApproval_NotRequestOrReturn(){
        Optional<ActionTransaction> transactionOpt = Optional.of(new ActionTransaction(ActionTransaction.ActionType.CancelRequest, null, null, 2L, null, null, null));
        when(transactionRepositoryMock.findById(10L)).thenReturn(transactionOpt);
        ActionTransaction transactionBefore = transactionRepositoryMock.findById(10L).get();
        managerServiceMock.approvedApproval(10L, Boolean.FALSE);
        ActionTransaction transactionAfter = transactionRepositoryMock.findById(10L).get();
    }
}
