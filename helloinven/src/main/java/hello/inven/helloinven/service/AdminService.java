package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AdminService {

    List<MyUser> findAll();
}
