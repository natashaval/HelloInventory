package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface ClerkService {
    ResponseAjax findManagerAndEmployee();

    ResponseAjax assignItemSerial(MyUser clerk, Long itemId, List<Long> employeeValues);

    ResponseAjax receiveItemRequest(MyUser clerk, Boolean requestType); // requestType = Request (true) / Return (false)

    ResponseAjax itemRequestActions(Long actionTransactionId, Long itemId, Long itemSerial, Boolean action);
    //Accept / Reject Item Request

    ResponseAjax itemReturnActions(Long actionTransactionId, Long itemId);
}
