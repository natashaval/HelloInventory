package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.exceptionhandler.BadRequestException;
import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.ActionTransaction;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.repository.ActionTransactionRepository;
import hello.inven.helloinven.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ActionTransactionRepository transactionRepository;

    @Override
    public List<ActionTransaction> requestApproval(ActionTransaction.ActionType actionType, MyUser manager){
        List<ActionTransaction> transactionList = transactionRepository.findByActionTypeAndApprovedBy(actionType, manager.getId());
//        if (transactionList.isEmpty()) throw new NotFoundException("Transaction list not found!");
        return transactionList;
    }

    @Override
    public ActionTransaction approvedApproval(Long actionId, Boolean requestType){
        ActionTransaction transaction = transactionRepository.findById(actionId).orElseThrow(null);
        if (transaction.getActionType().toString()!= "PendingApproval" || transaction.getActionType().toString()!="ReturnApproval") throw new BadRequestException("Action Transaction not allowed!");
        if (transaction != null){
            Date currentTime = new Date();
            transaction.setApprovedTime(currentTime);
            if (requestType == true) transaction.setActionType(ActionTransaction.ActionType.PendingInventory);
            else if (requestType == false) transaction.setActionType(ActionTransaction.ActionType.ReturnInventory);
            transaction = transactionRepository.save(transaction);
//            return new ResponseAjax("Done", "Request Approved and Sent to Inventory!");
            return transaction;
        }
        else {
//            return new ResponseAjax("Failed", "Request not found!");
            System.out.println("Not Found");
            throw new NotFoundException("Request not found!");

        }
    }

    @Override
    public ActionTransaction rejectedApproval(Long actionId){
        ActionTransaction transaction = transactionRepository.findById(actionId).orElse(null);
        if (transaction.getActionType().toString()!= "PendingApproval" || transaction.getActionType().toString()!= "ReturnApproval") throw new BadRequestException("Action Transaction not allowed!");
        if (transaction!= null){
            Date currentTime = new Date();
//            transaction.setApprovedTime(currentTime);
            transaction.setActionType(ActionTransaction.ActionType.RejectApproval);
            transaction = transactionRepository.saveAndFlush(transaction);
//            return new ResponseAjax("Done", "Request Rejected!");
            return  transaction;
        }
        else {
//            return new ResponseAjax("Failed", "Request not found!");
            throw new NotFoundException("Request not found!");
        }
    }
}
