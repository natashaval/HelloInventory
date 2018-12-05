package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;

public interface ClerkService {
    ResponseAjax findManagerAndEmployee();

    ResponseAjax getItemSerialHandleByClerk(MyUser clerk, Long itemId);
}
