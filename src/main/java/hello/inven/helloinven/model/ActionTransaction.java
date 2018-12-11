package hello.inven.helloinven.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "action_transaction")
public class ActionTransaction {
    public enum ActionType {
        Requested, AskApproval, Approved, AskInventory, HandedOver,
        Received, Rejected, Cancelled, Returned
    }

    @Id
    @GeneratedValue
    @Column(name = "action_id", unique = true, nullable = false, updatable = false)
    private Long actionId;

    @Enumerated
    private ActionType actionType;

    @ManyToOne
    private MyUser requestedBy; // Employee / manager who request the items

    @Column
    private Date requestTime;

    @Column
    private Long approvedBy; // If employee, then ask for manager approval

    @Column
    private Date approvedTime;

    @Column
    private Long receivedBy; // Ask the item to clerk

    @Column
    private Long receivedTime;

    @Column
    private String actionRemarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actionItemId.item", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    public Long getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Long receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Long receivedTime) {
        this.receivedTime = receivedTime;
    }

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
