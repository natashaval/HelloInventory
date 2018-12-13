package hello.inven.helloinven.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "action_transaction")
public class ActionTransaction {
    public enum ActionType {
//        Requested, AskApproval, Approved, AskInventory, HandedOver,
//        Done, Rejected, Cancelled, Returned
        PendingApproval, // Waiting for Manager Approval
        PendingInventory, // Waiting for Clerk to accept it
        HandedOver, // (Several) item is being sent
        Done, // The item(s) has been received
        RejectApproval, // Manager reject request
        RejectInventory, // Clerk reject request
        CancelRequest // Employee cancel on going request
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
//    @JsonIgnoreProperties(value = {"item", "actionTransaction"})
//    @JsonIgnoreProperties(value = {"actionItemId", "item", "actionTransaction"})
    @JsonIgnoreProperties(value = {"actionItemId", "actionTransaction"})
//    @JsonIgnoreProperties(value = {"actionItemId.item.category", "actionItemId.item.item"})
//    @JsonIgnore

    private List<ActionItem> actionItemList = new ArrayList<>();

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public MyUser getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(MyUser requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Date approvedTime) {
        this.approvedTime = approvedTime;
    }

//    public Long getReceivedBy() {
//        return receivedBy;
//    }
//
//    public void setReceivedBy(Long receivedBy) {
//        this.receivedBy = receivedBy;
//    }
//
//    public Long getReceivedTime() {
//        return receivedTime;
//    }
//
//    public void setReceivedTime(Long receivedTime) {
//        this.receivedTime = receivedTime;
//    }

    public String getActionRemarks() {
        return actionRemarks;
    }

    public void setActionRemarks(String actionRemarks) {
        this.actionRemarks = actionRemarks;
    }

    public List<ActionItem> getActionItemList() {
        return actionItemList;
    }

    public void setActionItemList(List<ActionItem> actionItemList) {
        this.actionItemList = actionItemList;
    }
}
