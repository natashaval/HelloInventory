package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.*;
import hello.inven.helloinven.repository.ActionItemRepository;
import hello.inven.helloinven.repository.ActionTransactionRepository;
import hello.inven.helloinven.repository.ItemRepository;
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
        transaction.setActionType(ActionTransaction.ActionType.PendingApproval);
        if (requester.getManagerId() != null) transaction.setApprovedBy(requester.getManagerId());
        transaction.setActionRemarks(comment);
        transaction = transactionRepository.save(transaction);

        transactionRepository.flush();

        System.out.println("\nget transaction ID: " + transaction.getActionId());

        List<ActionItem> actionItemList = new ArrayList<>();
        for (Long requestId: requestValues) {
            Item item = itemRepository.findById(requestId).orElse(null);
            ActionItem actionItem = new ActionItem();
            if (item != null) {
                actionItem.setActionTransaction(transaction);
                actionItem.setItem(item);
                actionItem.setItemStatus(ActionItem.ItemStatus.Pending);
                actionItemList.add(actionItem);
            }
        }
        actionItemRepository.saveAll(actionItemList);

        return new ResponseAjax("Done", "Items have been requested!");
    }
}