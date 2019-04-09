package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "action_transaction")
public class ActionTransaction {
    public enum ActionType {
        PendingApproval, // Waiting for Manager Approval
        PendingInventory, // Waiting for Clerk to accept it
        HandedOver, // (Several) item is being sent
        RequestDone, // The item(s) has been received
        RejectApproval, // Manager reject request
        RejectInventory, // Clerk reject request
        CancelRequest, // Employee cancel on going request

        ReturnApproval, // Employee return item and wait manager approval
        ReturnInventory, // Employee return and wait inventory approval
        ReturnDone // Employee return item(s)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id", unique = true, nullable = false, updatable = false)
    private Long actionId;

    @Column(nullable = false)
    @Enumerated
    private ActionType actionType;

    @ManyToOne
    @JsonIgnoreProperties(value = "actionTransactions")
    private MyUser requestedBy; // Employee / manager who request the items

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date requestTime;

    @Column
    private Long approvedBy; // If employee, then ask for manager approval

    @Column
    private Date approvedTime;

    @Column
    private String actionRemarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actionItemId.actionTransaction", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"actionItemId", "actionTransaction"})
    private List<ActionItem> actionItemList = new ArrayList<>();

    public ActionTransaction(ActionType actionType, MyUser requestedBy, Date requestTime, Long approvedBy, Date approvedTime, String actionRemarks, List<ActionItem> actionItemList) {
        this.actionType = actionType;
        this.requestedBy = requestedBy;
        this.requestTime = requestTime;
        this.approvedBy = approvedBy;
        this.approvedTime = approvedTime;
        this.actionRemarks = actionRemarks;
        this.actionItemList = actionItemList;
    }
}
