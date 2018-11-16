package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<MyUser> findAll();
    List<MyUser> findManagers(Integer roleId);
    MyUser findByUsername(String username);
    MyUser save(MyUser user, MultipartFile file) throws IOException;
}
