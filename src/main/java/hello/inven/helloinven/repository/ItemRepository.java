package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();

    // Mencari Item yang termasuk Asset sehingga dapat di request oleh Employee
    List<Item> findItemByItemType(Item.ItemType itemType);
}
