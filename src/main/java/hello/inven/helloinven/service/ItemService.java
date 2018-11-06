package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ResponseAjax;

import java.util.List;

public interface ItemService {
    ResponseAjax createItem(Item item);

    List<Item> getAllItems();
}
