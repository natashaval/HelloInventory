package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable // so this class can be embedded in other entities, to hold composite entity identifier
@Data

public class ActionItemId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;

    @ManyToOne
    private ActionTransaction actionTransaction;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ActionTransaction getActionTransaction() {
        return actionTransaction;
    }

    public void setActionTransaction(ActionTransaction actionTransaction) {
        this.actionTransaction = actionTransaction;
    }

    public ActionItemId(Item item, ActionTransaction actionTransaction) {
        this.item = item;
        this.actionTransaction = actionTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionItemId)) return false;
        ActionItemId that = (ActionItemId) o;
        return Objects.equals(item, that.item) &&
                Objects.equals(actionTransaction, that.actionTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, actionTransaction);
    }
}
