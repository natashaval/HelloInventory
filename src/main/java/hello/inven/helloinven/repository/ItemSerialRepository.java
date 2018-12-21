package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.Item;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSerialRepository extends JpaRepository<ItemSerial, Long> {

    @Query(value = "SELECT s FROM ItemSerial AS s JOIN s.item AS i WHERE i.id = :itemId")
    List<ItemSerial> findItemSerialByItemId(@Param("itemId") Long itemId);

    @Query(value = "SELECT * FROM item_serial AS s WHERE s.item_id = :itemId AND s.clerk_id = :clerkId AND s.emp_id = :clerkId", nativeQuery = true)
    List<ItemSerial> findItemSerialNotAssigned(@Param("itemId") Long itemId, @Param("clerkId") Long clerkId); // mengambil item yang masih berada dalam tangan clerk

    @Query(value = "SELECT DISTINCT clerk_id FROM item_serial AS s WHERE s.item_id = :itemId LIMIT 1", nativeQuery = true)
    Long findClerkIdByItem(@Param("itemId") Long itemId);

}
