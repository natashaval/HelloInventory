package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.exceptionhandler.BadRequestException;
import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.response.ResponseAjax;
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
    public Item createItem(Item item) throws IOException {
        Item existingItem = itemRepository.findById(item.getId()).orElse(null);
        if (existingItem == null) {
            Item newItem = new Item();
            newItem.setId(item.getId());
            newItem.setName(item.getName());
            newItem.setItemType(Item.ItemType.ITEM);
            newItem.setQuantity(item.getQuantity());
            newItem.setPrice(item.getPrice());
            newItem.setWeight(item.getWeight());
            newItem.setHeight(item.getHeight());
            newItem.setWidth(item.getWidth());
            newItem.setDepth(item.getDepth());

            newItem.setCategory(item.getCategory());

            MultipartFile file = item.getImage();

            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                InputStream is = file.getInputStream();
                String uploadDirectory = System.getProperty("user.dir") + "/uploads/item/";

                try {
                    Files.copy(is, Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                newItem.setImagePath(fileName);
            }

            itemRepository.save(newItem);
            return item;
        }
        else throw new BadRequestException("Item ID is already exists!");
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> itemList = itemRepository.findAll();
        return itemList;
    }

    @Override
    public Item deleteItem(Long id){
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            if (item.getImagePath() != null){
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
            return item;
        }
        else {
            throw new NotFoundException("Item not found and failed to be deleted!");
        }

    }

    @Override
    public Item detailItem(Long id){
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null){
            return item;
        }
        else throw new NotFoundException("Item not found!");

    }
}
