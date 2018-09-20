package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    //User findUserByUsername(String username);

}
