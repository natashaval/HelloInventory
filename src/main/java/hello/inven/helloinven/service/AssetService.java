package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;

import java.util.List;

public interface AssetService {
    ResponseAjax createAssets(MyUser clerk, Long itemId, List<String> assetValues);
}
