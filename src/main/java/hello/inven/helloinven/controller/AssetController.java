package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.Category;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.service.AssetService;
import hello.inven.helloinven.service.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AssetController {
    @Autowired
    AssetService assetService;

    @GetMapping(value = "/clerk/item/{id}/assets")
    public String assetsAdd(@PathVariable Long id, Model model) {
        model.addAttribute("itemId", id);
        return "/clerk/asset-add";
    }

    //    https://stackoverflow.com/questions/5399347/how-to-pass-post-array-parameter-in-spring-mvc
    @PostMapping(value = "/clerk/item/{id}/assets")
    public @ResponseBody
    ResponseAjax assetAddPost(@PathVariable Long id, @RequestParam(value = "assets[]") List<String> assetValues){
        // Get Clerk ID
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser clerk = myUserDetails.getUser();

        System.out.println("Item Id: " + id);
        for (String asset: assetValues) {
            System.out.println(asset);
        }
        return assetService.createAssets(clerk, id, assetValues);
//        return null;
    }


}
