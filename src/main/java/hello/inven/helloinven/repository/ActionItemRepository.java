package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.ActionItem;
import hello.inven.helloinven.model.ActionItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem, ActionItemId> {
    List<ActionItem> findActionItemsByReceivedByAndItemStatus(Long clerkId, ActionItem.ItemStatus itemStatus);

//    ActionItem findActionItemByActionTransactionActionIdAndItem_Id(Long actionTransactionId, Long ItemId);
    @Query(value = "SELECT * FROM action_item AS a WHERE a.action_id = :actionTransId AND a.item_id = :itemId AND a.status = 0", nativeQuery = true)
    ActionItem findActionItemForStatus(@Param("actionTransId") Long actionTransId, @Param("itemId") Long itemId);
}
