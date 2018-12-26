package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.ActionTransaction;
import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionTransactionRepository extends JpaRepository<ActionTransaction, Long> {

    List<ActionTransaction> findByActionTypeAndApprovedBy(ActionTransaction.ActionType actionType, Long managerId);
    List<ActionTransaction> findActionTransactionsByRequestedBy(MyUser myUser);
}
