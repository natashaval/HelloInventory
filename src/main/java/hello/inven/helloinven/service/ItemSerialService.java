package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;

import java.util.List;

public interface ItemSerialService {
    ResponseAjax getItemSerialByItemId(Long itemId);
    ResponseAjax createItemSerial(MyUser clerk, Long itemId, List<Long> itemSerialValues);
}
