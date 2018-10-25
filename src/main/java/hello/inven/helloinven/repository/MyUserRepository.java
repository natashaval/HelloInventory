package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    List<MyUser> findUserByName(String name);
    MyUser findByUsername(String username);

    List<MyUser> findAll();

}
