package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public ResponseAjax createItem(Item item){
        Item newItem = new Item();
        newItem.setId(item.getId());
        newItem.setName(item.getName());
        newItem.setQuantity(item.getQuantity());
        newItem.setPrice(item.getPrice());
        newItem.setWeight(item.getWeight());
        newItem.setHeight(item.getHeight());
        newItem.setWidth(item.getWidth());
        newItem.setDepth(item.getDepth());
        return new ResponseAjax("Created", itemRepository.save(newItem));
    }

    @Override
    public List<Item> getAllItems() {return itemRepository.findAll();}
}
