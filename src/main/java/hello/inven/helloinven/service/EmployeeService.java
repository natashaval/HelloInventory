package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;

import java.util.List;

public interface EmployeeService {
    ResponseAjax getAllItemAssets();

    ResponseAjax requestItemAssets(MyUser requester, List<Long> requestValues, String comment);

    ResponseAjax countMyItem(MyUser myUser);
}
