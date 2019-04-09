package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor

@Entity
@Table(name = "action_item") // ActionTransaction many-to-many Item
// mapping the join table using composite-ID class (ActionItemId)
@AssociationOverrides({ // override the mapping for MyUser and Item for composite key
        @AssociationOverride(name = "actionItemId.actionTransaction", joinColumns = @JoinColumn(name = "action_id")),
        @AssociationOverride(name = "actionItemId.item", joinColumns = @JoinColumn(name = "item_id"))
        // AssociationOverride namanya harus sama dengan yang private ActionItemId primaryKey
})
public class ActionItem { // Result of Many to Many Table
    @EmbeddedId // embedding composite-id as primary key
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
    private Date receivedTime; // Time the clerk accept / reject item sent

    @Column
    private Date receiveEmpTime; // Time employee receive item

    @Column
    private Long itemSerialId;

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

    public ActionItem(ActionItemId actionItemId, ItemStatus itemStatus, Long receivedBy, Date receivedTime, Date receiveEmpTime, Long itemSerialId) {
        this.actionItemId = actionItemId;
        this.itemStatus = itemStatus;
        this.receivedBy = receivedBy;
        this.receivedTime = receivedTime;
        this.receiveEmpTime = receiveEmpTime;
        this.itemSerialId = itemSerialId;
    }
}
