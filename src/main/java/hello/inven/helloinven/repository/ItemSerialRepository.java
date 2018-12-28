package hello.inven.helloinven.repository;

import hello.inven.helloinven.dto.ItemSerialOnly;
import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.dto.ItemSerialCount;
import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSerialRepository extends JpaRepository<ItemSerial, Long> {

//    @Query(value = "SELECT s FROM ItemSerial AS s JOIN s.item AS i WHERE i.id = :itemId")
//    List<ItemSerial> findItemSerialByItemId(@Param("itemId") Long itemId);

    List<ItemSerial> findItemSerialsByItemId(Long itemId);
    List<ItemSerial> findItemSerialsByItemIdAndMyUser(Long itemId, MyUser myUser);
    List<ItemSerial> findItemSerialsByMyUser(MyUser myUser);

    @Query(value = "SELECT new hello.inven.helloinven.dto.ItemSerialOnly(s.serialId, s.clerkId, s.item.id, s.item.name, s.myUser.id, s.myUser.name)" +
    "FROM ItemSerial s WHERE s.item.id = :itemId")
    List<ItemSerialOnly> findItemSerialsOnlyByItemId(@Param("itemId") Long itemId);

    @Query(value = "SELECT * FROM item_serial AS s WHERE s.item_id = :itemId AND s.clerk_id = :clerkId AND s.emp_id = :clerkId", nativeQuery = true)
    List<ItemSerial> findItemSerialNotAssigned(@Param("itemId") Long itemId, @Param("clerkId") Long clerkId); // mengambil item yang masih berada dalam tangan clerk

    @Query(value = "SELECT DISTINCT clerk_id FROM item_serial AS s WHERE s.item_id = :itemId LIMIT 1", nativeQuery = true)
    Long findClerkIdByItem(@Param("itemId") Long itemId);

//    @Query(value = "SELECT new map(s.serialId as cserial, s.myUser as cuser) FROM ItemSerial s")
//    List<ItemSerial> findandCountMyItem();

//    https://stackoverflow.com/questions/36328063/how-to-return-a-custom-object-from-a-spring-data-jpa-group-by-query
//    https://stackoverflow.com/questions/49682068/return-custom-object-using-custom-query-with-spring-data-jpa?rq=1
//    @Query(value = "SELECT it.item_id, it.name, iser.emp_id, count(serial_id) as countserial FROM item_serial as iser lefT JOIN item as it on iser.item_id = it.item_id where emp_id = 2222 group by item_id")
    @Query(value = "SELECT new hello.inven.helloinven.dto.ItemSerialCount(s.item.id, s.item.name, s.item.category.name, s.myUser.id, count(s.serialId))" +
    "FROM ItemSerial s WHERE s.myUser.id = :empId GROUP BY s.item")
    List<ItemSerialCount> findAndCountItemSerialByEmpId(@Param("empId") Long empId);

}
