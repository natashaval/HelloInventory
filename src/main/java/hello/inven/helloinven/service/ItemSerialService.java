package hello.inven.helloinven.service;

import hello.inven.helloinven.dto.ItemSerialOnly;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface ItemSerialService {
    List<ItemSerialOnly> getItemSerialByItemId(Long itemId);
    List<ItemSerial> createItemSerial(MyUser clerk, Long itemId, List<Long> itemSerialValues);
    ItemSerial deleteItemSerial(Long serialId);
}
