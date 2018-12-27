package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.dto.ItemSerialCount;
import hello.inven.helloinven.model.*;
import hello.inven.helloinven.repository.ActionItemRepository;
import hello.inven.helloinven.repository.ActionTransactionRepository;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.repository.ItemSerialRepository;
import hello.inven.helloinven.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemSerialRepository itemSerialRepository;

    @Autowired
    ActionTransactionRepository transactionRepository;

    @Autowired
    ActionItemRepository actionItemRepository;

    @Override
    public ResponseAjax getAllItemAssets(){
        List<Item> itemList = itemRepository.findItemByItemType(Item.ItemType.ASSET);
//        List<Item> itemList = itemRepository.findItemIdAndNameByItemType(Item.ItemType.ASSET);
        // Jika menggunakan Query sendiri akan menghasilkan array, bukan Object
        return new ResponseAjax("Done", itemList);
    }

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public ResponseAjax requestItemAssets(MyUser requester, List<Long> requestValues, String comment){
        ActionTransaction transaction = new ActionTransaction();
        Date currenttime = new Date();
        transaction.setRequestedBy(requester);
        transaction.setRequestTime(currenttime);
        if (requester.getManagerId() != null) {
            transaction.setActionType(ActionTransaction.ActionType.PendingApproval);
            transaction.setApprovedBy(requester.getManagerId());
        }
        else if (requester.getManagerId() == null){
            transaction.setActionType(ActionTransaction.ActionType.PendingInventory);
            transaction.setApprovedTime(currenttime); // Jika sudah manager akan ter-approve dengan sendirinya
        }
        transaction.setActionRemarks(comment);
        transaction = transactionRepository.save(transaction);

        transactionRepository.flush();

        System.out.println("\nget transaction ID: " + transaction.getActionId());

        List<ActionItem> actionItemList = new ArrayList<>();
        for (Long requestId: requestValues) {
            Item item = itemRepository.findById(requestId).orElse(null);
            Long clerkIdOne = itemSerialRepository.findClerkIdByItem(requestId);
            ActionItem actionItem = new ActionItem();
            if (item != null) {
                actionItem.setActionTransaction(transaction);
                actionItem.setItem(item);
                actionItem.setItemStatus(ActionItem.ItemStatus.Pending);
                actionItem.setReceivedBy(clerkIdOne);
                actionItemList.add(actionItem);
            }
        }
        actionItemRepository.saveAll(actionItemList);

        return new ResponseAjax("Done", "Items have been requested!");
    }

    @Override
    public ResponseAjax countMyItem(MyUser myUser){
        List<ItemSerialCount> myItemCount = itemSerialRepository.findAndCountItemSerialByEmpId(myUser.getId());
        if (myItemCount == null) return new ResponseAjax("None", "No item found!");
        return new ResponseAjax("Count Done", myItemCount);
    }

    @Override
    public ResponseAjax findMyItemSerials(Long itemId, MyUser myUser){
        List<ItemSerial> itemSerials = itemSerialRepository.findItemSerialsByItemIdAndMyUser(itemId, myUser);
        if (itemSerials.isEmpty()) return new ResponseAjax("Not Found", "item serial not found!");
        return new ResponseAjax("Found", itemSerials);
    }

    /* =========== Employee > Request Status ==========*/
    @Override
    public ResponseAjax getActionTransactions(MyUser myUser){
        List<ActionTransaction> transactionList = transactionRepository.findActionTransactionsByRequestedBy(myUser);
        if (transactionList.isEmpty()) return new ResponseAjax("Not Found", "transaction list not found!");
        return new ResponseAjax("Found", transactionList);
    }

    @Override
    public ResponseAjax getActionItemStatus(Long actionId, MyUser myUser){
        List<ActionItem> itemList = actionItemRepository.findActionItemsByActionItemIdActionTransactionActionIdAndActionItemIdActionTransactionRequestedBy(actionId, myUser);
        if (itemList.isEmpty()) return new ResponseAjax("Not Found", "Item status not found!");
        return new ResponseAjax("Found", itemList);
    }
}
