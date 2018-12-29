package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface ItemSerialService {
    ResponseAjax changeToItemSerial(Long itemId);
    ResponseAjax getItemSerialByItemId(Long itemId);
    ResponseAjax createItemSerial(MyUser clerk, Long itemId, List<Long> itemSerialValues);
    ResponseAjax deleteItemSerial(Long serialId);
}
