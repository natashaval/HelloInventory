package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;

import java.util.List;

public interface EmployeeService {
    /* =========== Employee > Request Item ==========*/
    ResponseAjax getAllItemAssets();
    ResponseAjax requestItemAssets(MyUser requester, List<Long> requestValues, String comment);

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
}
