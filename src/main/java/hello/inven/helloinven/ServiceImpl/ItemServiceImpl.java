package hello.inven.helloinven.ServiceImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.repository.CategoryRepository;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseAjax createItem(Item item){
        Item newItem = new Item();
        System.out.println("item ID: " + item.getId() + "item Name: " + item.getName());
        newItem.setId(item.getId());
        newItem.setName(item.getName());
        newItem.setQuantity(item.getQuantity());
        newItem.setPrice(item.getPrice());
        newItem.setWeight(item.getWeight());
        newItem.setHeight(item.getHeight());
        newItem.setWidth(item.getWidth());
        newItem.setDepth(item.getDepth());

//        Integer categoryId = item.getCategory().getId();
        System.out.println("Category ID apakah dapat: " + item.getCategory().getId());
        System.out.println("Ini cuma category: "+ item.getCategory());
//        Category category = categoryRepository.findById(categoryId).get();


//        newItem.setCategory(category);
        newItem.setCategory(item.getCategory());

         itemRepository.save(newItem);
        return new ResponseAjax("Created", newItem);
    }

    @Override
    public ResponseAjax getAllItems() {
        List<Item> itemList = itemRepository.findAll();
        return new ResponseAjax("Read", itemList);
    }
}
