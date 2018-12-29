package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface EmployeeService {
    /* =========== Employee > Request Item ==========*/
    List<Item> getAllItemAssets();
    ResponseAjax requestItemAssets(
            MyUser requester, List<Long> requestValues, String comment, Boolean requestType); // requestType = Request / Return Item


    /* =========== Employee > MyItem ==========*/
    ResponseAjax countMyItem(MyUser myUser);
    ResponseAjax findMyItemSerials(Long itemId, MyUser myUser);

    /* =========== Employee > Request Status ==========*/
    ResponseAjax getActionTransactions(MyUser myUser);
    ResponseAjax getActionItemStatus(Long actionId, MyUser myUser);
    ResponseAjax cancelRequest(Long actionId, MyUser myUser);
    ResponseAjax refreshRequest(MyUser myUser);

    /* =========== Employee > Receive Item ==========*/
    ResponseAjax getItemAssetsSent(MyUser myUser);
    ResponseAjax receiveItem(Long actionTransactionId, Long itemId);

    /* ============ Employee > Return Item =========== */
    List<ItemSerial> getMyItemSerial(MyUser myUser);
    void fillActionItemWithReturn(List<Object> itemAndSerial);
}
