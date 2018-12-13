package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;

public interface ManagerService {
    ResponseAjax requestApproval(MyUser manager);
}
