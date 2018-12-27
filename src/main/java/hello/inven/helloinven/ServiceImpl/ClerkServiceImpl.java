package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.*;
import hello.inven.helloinven.repository.ActionItemRepository;
import hello.inven.helloinven.repository.ItemSerialRepository;
import hello.inven.helloinven.repository.MyUserRepository;
import hello.inven.helloinven.service.ClerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Service
public class ClerkServiceImpl implements ClerkService {
    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    ItemSerialRepository itemSerialRepository;

    @Autowired
    ActionItemRepository actionItemRepository;

    @Override
    public ResponseAjax findManagerAndEmployee(){
        return new ResponseAjax("Done", myUserRepository.findManagerAndEmployee());
    }

    @Override
    public ResponseAjax assignItemSerial(MyUser clerk, Long itemId, List<Long> employeeValues){
        Long clerkId = clerk.getId();
        List<ItemSerial> itemSerialNotAssigned = itemSerialRepository.findItemSerialNotAssigned(itemId, clerkId);

        if (itemSerialNotAssigned.size() >= employeeValues.size()){
            for (int i = 0; i < employeeValues.size(); i++){
                ItemSerial itemSerial = itemSerialNotAssigned.get(i);
                MyUser myUser = myUserRepository.findById(employeeValues.get(i)).orElse(null);
                itemSerial.setMyUser(myUser);
                itemSerialRepository.save(itemSerial);
            }

            return new ResponseAjax("Done", "Assign Item Serial to Employee Finished");
        }

        else {
            return new ResponseAjax("Failed", "Assign Item Serial to Employee FAILED");
        }

    }

    @Override
    public ResponseAjax receiveItemRequest(MyUser clerk){
//        List<ActionItem> items = actionItemRepository.findActionItemsByReceivedByAndItemStatus(clerk.getId(), ActionItem.ItemStatus.Pending);
        List<ActionItem> items = actionItemRepository.findActionItemsByReceivedByAndItemStatusAndActionItemIdActionTransactionApprovedTimeNotNull(clerk.getId(), ActionItem.ItemStatus.Pending);
        return new ResponseAjax("Done", items);
    }

    @Override
    public ResponseAjax itemRequestActions(Long actionTransactionId, Long itemId, Boolean action) {
//        ActionItem actionItem = actionItemRepository.findActionItemByActionTransactionActionIdAndItemId(actionTransactionId, itemId);
        ActionItem actionItem = actionItemRepository.findActionItemForStatus(actionTransactionId, itemId);
        if (action == Boolean.TRUE) { //Inventory Item Request Accepted
            actionItem.setItemStatus(ActionItem.ItemStatus.Sent);
            Date currentTime = new Date();
            actionItem.setReceivedTime(currentTime);
            actionItemRepository.save(actionItem);
            return new ResponseAjax("Done", "Item Sent to Employee");
        } else if (action == Boolean.FALSE) {
            actionItem.setItemStatus(ActionItem.ItemStatus.Rejected);
            Date currentTime = new Date();
            actionItem.setReceivedTime(currentTime);
            actionItemRepository.save(actionItem);
            return new ResponseAjax("Failed", "Item Request has been rejected!");
        }
        return null;
    }
}
