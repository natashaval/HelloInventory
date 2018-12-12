package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.ActionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTransactionRepository extends JpaRepository<ActionTransaction, Long> {
}
