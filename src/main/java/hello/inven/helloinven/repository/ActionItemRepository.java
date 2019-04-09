package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.ActionItem;
import hello.inven.helloinven.model.ActionItemId;
import hello.inven.helloinven.model.ActionTransaction;
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
    List<ActionItem> findActionItemsByReceivedByAndItemStatusAndActionItemIdActionTransactionActionTypeAndActionItemIdActionTransactionApprovedTimeNotNull(Long clerkId, ActionItem.ItemStatus itemStatus, ActionTransaction.ActionType actionType);

    // Get action item FOR employee to see status
    List<ActionItem> findActionItemsByActionItemIdActionTransactionActionIdAndActionItemIdActionTransactionRequestedBy(Long actionId, MyUser myUser);

    // Get action item FOR employee which has been sent
    List<ActionItem> findActionItemsByItemStatusAndActionItemIdActionTransactionRequestedBy(ActionItem.ItemStatus itemStatus, MyUser myUser);

    // Get action item BY STATUS and actTransId and itemId
    ActionItem findActionItemByItemStatusAndActionItemIdActionTransactionActionIdAndActionItemIdItemId(ActionItem.ItemStatus itemStatus, Long actionId, Long itemId);

}
