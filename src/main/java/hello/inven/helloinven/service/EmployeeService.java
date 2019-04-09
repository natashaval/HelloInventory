package hello.inven.helloinven.service;

import hello.inven.helloinven.dto.ItemSerialCount;
import hello.inven.helloinven.model.*;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface EmployeeService {
    /* =========== Employee > Request Item ==========*/
    List<Item> getAllItemAssets();
    List<ActionItem> requestItemAssets(
            MyUser requester, List<Long> requestValues, String comment, Boolean requestType); // requestType = Request / Return Item


    /* =========== Employee > MyItem ==========*/
    List<ItemSerialCount> countMyItem(MyUser myUser);
    List<ItemSerial> findMyItemSerials(Long itemId, MyUser myUser);

    /* =========== Employee > Request Status ==========*/
    List<ActionTransaction> getActionTransactions(MyUser myUser);
    List<ActionItem> getActionItemStatus(Long actionId, MyUser myUser);
    ActionTransaction cancelRequest(Long actionId, MyUser myUser);
    ResponseAjax refreshRequest(MyUser myUser);

    /* =========== Employee > Receive Item ==========*/
    List<ActionItem> getItemAssetsSent(MyUser myUser);
    ActionItem receiveItem(Long actionTransactionId, Long itemId);

    /* ============ Employee > Return Item =========== */
    List<ItemSerial> getMyItemSerial(MyUser myUser);
}
