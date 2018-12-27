package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "action_item")
@AssociationOverrides({ // override the mapping for MyUser and Item for composite key
        @AssociationOverride(name = "actionItemId.actionTransaction", joinColumns = @JoinColumn(name = "action_id")),
        @AssociationOverride(name = "actionItemId.item", joinColumns = @JoinColumn(name = "item_id"))
        // AssociationOverride namanya harus sama dengan yang private AssetId primaryKey
})
public class ActionItem { // Result of Many to Many Table
    @EmbeddedId
//    @JsonIgnoreProperties(value = {"item.actionItemList", "item.category"})
//    @JsonIgnoreProperties(value = {"item", "actionTransaction"})
    @JsonIgnore
    private ActionItemId actionItemId = new ActionItemId();

    public enum ItemStatus {
        Pending, // Still in waiting list after request item
        Sent, //  Clerk has approved and the item is on going
        Received, // Employee has receive the item
        Rejected, // Clerk has rejected the request
        Cancelled // Employee cancelled item request
    }

    @Column(name = "status", nullable = false)
    private ItemStatus itemStatus;

    @Column
    private Long receivedBy; // Ask the item to clerk

    @Column
    private Date receivedTime;

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

    public Long getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Long receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
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
