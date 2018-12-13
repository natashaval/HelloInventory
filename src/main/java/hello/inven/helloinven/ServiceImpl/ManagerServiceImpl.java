package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.ActionTransaction;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.repository.ActionTransactionRepository;
import hello.inven.helloinven.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
