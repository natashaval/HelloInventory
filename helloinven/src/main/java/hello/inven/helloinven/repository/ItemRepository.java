package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("itemRepository")
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();
}
