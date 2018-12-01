package hello.inven.helloinven.ServiceImpl;

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
    public ResponseAjax getItemSerialByItemId(Long itemId){
        List<ItemSerial> itemSerials = itemSerialRepository.findItemSerialByItemId(itemId);
        return new ResponseAjax("Done", itemSerials);
    }

    @Override
    public ResponseAjax createItemSerial(MyUser clerk, Long itemId, List<Long> itemSerialValues){
        Item item = itemRepository.findById(itemId).orElse(null);

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
            ItemSerial newItemSerial = new ItemSerial();
            newItemSerial.setSerialId(itemSerial);
            newItemSerial.setItem(item);
            newItemSerial.setMyUser(clerk);

            itemSerialList.add(newItemSerial);
        }

        itemSerialRepository.saveAll(itemSerialList);

        return new ResponseAjax("Done", "Item Serials has been saved!");
    }
}
