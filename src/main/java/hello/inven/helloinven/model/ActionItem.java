package hello.inven.helloinven.model;

import javax.persistence.*;

@Entity
@Table(name = "action_item")
@AssociationOverrides({ // override the mapping for MyUser and Item for composite key
        @AssociationOverride(name = "actionItemId.actionTransaction", joinColumns = @JoinColumn(name = "action_id")),
        @AssociationOverride(name = "actionItemId.item", joinColumns = @JoinColumn(name = "item_id"))
        // AssociationOverride namanya harus sama dengan yang private AssetId primaryKey
})
public class ActionItem { // Result of Many to Many Table
    @EmbeddedId
    private ActionItemId actionItemId = new ActionItemId();

    public enum ItemStatus {
        Pending, Sent, Received, Rejected
    }

    @Column(name = "status", nullable = false)
    private ItemStatus itemStatus;

    public ActionItemId getActionItemId() {
        return actionItemId;
    }

    public void setActionItemId(ActionItemId actionItemId) {
        this.actionItemId = actionItemId;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Transient
    public Item getItem(){
        return getActionItemId().getItem();
    }

    public void setItem(Item item){
        getActionItemId().setItem(item);
    }

    @Transient
    public ActionTransaction getActionTransaction() {return getActionItemId().getActionTransaction();}

    public void setActionTransaction(ActionTransaction actionTransaction) {
        getActionItemId().setActionTransaction(actionTransaction);
    }

}
