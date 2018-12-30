package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.response.ResponseAjax;

import java.io.IOException;
import java.util.List;

public interface ItemService {
//    ResponseAjax createItem(Item item, MultipartFile file) throws IOException;
    ResponseAjax createItem(Item item) throws IOException;
    ResponseAjax deleteItem(Long id);
    Item detailItem(Long id);

    List<Item> getAllItems();

}
