package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    List<MyUser> findUserByName(String name);
    MyUser findByUsername(String username);

    List<MyUser> findAll();

//    https://stackoverflow.com/questions/14446048/hibernate-table-not-mapped-error
    @Query("SELECT u FROM MyUser AS u JOIN u.role AS r WHERE r.id = :roleId ")
    List<MyUser> findByRole(@Param("roleId") Integer roleId);
}
