package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.model.Asset;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.repository.AssetRepository;
import hello.inven.helloinven.repository.ItemRepository;
import hello.inven.helloinven.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    AssetRepository assetRepository;

//    https://www.codejava.net/frameworks/hibernate/hibernate-many-to-many-association-with-extra-columns-in-join-table-example
    @Override
    public ResponseAjax createAssets(MyUser clerk, Long itemId, List<String> assetValues){
        Item item = itemRepository.findById(itemId).orElse(null);

//        List<Asset> assetList = new ArrayList<>();

        for (String asset: assetValues) {
            System.out.println(asset);
            Asset newAsset = new Asset();
            newAsset.setMyUser(clerk);
            newAsset.setItem(item);
            newAsset.setSerialNumber(asset);

//            assetList.add(newAsset);

            assetRepository.save(newAsset);
        }

//        assetRepository.saveAll(assetList);
        /*
        for (int i = 0; i < assetList.size(); i++){
            assetRepository.save(assetList.get(i));
        }
        */

        return new ResponseAjax("Done", "Asset(s) are saved!");
    }

}
