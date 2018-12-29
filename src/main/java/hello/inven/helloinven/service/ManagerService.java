package hello.inven.helloinven.service;

import hello.inven.helloinven.model.ActionTransaction;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface ManagerService {
    List<ActionTransaction> requestApproval(ActionTransaction.ActionType actionType, MyUser manager);
    ActionTransaction approvedApproval(Long actionId, Boolean requestType); // requestType = Request (true) / Return (false) Item
    ActionTransaction rejectedApproval(Long actionId);

}
