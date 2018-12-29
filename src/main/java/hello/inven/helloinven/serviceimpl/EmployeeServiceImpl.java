package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.dto.ItemSerialCount;
import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.*;
import hello.inven.helloinven.repository.ActionItemRepository;
import hello.inven.helloinven.repository.ActionTransactionRepository;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.repository.ItemSerialRepository;
import hello.inven.helloinven.response.ResponseAjax;
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
    public List<Item> getAllItemAssets(){
        List<Item> itemList = itemRepository.findItemByItemType(Item.ItemType.ASSET);
//        List<Item> itemList = itemRepository.findItemIdAndNameByItemType(Item.ItemType.ASSET);
        // Jika menggunakan Query sendiri akan menghasilkan array, bukan Object
        return itemList;
    }

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public ResponseAjax requestItemAssets(MyUser requester, List<Long> requestValues, String comment, Boolean requestType){
        // ------ Simpan transaksi
        ActionTransaction transaction = new ActionTransaction();
        Date currenttime = new Date();
        transaction.setRequestedBy(requester);
        transaction.setRequestTime(currenttime);
        if (requester.getManagerId() != null) {
            if(requestType == true) transaction.setActionType(ActionTransaction.ActionType.PendingApproval);
            else if (requestType == false) transaction.setActionType(ActionTransaction.ActionType.ReturnApproval);
            transaction.setApprovedBy(requester.getManagerId());
        }
        else if (requester.getManagerId() == null){
            if (requestType == true) transaction.setActionType(ActionTransaction.ActionType.PendingInventory);
            else if (requestType == false) transaction.setActionType(ActionTransaction.ActionType.ReturnInventory);
            transaction.setApprovedTime(currenttime); // Jika sudah manager akan ter-approve dengan sendirinya
        }
        transaction.setActionRemarks(comment);
        transaction = transactionRepository.saveAndFlush(transaction);

        System.out.println("\nget transaction ID: " + transaction.getActionId());

        // ------ Masukkan barang transaksi
        if (requestType == true) {
            List<ActionItem> actionItemList = new ArrayList<>();
            for (Long requestId : requestValues) {
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

        else if (requestType == false){
            List<ActionItem> actionItemList = new ArrayList<>();
            for (Long returnId : requestValues){ // berisi serialId
                ActionItem actionItem = new ActionItem();
                ItemSerial itemSerial = itemSerialRepository.findById(returnId).orElse(null);
                if (itemSerial != null){
                    actionItem.setActionTransaction(transaction);
                    actionItem.setItem(itemSerial.getItem());
                    actionItem.setItemStatus(ActionItem.ItemStatus.Pending);
                    actionItem.setReceivedBy(itemSerial.getClerkId());
                    actionItem.setItemSerialId(itemSerial.getSerialId());
                    actionItemList.add(actionItem);
                }
            }
            actionItemRepository.saveAll(actionItemList);
            return new ResponseAjax("Done", "Items are asked to be returned!");
        }

        return new ResponseAjax("Wrong", "Something wrong happened!");
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

    @Override
    public ResponseAjax cancelRequest(Long actionId, MyUser myUser){
        ActionTransaction transaction = transactionRepository.findById(actionId).orElse(null);
        if (transaction == null) return new ResponseAjax("Not Found", "Action Transaction not found!");

        transaction.setActionType(ActionTransaction.ActionType.CancelRequest);
         List<ActionItem> actionItemList = actionItemRepository.findActionItemsByActionItemIdActionTransactionActionIdAndActionItemIdActionTransactionRequestedBy(actionId, myUser);
        for (ActionItem item: actionItemList) {
            item.setItemStatus(ActionItem.ItemStatus.Cancelled);
            actionItemRepository.save(item);
        }
        transactionRepository.save(transaction);
        return new ResponseAjax("Cancelled", "Request has been cancelled!");
    }

    @Override
    public ResponseAjax refreshRequest(MyUser myUser){
        List<ActionTransaction> transactionList = transactionRepository.findActionTransactionsByRequestedBy(myUser);
        for (ActionTransaction transaction: transactionList) {
            System.out.println(transaction.getActionType());
            if (transaction.getActionType().toString() == "CancelRequest"){
                continue;
            }
            else if (transaction.getActionType().toString() == "PendingInventory"){
                // Check if clerk has accept / reject request inventory
                Integer itemSize = transaction.getActionItemList().size();
                Integer sentCount = 0, rejectCount = 0;
                for (ActionItem actionItem: transaction.getActionItemList()) {
                    if (actionItem.getItemStatus().toString() == "Sent") sentCount++;
                    else if (actionItem.getItemStatus().toString() == "Rejected") rejectCount++;
                }

                if (rejectCount == itemSize) {
                    transaction.setActionType(ActionTransaction.ActionType.RejectInventory);
                    transactionRepository.save(transaction);
                }
                else {
                    transaction.setActionType(ActionTransaction.ActionType.HandedOver);
                    transactionRepository.save(transaction);
                }
            }
            else if (transaction.getActionType().toString() == "HandedOver"){
                Integer itemSize = transaction.getActionItemList().size();
                Integer receiveCount = 0, rejectCount = 0;
                for (ActionItem actionItem: transaction.getActionItemList()) {
                    if (actionItem.getItemStatus().toString() == "Received") receiveCount++;
                    else if (actionItem.getItemStatus().toString() == "Rejected") rejectCount++;
                }

                if (receiveCount + rejectCount == itemSize) {
                    transaction.setActionType(ActionTransaction.ActionType.RequestDone);
                    transactionRepository.save(transaction);
                }
            }
        }
        return new ResponseAjax("Done", "Request Status has been refreshed!");
    }

    /* ============ EMPLOYEE RECEIVE ITEM ============*/
    @Override
    public ResponseAjax getItemAssetsSent(MyUser myUser){
        List<ActionItem> itemList = actionItemRepository.findActionItemsByItemStatusAndActionItemIdActionTransactionRequestedBy(ActionItem.ItemStatus.Sent, myUser);
        if (itemList.isEmpty()) return new ResponseAjax("Not Found", "No Item Available!");
        return new ResponseAjax("Found", itemList);
    }

    @Override
    public ResponseAjax receiveItem(Long actionTransactionId, Long itemId){
        ActionItem actionItem = actionItemRepository.findActionItemByItemStatusAndActionItemIdActionTransactionActionIdAndActionItemIdItemId(ActionItem.ItemStatus.Sent, actionTransactionId, itemId);
        actionItem.setItemStatus(ActionItem.ItemStatus.Received);
        Date currentTime = new Date();
        actionItem.setReceiveEmpTime(currentTime);
        actionItemRepository.save(actionItem);
        return new ResponseAjax("Success", "Item has been received! Check 'My Item'");
    }

    /* ============ Employee > Return Item =========== */
    @Override
    public List<ItemSerial> getMyItemSerial(MyUser myUser){
        List<ItemSerial> serials = itemSerialRepository.findItemSerialsByMyUser(myUser);
        return serials;
    }

    @Override
    public void fillActionItemWithReturn(List<Object> itemAndSerial){

    }
}
