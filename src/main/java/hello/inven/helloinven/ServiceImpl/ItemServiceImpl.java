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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseAjax createItem(Item item) throws IOException {
//    public ResponseAjax createItem(Item item, MultipartFile file) throws IOException {
        Item newItem = new Item();
        System.out.println("item ID: " + item.getId() + "item Name: " + item.getName());
        newItem.setId(item.getId());
        newItem.setName(item.getName());
        newItem.setItemType(Item.ItemType.ITEM);
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


        MultipartFile file = item.getImage();

        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
//            String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads/item/";
            String uploadDirectory = System.getProperty("user.dir") + "/uploads/item/";

            try {
                Files.copy(is, Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e){
                e.printStackTrace();
            }

            newItem.setImagePath(fileName);
            System.out.println("\nFileSUDAHDITAMBAH: " + Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize());
        }

         itemRepository.save(newItem);
        return new ResponseAjax("Created", newItem);
    }

    @Override
    public ResponseAjax getAllItems() {
        List<Item> itemList = itemRepository.findAll();
        return new ResponseAjax("Read", itemList);
    }

    @Override
    public ResponseAjax deleteItem(Long id){
        Item item = itemRepository.findById(id).orElse(null);
        System.out.println(item);
        if (item != null) {
            if (!item.getImagePath().isEmpty()){
                String photoDirectory = System.getProperty("user.dir") + "/uploads/item/";
                try {
                    File file = new File(photoDirectory + item.getImagePath());

                    if (file.delete()) {
                        System.out.println(file.getName() + "is deleted");
                    } else {
                        System.out.println("Delete file error");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            itemRepository.delete(item);
            return new ResponseAjax("Deleted", "Item has been deleted!");
        }
        else {
            return new ResponseAjax("Failed", "Item is failed to be deleted!");
        }

    }

    @Override
    public ResponseAjax detailItem(Long id){
//            public Item detailItem(Long id){
        Item item = itemRepository.findById(id).orElse(null);
        /*
        if (item != null){
            return new ResponseAjax("Done", item);
        }
        else {
            return new ResponseAjax("Failed", "Item not found!");
        }
        */
        return new ResponseAjax("Done", item);
    }
}
