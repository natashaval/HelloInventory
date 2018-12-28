package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.dto.ItemSerialOnly;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
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
    public ResponseAjax changeToItemSerial(Long itemId){
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            item.setItemType(Item.ItemType.ASSET);
            itemRepository.save(item);
        }
        return new ResponseAjax("Done", "Item has been changed to Assets");
    }

    @Override
//    @Transactional
    public ResponseAjax getItemSerialByItemId(Long itemId){
//        List<ItemSerial> itemSerials = itemSerialRepository.findItemSerialsByItemId(itemId);
//        return new ResponseAjax("Done", itemSerials);
        List<ItemSerialOnly> serialOnly = itemSerialRepository.findItemSerialsOnlyByItemId(itemId);
        return new ResponseAjax("Done", serialOnly);
    }

    @Override
    public ResponseAjax createItemSerial(MyUser clerk, Long itemId, List<Long> itemSerialValues){
        Item item = itemRepository.findById(itemId).orElse(null);
        item.setItemType(Item.ItemType.ASSET);
        itemRepository.save(item);


        /*
        List<Asset> assetList = new ArrayList<>();

        for (String asset: assetValues) {
            System.out.println(asset);
            Asset newAsset = new Asset();
            newAsset.setMyUser(clerk);
            newAsset.setItem(item);
            newAsset.setSerialNumber(asset);

            assetList.add(newAsset);

//            assetRepository.save(newAsset);
        }
         */
        List<ItemSerial> itemSerialList = new ArrayList<>();
        for (Long itemSerial : itemSerialValues ){
            System.out.println(itemSerial);
            if (itemSerial == null || itemSerial.toString().isEmpty()) {
                System.out.println("iniKosong: " + itemSerial);
                continue;
            };

            ItemSerial newItemSerial = new ItemSerial();
            newItemSerial.setSerialId(itemSerial);
            newItemSerial.setItem(item);
            newItemSerial.setMyUser(clerk);
            newItemSerial.setClerkId(clerk.getId());

            itemSerialList.add(newItemSerial);

        }
        System.out.println(itemSerialList);
        itemSerialRepository.saveAll(itemSerialList);

        return new ResponseAjax("Done", "Item Serials has been saved!");
    }

    @Override
    public ResponseAjax deleteItemSerial(Long serialId){
        ItemSerial itemSerial = itemSerialRepository.findById(serialId).orElse(null);
        if (itemSerial != null){
            itemSerialRepository.deleteById(serialId);
            return new ResponseAjax("Deleted", "Item Serial has been deleted!");
        }
        else {
            return new ResponseAjax("Failed", "Item Serial is failed to be deleted!");
        }

    }
}
