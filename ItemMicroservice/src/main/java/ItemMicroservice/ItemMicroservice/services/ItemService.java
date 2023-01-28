package ItemMicroservice.ItemMicroservice.services;

import ItemMicroservice.ItemMicroservice.models.Item;
import ItemMicroservice.ItemMicroservice.repositories.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Item getItemById(Long id) {
        Optional<Item> optional;
        if ((optional = itemRepository.findById(id)).isEmpty()) {
            return null;
        } else {
            return optional.get();
        }
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemByName(String name) {
        return itemRepository.findByName(name);
    }

    public Item createNewItem(Item newItem) {
        return itemRepository.save(newItem);
    }


    public Item updateItem(Item updatedItem) {
        Item item = itemRepository.findByName(updatedItem.getName());
        BeanUtils.copyProperties(updatedItem, item);
        return itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }
}
