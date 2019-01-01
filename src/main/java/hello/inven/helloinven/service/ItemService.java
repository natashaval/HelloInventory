package hello.inven.helloinven.service;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.response.ResponseAjax;

import java.io.IOException;
import java.util.List;

public interface ItemService {
//    ResponseAjax createItem(Item item, MultipartFile file) throws IOException;
    Item createItem(Item item) throws IOException;
    List<Item> getAllItems();
    Item deleteItem(Long id);
    Item detailItem(Long id);



}
