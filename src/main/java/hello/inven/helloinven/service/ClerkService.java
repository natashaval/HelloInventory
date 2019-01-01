package hello.inven.helloinven.service;

import hello.inven.helloinven.model.ActionItem;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface ClerkService {
    List<MyUser> findManagerAndEmployee();

    List<Long> assignItemSerial(MyUser clerk, Long itemId, List<Long> employeeValues);

    List<ActionItem> receiveItemRequest(MyUser clerk, Boolean requestType); // requestType = Request (true) / Return (false)

    ActionItem itemRequestActions(Long actionTransactionId, Long itemId, Long itemSerial, Boolean action);
    //Accept / Reject Item Request

    ActionItem itemReturnActions(Long actionTransactionId, Long itemId);
}
