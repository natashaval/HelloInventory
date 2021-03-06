package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.dto.ItemSerialCount;
import hello.inven.helloinven.exceptionhandler.BadRequestException;
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
        // Jika menggunakan Query sendiri akan menghasilkan array, bukan Object
        return itemList;
    }

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public List<ActionItem> requestItemAssets(MyUser requester, List<Long> requestValues, String comment, Boolean requestType){
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
            return  actionItemList;
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
            return actionItemList;
        }

        throw new BadRequestException("Request / Return Error happened!");
    }

    @Override
    public List<ItemSerialCount> countMyItem(MyUser myUser){
        List<ItemSerialCount> myItemCount = itemSerialRepository.findAndCountItemSerialByEmpId(myUser.getId());
        return myItemCount;
    }

    @Override
    public List<ItemSerial> findMyItemSerials(Long itemId, MyUser myUser){
        List<ItemSerial> itemSerials = itemSerialRepository.findItemSerialsByItemIdAndMyUser(itemId, myUser);
        return itemSerials;

    }

    /* =========== Employee > Request Status ==========*/
    @Override
    public List<ActionTransaction> getActionTransactions(MyUser myUser){
        List<ActionTransaction> transactionList = transactionRepository.findActionTransactionsByRequestedBy(myUser);
        return transactionList;
    }

    @Override
    public List<ActionItem> getActionItemStatus(Long actionId, MyUser myUser){
        List<ActionItem> itemList = actionItemRepository.findActionItemsByActionItemIdActionTransactionActionIdAndActionItemIdActionTransactionRequestedBy(actionId, myUser);
        return itemList;
    }

    @Override
    public ActionTransaction cancelRequest(Long actionId, MyUser myUser){
        ActionTransaction transaction = transactionRepository.findById(actionId).orElse(null);
        if (transaction == null) throw new NotFoundException("Action Transaction not found!");

        if (transaction.getActionType().equals(ActionTransaction.ActionType.PendingApproval) || transaction.getActionType().equals(ActionTransaction.ActionType.PendingInventory) || transaction.getActionType().equals(ActionTransaction.ActionType.ReturnApproval) ) {
            transaction.setActionType(ActionTransaction.ActionType.CancelRequest);
            List<ActionItem> actionItemList = actionItemRepository.findActionItemsByActionItemIdActionTransactionActionIdAndActionItemIdActionTransactionRequestedBy(actionId, myUser);
            for (ActionItem item : actionItemList) {
                item.setItemStatus(ActionItem.ItemStatus.Cancelled);
                actionItemRepository.save(item);
            }
            transactionRepository.save(transaction);
            return transaction;
        }
        else throw new BadRequestException("Request are not allowed to be cancelled!");
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
                else if (sentCount + rejectCount == itemSize){
                    transaction.setActionType(ActionTransaction.ActionType.HandedOver);
                    transactionRepository.save(transaction);
                }
            }
            else if (transaction.getActionType().toString() == "HandedOver" || transaction.getActionType().toString() == "ReturnInventory"){
                // Check if request has done (item has sent / rejected)
                Integer itemSize = transaction.getActionItemList().size();
                Integer receiveCount = 0, rejectCount = 0;
                for (ActionItem actionItem: transaction.getActionItemList()) {
                    if (actionItem.getItemStatus().toString() == "Received") receiveCount++;
                    else if (actionItem.getItemStatus().toString() == "Rejected") rejectCount++;
                }

                if (receiveCount + rejectCount == itemSize) {
                    if (transaction.getActionType().toString() == "HandedOver") transaction.setActionType(ActionTransaction.ActionType.RequestDone);
                    else if (transaction.getActionType().toString() == "ReturnInventory") transaction.setActionType(ActionTransaction.ActionType.ReturnDone);
                    transactionRepository.save(transaction);
                }
            }
        }
        return new ResponseAjax("Done", "Request Status has been refreshed!");
    }

    /* ============ EMPLOYEE RECEIVE ITEM ============*/
    @Override
    public List<ActionItem> getItemAssetsSent(MyUser myUser){
        List<ActionItem> itemList = actionItemRepository.findActionItemsByItemStatusAndActionItemIdActionTransactionRequestedBy(ActionItem.ItemStatus.Sent, myUser);
        return itemList;
    }

    @Override
    public ActionItem receiveItem(Long actionTransactionId, Long itemId){
        ActionItem actionItem = actionItemRepository.findActionItemByItemStatusAndActionItemIdActionTransactionActionIdAndActionItemIdItemId(ActionItem.ItemStatus.Sent, actionTransactionId, itemId);
        actionItem.setItemStatus(ActionItem.ItemStatus.Received);
        Date currentTime = new Date();
        actionItem.setReceiveEmpTime(currentTime);
        actionItemRepository.save(actionItem);
        return actionItem;
    }

    /* ============ Employee > Return Item =========== */
    @Override
    public List<ItemSerial> getMyItemSerial(MyUser myUser){
        List<ItemSerial> serials = itemSerialRepository.findItemSerialsByMyUser(myUser);
        return serials;
    }
}
