package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data

@Entity
@Table(name = "action_transaction")
public class ActionTransaction {
    public enum ActionType {
//        Requested, AskApproval, Approved, AskInventory, HandedOver,
//        Done, Rejected, Cancelled, Returned
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
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_generator")
//    @SequenceGenerator(name = "action_generator", sequenceName = "action_seq")
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

//    @Column
//    private Long receivedBy; // Ask the item to clerk
//
//    @Column
//    private Long receivedTime;

    @Column
    private String actionRemarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actionItemId.actionTransaction", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"actionItemId", "actionTransaction"})
//    @JsonIgnore
    private List<ActionItem> actionItemList = new ArrayList<>();
}
