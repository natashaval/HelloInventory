package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.ActionItem;
import hello.inven.helloinven.model.ActionItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem, ActionItemId> {
}
