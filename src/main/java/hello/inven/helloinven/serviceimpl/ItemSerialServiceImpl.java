package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.dto.ItemSerialOnly;
import hello.inven.helloinven.exceptionhandler.BadRequestException;
import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.repository.ItemSerialRepository;
import hello.inven.helloinven.service.ItemSerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemSerialServiceImpl implements ItemSerialService {

    @Autowired
    ItemSerialRepository itemSerialRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<ItemSerialOnly> getItemSerialByItemId(Long itemId){
        List<ItemSerialOnly> serialOnly = itemSerialRepository.findItemSerialsOnlyByItemId(itemId);
        return serialOnly;
    }

    @Override
    public List<ItemSerial> createItemSerial(MyUser clerk, Long itemId, List<Long> itemSerialValues){
        Item item = itemRepository.findById(itemId).orElse(null);
        item.setItemType(Item.ItemType.ASSET);
        itemRepository.save(item);

        List<ItemSerial> itemSerialList = new ArrayList<>();
        for (Long itemSerial : itemSerialValues ){
            if (itemSerial == null || itemSerial.toString().isEmpty()) {
                continue;
            };
            ItemSerial itemSerialExists = itemSerialRepository.findById(itemSerial).orElse(null);
            if (itemSerialExists!= null) throw new BadRequestException("One of item serial inputted has already exists");

            ItemSerial newItemSerial = new ItemSerial();
            newItemSerial.setSerialId(itemSerial);
            newItemSerial.setItem(item);
            newItemSerial.setMyUser(clerk);
            newItemSerial.setClerkId(clerk.getId());
            itemSerialList.add(newItemSerial);

        }
        itemSerialRepository.saveAll(itemSerialList);
        return itemSerialList;
    }

    @Override
    public ItemSerial deleteItemSerial(Long serialId){
        ItemSerial itemSerial = itemSerialRepository.findById(serialId).orElse(null);
        if (itemSerial != null){
            itemSerialRepository.deleteById(serialId);
            return itemSerial;
        }
        else {
            throw new NotFoundException("Item Serial not found and failed to be deleted!");
        }
    }
}
