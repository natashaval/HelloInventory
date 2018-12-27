package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.ActionItem;
import hello.inven.helloinven.model.ActionItemId;
import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem, ActionItemId> {
    // Get action item FOR clerk which has been approved by manager
    List<ActionItem> findActionItemsByReceivedByAndItemStatusAndActionItemIdActionTransactionApprovedTimeNotNull(Long clerkId, ActionItem.ItemStatus itemStatus);

    // Get action item FOR employee to see status
    List<ActionItem> findActionItemsByActionItemIdActionTransactionActionIdAndActionItemIdActionTransactionRequestedBy(Long actionId, MyUser employee);

//    ActionItem findActionItemByActionTransactionActionIdAndItem_Id(Long actionTransactionId, Long ItemId);
    @Query(value = "SELECT * FROM action_item AS a WHERE a.action_id = :actionTransId AND a.item_id = :itemId AND a.status = 0", nativeQuery = true)
    ActionItem findActionItemForStatus(@Param("actionTransId") Long actionTransId, @Param("itemId") Long itemId);

}
