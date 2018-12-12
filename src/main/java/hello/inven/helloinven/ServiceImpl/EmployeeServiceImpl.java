package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    ItemRepository itemRepository;

    @Override
    public ResponseAjax getAllItemAssets(){
        List<Item> itemList = itemRepository.findItemByItemType(Item.ItemType.ASSET);
//        List<Item> itemList = itemRepository.findItemIdAndNameByItemType(Item.ItemType.ASSET);
        // Jika menggunakan Query sendiri akan menghasilkan array, bukan Object
        return new ResponseAjax("Done", itemList);
    }
}
