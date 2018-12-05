package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;

import java.util.List;

public interface ClerkService {
    ResponseAjax findManagerAndEmployee();

    ResponseAjax assignItemSerial(MyUser clerk, Long itemId, List<Long> employeeValues);
}
