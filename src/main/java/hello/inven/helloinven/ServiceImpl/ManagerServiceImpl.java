package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.ActionTransaction;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
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
    public ResponseAjax requestApproval(MyUser manager){
        List<ActionTransaction> transactionList = transactionRepository.findByActionTypeAndApprovedBy(ActionTransaction.ActionType.PendingApproval, manager.getId());
        return new ResponseAjax("Done", transactionList);
    }

    @Override
    public ResponseAjax approvedApproval(Long actionId){
        ActionTransaction transaction = transactionRepository.findById(actionId).orElse(null);
        if (transaction!= null){
            Date currentTime = new Date();
            transaction.setApprovedTime(currentTime);
            transaction.setActionType(ActionTransaction.ActionType.PendingInventory);
            transactionRepository.save(transaction);
            return new ResponseAjax("Done", "Request Approved and Sent to Inventory!");
        }
        else {
            return new ResponseAjax("Failed", "Request not found!");
        }
    }

    @Override
    public ResponseAjax rejectedApproval(Long actionId){
        ActionTransaction transaction = transactionRepository.findById(actionId).orElse(null);
        if (transaction!= null){
            Date currentTime = new Date();
//            transaction.setApprovedTime(currentTime);
            transaction.setActionType(ActionTransaction.ActionType.RejectApproval);
            transactionRepository.save(transaction);
            return new ResponseAjax("Done", "Request Rejected!");
        }
        else {
            return new ResponseAjax("Failed", "Request not found!");
        }
    }
}
