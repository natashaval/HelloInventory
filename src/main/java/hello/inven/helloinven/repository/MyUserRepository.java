package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String username);

    List<MyUser> findAll();

    @Query("SELECT u FROM MyUser AS u JOIN u.role AS r WHERE r.id = :roleId ")
    List<MyUser> findByRole(@Param("roleId") Integer roleId);

    // digunakan oleh clerk untuk assign inventory to worker
    @Query("SELECT u FROM MyUser AS u JOIN u.role AS r WHERE r.id = 1 OR r.id = 2")
    List<MyUser> findManagerAndEmployee();
}
