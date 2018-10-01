package hello.inven.helloinven.service;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.MyUserRepository;
import hello.inven.helloinven.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    MyUserRepository myUserRepository;

    @Override
    public List<MyUser> findAll(){
        return myUserRepository.findAll();
    }

    @Override
    public MyUser findByUsername(String username){
        return myUserRepository.findByUsername(username);
    }

    @Override
    public void save(MyUser user){
        myUserRepository.save(user);
    }

}
