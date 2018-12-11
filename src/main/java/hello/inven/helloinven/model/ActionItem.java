package hello.inven.helloinven.model;

import javax.persistence.*;

@Entity
@Table(name = "action_item")
@AssociationOverrides({ // override the mapping for MyUser and Item for composite key
        @AssociationOverride(name = "actionItemId.myUser", joinColumns = @JoinColumn(name = "emp_id")),
        @AssociationOverride(name = "actionItemId.item", joinColumns = @JoinColumn(name = "item_id"))
        // AssociationOverride namanya harus sama dengan yang private AssetId primaryKey
})
public class ActionItem { // Result of Many to Many Table
    @EmbeddedId
    private ActionItemId actionItemId = new ActionItemId();

    @Column(name = "status", nullable = false)
    private Byte itemStatus;

    public ActionItemId getActionItemId() {
        return actionItemId;
    }

    public void setActionItemId(ActionItemId actionItemId) {
        this.actionItemId = actionItemId;
    }

    public Byte getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Byte itemStatus) {
        this.itemStatus = itemStatus;
    }
}
