package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSerialRepository extends JpaRepository<ItemSerial, Long> {

    @Query("SELECT u FROM MyUser AS u JOIN u.role AS r WHERE r.id = :roleId ")
    List<MyUser> findByRole(@Param("roleId") Integer roleId);

    @Query("SELECT s FROM ItemSerial AS s JOIN s.item AS i WHERE i.id = :itemId")
    List<ItemSerial> findItemSerialByItemId(@Param("itemId") Long itemId);
}
